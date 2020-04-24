package jhi.germinate.brapi.resource.list;

import java.sql.Timestamp;
import java.util.Map;

import jhi.germinate.brapi.resource.base.Reference;

/**
 * @author Sebastian Raubach
 */
public class Lists
{
	private Map<String, String>       additionalInfo;
	private java.util.List<String>    data;
	private Timestamp                 dateCreated;
	private Timestamp                 dateModified;
	private java.util.List<Reference> externalReferences;
	private String                    listDbId;
	private String                    listDescription;
	private String                    listName;
	private String                    listOwnerName;
	private String                    listOwnerPersonDbId;
	private Long                      listSize;
	private String                    listSource;
	private String                    listType;

	public Map<String, String> getAdditionalInfo()
	{
		return additionalInfo;
	}

	public Lists setAdditionalInfo(Map<String, String> additionalInfo)
	{
		this.additionalInfo = additionalInfo;
		return this;
	}

	public java.util.List<String> getData()
	{
		return data;
	}

	public Lists setData(java.util.List<String> data)
	{
		this.data = data;
		return this;
	}

	public Timestamp getDateCreated()
	{
		return dateCreated;
	}

	public Lists setDateCreated(Timestamp dateCreated)
	{
		this.dateCreated = dateCreated;
		return this;
	}

	public Timestamp getDateModified()
	{
		return dateModified;
	}

	public Lists setDateModified(Timestamp dateModified)
	{
		this.dateModified = dateModified;
		return this;
	}

	public java.util.List<Reference> getExternalReferences()
	{
		return externalReferences;
	}

	public Lists setExternalReferences(java.util.List<Reference> externalReferences)
	{
		this.externalReferences = externalReferences;
		return this;
	}

	public String getListDbId()
	{
		return listDbId;
	}

	public Lists setListDbId(String listDbId)
	{
		this.listDbId = listDbId;
		return this;
	}

	public String getListDescription()
	{
		return listDescription;
	}

	public Lists setListDescription(String listDescription)
	{
		this.listDescription = listDescription;
		return this;
	}

	public String getListName()
	{
		return listName;
	}

	public Lists setListName(String listName)
	{
		this.listName = listName;
		return this;
	}

	public String getListOwnerName()
	{
		return listOwnerName;
	}

	public Lists setListOwnerName(String listOwnerName)
	{
		this.listOwnerName = listOwnerName;
		return this;
	}

	public String getListOwnerPersonDbId()
	{
		return listOwnerPersonDbId;
	}

	public Lists setListOwnerPersonDbId(String listOwnerPersonDbId)
	{
		this.listOwnerPersonDbId = listOwnerPersonDbId;
		return this;
	}

	public Long getListSize()
	{
		return listSize;
	}

	public Lists setListSize(Long listSize)
	{
		this.listSize = listSize;
		return this;
	}

	public String getListSource()
	{
		return listSource;
	}

	public Lists setListSource(String listSource)
	{
		this.listSource = listSource;
		return this;
	}

	public String getListType()
	{
		return listType;
	}

	public Lists setListType(String listType)
	{
		this.listType = listType;
		return this;
	}
}
