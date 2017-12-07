/*
* David Weinflash
* Tim Root
* Section 001B
*
* Assignment #12 - The Adventure, CSc 210, Fall 2017
* This class is a child of the Item class with a damage instance variable.
*/

public class Weapon extends Item {

	private int damage;

	public void set_damage(int d)
	{
		damage = d;
	}

	public int get_damage()
	{
		return damage;
	}

	public void print()
	{
		String name = super.get_name();
		float weight = super.get_weight();
	
		System.out.format("Name: %s\n", name);
		System.out.format("Weight: %.1f\n", weight);
		System.out.format("Power: %d\n", damage);
	}
}
