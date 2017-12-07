/*
* David Weinflash
* Tim Root
* Section 001B
*
* Assignment #12 - The Adventure, CSc 210, Fall 2017
* This class is a child of the Item class with a value instance variable.
*/

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

		System.out.format("Name: %s\n", name);
		System.out.format("Weight: %.1f\n", weight);
		System.out.format("Value: %d\n", value);
	}
}
