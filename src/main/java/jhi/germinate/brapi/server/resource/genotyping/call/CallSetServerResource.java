package jhi.germinate.brapi.server.resource.genotyping.call;

import jakarta.ws.rs.core.*;
import jhi.germinate.brapi.server.Brapi;
import jhi.germinate.brapi.server.util.*;
import jhi.germinate.server.*;
import jhi.germinate.server.database.codegen.tables.records.*;
import jhi.germinate.server.resource.datasets.DatasetTableResource;
import jhi.germinate.server.util.*;
import org.jooq.*;
import org.jooq.impl.DSL;
import uk.ac.hutton.ics.brapi.resource.base.*;
import uk.ac.hutton.ics.brapi.resource.genotyping.call.*;
import uk.ac.hutton.ics.brapi.resource.genotyping.variant.Genotype;
import uk.ac.hutton.ics.brapi.server.genotyping.call.BrapiCallSetServerResource;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.stream.*;

import static jhi.germinate.server.database.codegen.tables.Datasetmembers.*;
import static jhi.germinate.server.database.codegen.tables.Datasets.*;
import static jhi.germinate.server.database.codegen.tables.Germinatebase.*;
import static jhi.germinate.server.database.codegen.tables.Markers.*;

@Path("brapi/v2/callsets")
@Secured
@PermitAll
public class CallSetServerResource extends CallSetBaseServerResource implements BrapiCallSetServerResource
{
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResult<ArrayResult<CallSet>> getCallsets(@QueryParam("callSetDbId") String callSetDbId,
														@QueryParam("callSetName") String callSetName,
														@QueryParam("variantSetDbId") String variantSetDbId,
														@QueryParam("sampleDbId") String sampleDbId,
														@QueryParam("germplasmDbId") String germplasmDbId,
														@QueryParam("externalReferenceId") String externalReferenceId,
														@QueryParam("externalReferenceSource") String externalReferenceSource)
		throws IOException, SQLException
	{
		AuthenticationFilter.UserDetails userDetails = (AuthenticationFilter.UserDetails) securityContext.getUserPrincipal();
		List<Integer> datasets = DatasetTableResource.getDatasetIdsForUser(req, userDetails, "genotype");

		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);
			List<Condition> conditions = new ArrayList<>();

			conditions.add(DATASETMEMBERS.DATASET_ID.in(datasets));

			if (!StringUtils.isEmpty(callSetDbId))
				conditions.add(DSL.concat(DATASETMEMBERS.DATASET_ID, DSL.val("-"), GERMINATEBASE.ID).eq(callSetDbId));
			if (!StringUtils.isEmpty(callSetName))
				conditions.add(GERMINATEBASE.NAME.eq(callSetName));
			if (!StringUtils.isEmpty(variantSetDbId))
				conditions.add(DATASETMEMBERS.DATASET_ID.cast(String.class).eq(variantSetDbId));
			if (!StringUtils.isEmpty(germplasmDbId))
				conditions.add(GERMINATEBASE.ID.cast(String.class).eq(germplasmDbId));

			List<CallSet> callSets = getCallSets(context, conditions);

			long totalCount = context.fetchOne("SELECT FOUND_ROWS()").into(Long.class);
			return new BaseResult<>(new ArrayResult<CallSet>()
				.setData(callSets), page, pageSize, totalCount);
		}
	}

	@GET
	@Path("/{callSetDbId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResult<CallSet> getCallSetById(@PathParam("callSetDbId") String callSetDbId)
		throws IOException, SQLException
	{
		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);
			List<CallSet> callSets = getCallSets(context, Collections.singletonList(DSL.concat(DATASETMEMBERS.DATASET_ID, DSL.val("-"), GERMINATEBASE.ID).eq(callSetDbId)));

			if (CollectionUtils.isEmpty(callSets))
				return new BaseResult<>(null, page, pageSize, 0);
			else
				return new BaseResult<>(callSets.get(0), page, pageSize, 1);
		}
	}

	@Override
	@GET
	@Path("/{callSetDbId}/calls")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResult<CallResult<Call>> getCallSetByIdCalls(@PathParam("callSetDbId") String callSetDbId,
															@QueryParam("expandHomozygotes") Boolean expandHomozygotes,
															@QueryParam("unknownString") String unknownString,
															@QueryParam("sepPhased") String sepPhased,
															@QueryParam("sepUnphased") String sepUnphased)
		throws IOException, SQLException
	{
		if (StringUtils.isEmpty(callSetDbId) || !callSetDbId.contains("-"))
		{
			resp.sendError(Response.Status.NOT_FOUND.getStatusCode());
			return null;
		}

		AuthenticationFilter.UserDetails userDetails = (AuthenticationFilter.UserDetails) securityContext.getUserPrincipal();
		List<Integer> datasets = DatasetTableResource.getDatasetIdsForUser(req, userDetails, "genotype");

		try (Connection conn = Database.getConnection())
		{
			DSLContext context = Database.getContext(conn);
			String[] parts = callSetDbId.split("-");

			DatasetsRecord dataset = context.selectFrom(DATASETS)
											.where(DATASETS.ID.in(datasets))
											.and(DATASETS.IS_EXTERNAL.eq(false))
											.and(DATASETS.ID.cast(String.class).eq(parts[0]))
											.fetchAny();
			GerminatebaseRecord germplasm = context.selectFrom(GERMINATEBASE)
												   .where(GERMINATEBASE.ID.cast(String.class).eq(parts[1]))
												   .fetchAny();

			if (dataset == null || StringUtils.isEmpty(dataset.getSourceFile()) || germplasm == null)
			{
				resp.sendError(Response.Status.BAD_REQUEST.getStatusCode());
				return null;
			}

			GenotypeEncodingParams params = new GenotypeEncodingParams();
			params.setUnknownString(unknownString);
			params.setSepPhased(sepPhased);
			params.setSepUnphased(sepUnphased);
			params.setUnknownString(unknownString);
			try
			{
				params.setCollapse(!expandHomozygotes);
			}
			catch (Exception e)
			{
			}

			Hdf5DataExtractor extractor = new Hdf5DataExtractor(new File(Brapi.BRAPI.hdf5BaseFolder, dataset.getSourceFile()));
			List<String> alleles = extractor.getAllelesForLine(germplasm.getName(), params);
			List<String> markerNames = extractor.getMarkers();
			Map<String, Integer> markerNamesToIds = context.selectFrom(MARKERS)
														   .where(MARKERS.MARKER_NAME.in(markerNames))
														   .fetchMap(MARKERS.MARKER_NAME, MARKERS.ID);

			List<Call> calls = IntStream.range(0, alleles.size())
										.skip(pageSize * page)
										.limit(pageSize)
										.mapToObj(i -> new Call()
											.setCallSetDbId(callSetDbId)
											.setCallSetName(germplasm.getName())
											.setGenotypeValue(alleles.get(i))
											.setVariantDbId(dataset.getId() + "-" + markerNamesToIds.get(markerNames.get(i)))
											.setVariantName(markerNames.get(i)))
										.collect(Collectors.toList());

			CallResult<Call> callResult = new CallResult<Call>()
				.setData(calls)
				.setExpandHomozygotes(!params.isCollapse())
				.setSepPhased(params.getSepPhased())
				.setSepUnphased(params.getSepUnphased())
				.setUnknownString(params.getUnknownString());
			return new BaseResult<>(callResult, page, pageSize, alleles.size());
		}
	}
}
