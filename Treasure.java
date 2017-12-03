public class Treasure extends Item {

	private int value;

	public void set_value(int v)
	{
		value = v;
	}

	public int get_value()
	{
		return value;
	}

	public void print()
	{
		String name = super.get_name();
		float weight = super.get_weight();

		System.out.format("Name: %s \n", name);
		System.out.format("Weight: %f \n", weight);
		System.out.format("Value: %d \n", value);
	}
}
