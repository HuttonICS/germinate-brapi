package jhi.germinate.brapi.resource.call;

import java.sql.Timestamp;
import java.util.*;

/**
 * @author Sebastian Raubach
 */
public class CallSet
{
	private Map<String, String> additionalInfo;
	private String              callSetDbId;
	private String              callSetName;
	private Timestamp           created;
	private String              sampleDbId;
	private String              studyDbId;
	private Timestamp           updated;
	private List<String>        variantSetDbIds;

	public Map<String, String> getAdditionalInfo()
	{
		return additionalInfo;
	}

	public CallSet setAdditionalInfo(Map<String, String> additionalInfo)
	{
		this.additionalInfo = additionalInfo;
		return this;
	}

	public String getCallSetDbId()
	{
		return callSetDbId;
	}

	public CallSet setCallSetDbId(String callSetDbId)
	{
		this.callSetDbId = callSetDbId;
		return this;
	}

	public String getCallSetName()
	{
		return callSetName;
	}

	public CallSet setCallSetName(String callSetName)
	{
		this.callSetName = callSetName;
		return this;
	}

	public Timestamp getCreated()
	{
		return created;
	}

	public CallSet setCreated(Timestamp created)
	{
		this.created = created;
		return this;
	}

	public String getSampleDbId()
	{
		return sampleDbId;
	}

	public CallSet setSampleDbId(String sampleDbId)
	{
		this.sampleDbId = sampleDbId;
		return this;
	}

	public String getStudyDbId()
	{
		return studyDbId;
	}

	public CallSet setStudyDbId(String studyDbId)
	{
		this.studyDbId = studyDbId;
		return this;
	}

	public Timestamp getUpdated()
	{
		return updated;
	}

	public CallSet setUpdated(Timestamp updated)
	{
		this.updated = updated;
		return this;
	}

	public List<String> getVariantSetDbIds()
	{
		return variantSetDbIds;
	}

	public CallSet setVariantSetDbIds(List<String> variantSetDbIds)
	{
		this.variantSetDbIds = variantSetDbIds;
		return this;
	}
}
