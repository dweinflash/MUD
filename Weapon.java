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
