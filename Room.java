/*
* David Weinflash
* Tim Root
* Section 001B
*
* Assignment #12 - The Adventure, CSc 210, Fall 2017
* This class acts as a base class for the room.
*/

import java.util.Arrays;
import java.util.*;

public class Room {

	private String name;
	private String description;
	private ArrayList<Item> itemList;
	private String mob;
	private Mob mob_obj;

	// connections are room objects 
	// arranged in directional alphabetical order

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

	public void set_items(String[] i, ArrayList<Treasure> t, ArrayList<Weapon> w)
	{
		/*
		* set_items
		* Based on a list of item names, add item objects to the ArrayList
		* itemList.
		*
		* Arguments: 
		* String[] i - List of item names
		* ArrayList<Treasure> t - Treasure objects
		* ArrayList<Weapon> w - Weapon objects
		*
		* Return value - None.
		*/

		itemList = new ArrayList<Item>();		

		// clean item names
		int n = 0;
		for (String item : i)
		{
			item = item.trim();
			i[n] = item;
			n++;
		}

		// add items to array list
		String item_name;
		Item obj;
		for (int k = 0; k < i.length; k++)
		{
			item_name = i[k];
			
			// search treasure list
			for (int j = 0; j < t.size(); j++)
			{
				obj = t.get(j);
				if (obj.get_name().equals(item_name))
					itemList.add(obj);	
			}
			
			// search weapon list
			for (int j = 0; j < w.size(); j++)
			{
				obj = w.get(j);
				if (obj.get_name().equals(item_name))
					itemList.add(obj);
			}
		}
	}

	public void set_mob(String m)
	{
		mob = m;
	}

	public void set_mob_obj(Mob m)
	{
		mob_obj = m;
	}

	public void set_connection(String direction, Room connect)
	{
		/*
		* set_connections
		* Add room connection to list based on direction.
		*
		* Arguments: 
		* String direction - direction
		* Room connect - connection
		*
		* Return value - None.
		*/

		direction = direction.toUpperCase();

		// add connect based on direction
		// print error and exit if connection already defined
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
		/*
		* valid_connect
		* Determine if connection exists at direction.
		*
		* Arguments: 
		* String direction - direction
		*
		* Return value - True if connection exists, false if not.
		*/

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
		/*
		* get_connection
		* Get the room connection at direction.
		*
		* Arguments: 
		* String direction - direction
		*
		* Return value - Room object.
		*/

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
		/*
		* get_items
		* Return list of items in room.
		*
		* Arguments: None.
		*
		* Return value - String[] item name list.
		*/

		String[] items = new String[itemList.size()];

		for (int i = 0; i < itemList.size(); i++)
			items[i] = itemList.get(i).get_name();

		return items;
	}

	public Item get_item(String item_name)
	{
		/*
		* get_item
		* Return item object if in room.
		*
		* Arguments: String item_name
		*
		* Return value - Item object
		*/

		item_name = item_name.toUpperCase();

		int j = 0;
		for (int i = 0; i < itemList.size(); i++)
		{
			if (itemList.get(i).get_name().toUpperCase().equals(item_name))
				j = i;
		}

		return itemList.get(j);
	}

	public void remove(Item obj)
	{
		/*
		* remove
		* Remove an Item object from the room.
		*
		* Arguments: Item object
		*
		* Return value - None
		*/

		for (int i = 0; i < itemList.size(); i++)
		{
			if (itemList.get(i).get_name().equals(obj.get_name()))
				itemList.remove(i);
		}
	}

	public void add(Item obj)
	{
		itemList.add(obj);
	}

	public String get_mob()
	{
		return mob;
	}

	public Mob get_mob_obj()
	{
		return mob_obj;
	}

	public void print()
	{
		/*
		* print
		* Print the connections and room inventory in alphabetical order.
		*
		* Arguments: None
		*
		* Return value - None. Print to stdout.
		*/

		System.out.format("Name: %s\n", name);
		System.out.format("Description: %s\n", description);
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
		String[] items = new String[itemList.size()];
		for (int i = 0; i < itemList.size(); i++)
			items[i] = itemList.get(i).get_name();
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
		System.out.format("Mob: %s\n", mob);
	}
}
