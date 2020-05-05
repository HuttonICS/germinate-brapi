package jhi.germinate.brapi.resource.attribute;

import java.util.List;

/**
 * @author Sebastian Raubach
 */
public class ValidValues
{
	private List<Category> categories;
	private Integer        max;
	private Integer        min;

	public List<Category> getCategories()
	{
		return categories;
	}

	public ValidValues setCategories(List<Category> categories)
	{
		this.categories = categories;
		return this;
	}

	public Integer getMax()
	{
		return max;
	}

	public ValidValues setMax(Integer max)
	{
		this.max = max;
		return this;
	}

	public Integer getMin()
	{
		return min;
	}

	public ValidValues setMin(Integer min)
	{
		this.min = min;
		return this;
	}
}
