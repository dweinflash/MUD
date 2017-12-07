/*
* David Weinflash
* Tim Root
* Section 001B
*
* Assignment #12 - The Adventure, CSc 210, Fall 2017
* This class acts as a base class for the mob.
*/

public class Mob {

	private String name;
	private String description;
	private int power;

	public void set_name(String n)
	{
		name = n;
	}

	public void set_description(String d)
	{
		description = d;
	}

	public void set_power(int p)
	{
		power = p;
	}

	public String get_name()
	{
		return name;
	}

	public String get_description()
	{
		return description;
	}

	public int get_power()
	{
		return power;
	}

	public void print()
	{
		System.out.format("Name: %s\n", name);
		System.out.format("Description: %s\n", description);
	}

}
