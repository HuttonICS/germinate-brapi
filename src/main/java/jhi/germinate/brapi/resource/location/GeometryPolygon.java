package jhi.germinate.brapi.resource.location;

/**
 * @author Sebastian Raubach
 */
public class GeometryPolygon
{
	private String type;
	private Double[][][] coordinates;

	public String getType()
	{
		return type;
	}

	public GeometryPolygon setType(String type)
	{
		this.type = type;
		return this;
	}

	public Double[][][] getCoordinates()
	{
		return coordinates;
	}

	public GeometryPolygon setCoordinates(Double[][][] coordinates)
	{
		this.coordinates = coordinates;
		return this;
	}
}
