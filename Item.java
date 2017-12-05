public class Item {

	private String name;
	private float weight;

	public void set_name(String n)
	{
		name = n;
	}

	public void set_weight(float w)
	{
		weight = w;
	}

	public String get_name()
	{
		return name;
	}

	public float get_weight()
	{
		return weight;
	}

	public void print()
	{
		System.out.format("Name: %s\n", name);
		System.out.format("Weight: %f\n", weight);
	}
}
