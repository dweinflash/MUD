public class Weapon {

	private String name;
	private float weight;
	private int damage;

	public void set_name(String n)
	{
		name = n;
	}

	public void set_weight(float w)
	{
		weight = w;
	}

	public void set_damage(int d)
	{
		damage = d;
	}

	public String get_name()
	{
		return name;
	}

	public float get_weight()
	{
		return weight;
	}

	public int get_damage()
	{
		return damage;
	}

}