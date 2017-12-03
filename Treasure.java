public class Treasure {

	private String name;
	private float weight;
	private int value;

	public void set_name(String n)
	{
		name = n;
	}

	public void set_weight(float w)
	{
		weight = w;
	}

	public void set_value(int v)
	{
		value = v;
	}

	public String get_name()
	{
		return name;
	}

	public float get_weight()
	{
		return weight;
	}

	public int get_value()
	{
		return value;
	}

	public void print()
	{
		System.out.format("Name: %s \n", name);
		System.out.format("Weight: %f \n", weight);
		System.out.format("Value: %d \n", value);
	}
}
