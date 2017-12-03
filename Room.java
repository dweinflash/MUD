import java.util.Arrays;

public class Room {

	private String name;
	private String description;
	private String[] items;
	private String mob;

	private Room[] connections = new Room[4];
	String[] dirs = {"EAST", "NORTH", "SOUTH", "WEST"};

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

	public void set_connection(String direction, Room connect)
	{
		direction = direction.toUpperCase();

		for (int i = 0; i < 4; i++)
		{
			if (direction.equals(dirs[i]))
			{
				if (connections[i] == null)
					connections[i] = connect;
				else
				{
					System.err.println(direction + " already defined for " + name + ". Exiting.");
					System.exit(1);
				}
			}
		}
	}

	public boolean valid_connect(String direction)
	{
		direction = direction.toUpperCase();

		for (int i = 0; i < 4; i++)
		{
			if (direction.equals(dirs[i]))
			{
				if (connections[i] != null)
					return true;
				else
					return false;
			}
		}

		return false;
	}

	public Room get_connection(String direction)
	{
		direction = direction.toUpperCase();

		int j = 0;
		for (int i = 0; i < 4; i++)
		{
			if (direction.equals(dirs[i]))
				j = i;
		}

		return connections[j];
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
		System.out.format("Name: %s \n", name);
		System.out.format("Description: %s \n", description);
		System.out.print("There are connections in the following directions: ");

		// Print sorted connection directions
		int comma = 0;
		for (int i = 0; i < 4; i++)
		{
			if (connections[i] != null)
			{
				if (comma > 0)
					System.out.print(", ");
				System.out.print(dirs[i]);
				comma++;
			}
		}
		if (comma == 0)
			System.out.print("none");
		System.out.println("");

		// Print sorted item list
		Arrays.sort(items);
		System.out.print("Items: ");

		comma = 0;
		for (String i : items)
		{
			if (comma > 0)
				System.out.print(", ");
			System.out.print(i);
			comma++;
		}
		if (comma == 0)
			System.out.print("none");
		System.out.println("");


		//Print mob
		if (mob == null)
			mob = "none";
		System.out.format("Mob: %s \n", mob);
	}
}
