package jhi.germinate.brapi.resource.germplasm;

/**
 * @author Sebastian Raubach
 */
public class Institute
{
	private String instituteCode;
	private String instituteName;
	private String instituteAddress;

	public String getInstituteCode()
	{
		return instituteCode;
	}

	public Institute setInstituteCode(String instituteCode)
	{
		this.instituteCode = instituteCode;
		return this;
	}

	public String getInstituteName()
	{
		return instituteName;
	}

	public Institute setInstituteName(String instituteName)
	{
		this.instituteName = instituteName;
		return this;
	}

	public String getInstituteAddress()
	{
		return instituteAddress;
	}

	public Institute setInstituteAddress(String instituteAddress)
	{
		this.instituteAddress = instituteAddress;
		return this;
	}
}
