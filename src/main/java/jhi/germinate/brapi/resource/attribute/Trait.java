package jhi.germinate.brapi.resource.attribute;

import java.util.*;

import jhi.germinate.brapi.resource.base.Reference;

/**
 * @author Sebastian Raubach
 */
public class Trait
{
	private Map<String, String> additionalInfo;
	private List<String>        alternativeAbbreviations;
	private String              attribute;
	private String              entity;
	private List<Reference>     externalReferences;
	private String              mainAbbreviation;
	private Ontology            ontologyReference;
	private String              status;
	private List<String>        synonyms;
	private String              traitClass;
	private String              traitDbId;
	private String              traitDescription;
	private String              traitName;

	public Map<String, String> getAdditionalInfo()
	{
		return additionalInfo;
	}

	public Trait setAdditionalInfo(Map<String, String> additionalInfo)
	{
		this.additionalInfo = additionalInfo;
		return this;
	}

	public List<String> getAlternativeAbbreviations()
	{
		return alternativeAbbreviations;
	}

	public Trait setAlternativeAbbreviations(List<String> alternativeAbbreviations)
	{
		this.alternativeAbbreviations = alternativeAbbreviations;
		return this;
	}

	public String getAttribute()
	{
		return attribute;
	}

	public Trait setAttribute(String attribute)
	{
		this.attribute = attribute;
		return this;
	}

	public String getEntity()
	{
		return entity;
	}

	public Trait setEntity(String entity)
	{
		this.entity = entity;
		return this;
	}

	public List<Reference> getExternalReferences()
	{
		return externalReferences;
	}

	public Trait setExternalReferences(List<Reference> externalReferences)
	{
		this.externalReferences = externalReferences;
		return this;
	}

	public String getMainAbbreviation()
	{
		return mainAbbreviation;
	}

	public Trait setMainAbbreviation(String mainAbbreviation)
	{
		this.mainAbbreviation = mainAbbreviation;
		return this;
	}

	public Ontology getOntologyReference()
	{
		return ontologyReference;
	}

	public Trait setOntologyReference(Ontology ontologyReference)
	{
		this.ontologyReference = ontologyReference;
		return this;
	}

	public String getStatus()
	{
		return status;
	}

	public Trait setStatus(String status)
	{
		this.status = status;
		return this;
	}

	public List<String> getSynonyms()
	{
		return synonyms;
	}

	public Trait setSynonyms(List<String> synonyms)
	{
		this.synonyms = synonyms;
		return this;
	}

	public String getTraitClass()
	{
		return traitClass;
	}

	public Trait setTraitClass(String traitClass)
	{
		this.traitClass = traitClass;
		return this;
	}

	public String getTraitDbId()
	{
		return traitDbId;
	}

	public Trait setTraitDbId(String traitDbId)
	{
		this.traitDbId = traitDbId;
		return this;
	}

	public String getTraitDescription()
	{
		return traitDescription;
	}

	public Trait setTraitDescription(String traitDescription)
	{
		this.traitDescription = traitDescription;
		return this;
	}

	public String getTraitName()
	{
		return traitName;
	}

	public Trait setTraitName(String traitName)
	{
		this.traitName = traitName;
		return this;
	}
}
