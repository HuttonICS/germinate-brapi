package jhi.germinate.brapi.server.resource.genotyping.call;

import jhi.germinate.server.*;
import jhi.germinate.server.resource.datasets.DatasetTableResource;
import jhi.germinate.server.util.*;
import org.jooq.*;
import org.jooq.impl.DSL;
import uk.ac.hutton.ics.brapi.resource.base.*;
import uk.ac.hutton.ics.brapi.resource.genotyping.call.CallSet;
import uk.ac.hutton.ics.brapi.server.genotyping.call.BrapiCallSetServerResource;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import static jhi.germinate.server.database.codegen.tables.Datasetmembers.*;
import static jhi.germinate.server.database.codegen.tables.Germinatebase.*;

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
														@QueryParam("germplasmDbId") String germplasmDbId)
		throws IOException, SQLException
	{
		AuthenticationFilter.UserDetails userDetails = (AuthenticationFilter.UserDetails) securityContext.getUserPrincipal();
		List<Integer> datasets = DatasetTableResource.getDatasetIdsForUser(req, resp, userDetails, "genotype");

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
}
