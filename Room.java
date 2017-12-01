public class Room {

	private String name;
	private String description;
	private String[] items;
	private String mob;

	public void set_name(String n)
	{
		name = n;
	}

	public void set_description(String d)
	{
		description = d;
	}

	public void set_items(String[] i)
	{
		int n = 0;
		for (String item : i)
		{
			item = item.trim();
			i[n] = item;
			n++;
		}

		items = i;
	}

	public void set_mob(String m)
	{
		mob = m;
	}

	public String get_name()
	{
		return name;
	}

	public String get_description()
	{
		return description;
	}

	public String[] get_items()
	{
		return items;
	}

	public String get_mob()
	{
		return mob;
	}

	public void print()
	{
		System.out.format("name: %s \n", name);
		System.out.format("description: %s \n", description);
		System.out.println("items:");

		for (String i : items)
			System.out.println(i);

		System.out.format("mob: %s \n", mob);
		System.out.println("");
	}
}
