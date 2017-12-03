import java.io.*;
import java.util.*;

public class Game {

	private static ArrayList<Mob> mobList = new ArrayList<Mob>();
	private static ArrayList<Treasure> treasureList = new ArrayList<Treasure>();	
	private static ArrayList<Weapon> weaponList = new ArrayList<Weapon>();
	private static ArrayList<Room> roomList = new ArrayList<Room>();

	public static void main(String[] args)
	{
		// Exit if no filename in cmd line.
		if (args.length < 1)
		{
			System.err.println("Invalid game file. Exiting.");
			System.exit(1);
		}
	
		// Define the dungeon with game file	
		String filename = args[0];
		read_file(filename);

		for (int i = 0; i < roomList.size(); i++)
			roomList.get(i).print();


	}

	public static void read_file(String filename)
	{
		BufferedReader reader = null;
		String line;
		String[] words;
		String cmd;
		String type;

		// exit program if game file does not exist
		try
		{
			File gamefile = new File(filename);
			reader = new BufferedReader(new FileReader(gamefile));
		}
		catch (Exception e)
		{
			System.err.println("Invalid game file. Exiting.");
			System.exit(1);
		}

		// read game file
		try
		{
			while ((line = reader.readLine()) != null)
			{
				words = line.split(" ");
				cmd = words[0].toUpperCase();

				// definitions
				if (cmd.equals("DEFINE"))
				{
					type = words[1].toUpperCase();
					
					if (type.equals("MOB"))
						defineMob(reader);
					if (type.equals("TREASURE"))
						defineTreasure(reader);
					if (type.equals("WEAPON"))
						defineWeapon(reader);
					if (type.equals("ROOM"))
						defineRoom(reader);		
				}
				// connection
				else if (cmd.length() > 0)
					defineConnection(line);
	
			}
	
			reader.close();
		}
		catch (Exception e)
		{
			System.err.println("Error when reading game file.");	
		}
		

	}

	public static void defineConnection(String line)
	{
		String[] dirs = {"NORTH", "SOUTH", "EAST", "WEST"};
		String [] words = line.split(" ");
		String direction = "";
		String room1;
		String room2;
		Room room2_obj = null;

		// find direction
		for (int i = 0; i < words.length; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				if (dirs[j].equals(words[i].toUpperCase()))
					direction = dirs[j];
			}
		}
	
		// find rooms	
		room1 = line.substring(0,line.indexOf(direction)-1);
		room2 = line.substring(line.indexOf(direction) + direction.length() + 1);

		// find room2 obj
		for (int i = 0; i < roomList.size(); i++)
		{
			if (room2.equals(roomList.get(i).get_name()))
				room2_obj = roomList.get(i);
		}

		// make connection
		for (int i = 0; i < roomList.size(); i++)
		{
			if (room1.equals(roomList.get(i).get_name()))
				roomList.get(i).set_connection(direction, room2_obj); 
		}	
			
	}

	public static void defineMob(BufferedReader reader)
	{
		String line;
		String category;
		String[] words;

		String name = "";
		String description;		
		int power;

		Mob mob = new Mob();

		try
		{
			// read mob definition until "end"
			while (!(line = reader.readLine()).trim().equals("end"))
			{
				line = line.trim();
				words = line.split(" ");
					
				category = words[0].toUpperCase();

				if (category.equals("NAME:"))
				{
					name = words[1];
					mob.set_name(name);
				}
				else if (category.equals("DESCRIPTION:"))
				{
					description = line.substring(category.length()+1);
					mob.set_description(description);
				}
				else if (category.equals("POWER:"))
				{	
					power = Integer.parseInt(words[1]);
					mob.set_power(power);
				}
			}

			// add mob to list, exit if duplicate exists
			for (int i = 0; i < mobList.size(); i++)
			{
				String n = mobList.get(i).get_name();
				if (name.equals(n))
				{
					System.err.println("Mob defined earlier in file. Exiting.");
					System.exit(1);
				}
			}
			mobList.add(mob);
 
		}
		catch (Exception e)
		{
			System.err.println("Error when defining mob.");
		}
		

	}

	public static void defineTreasure(BufferedReader reader)
	{
		String line;
		String category;
		String[] words;

		String name = "";
		float weight;	
		int value;

		Treasure treasure = new Treasure();

		try
		{
			// read treasure definition until "end"
			while (!(line = reader.readLine()).trim().equals("end"))
			{
				line = line.trim();
				words = line.split(" ");
					
				category = words[0].toUpperCase();

				if (category.equals("NAME:"))
				{
					name = line.substring(category.length()+1);
					treasure.set_name(name);
				}
				else if (category.equals("WEIGHT:"))
				{
					weight = Float.parseFloat(words[1]);
					treasure.set_weight(weight);
				}
				else if (category.equals("VALUE:"))
				{	
					value = Integer.parseInt(words[1]);
					treasure.set_value(value);
				}
			}

			// add treasure to list, exit if duplicate exists
			for (int i = 0; i < treasureList.size(); i++)
			{
				String n = treasureList.get(i).get_name();
				if (name.equals(n))
				{
					System.err.println("Treasure defined earlier in file. Exiting.");
					System.exit(1);
				}
			}
			treasureList.add(treasure);
 
		}
		catch (Exception e)
		{
			System.err.println("Error when defining treasure.");
		}
		
	}

	public static void defineWeapon(BufferedReader reader)
	{
		String line;
		String category;
		String[] words;

		String name = "";
		float weight;	
		int damage;

		Weapon weapon = new Weapon();

		try
		{
			// read weapon definition until "end"
			while (!(line = reader.readLine()).trim().equals("end"))
			{
				line = line.trim();
				words = line.split(" ");
					
				category = words[0].toUpperCase();

				if (category.equals("NAME:"))
				{
					name = line.substring(category.length()+1);
					weapon.set_name(name);
				}
				else if (category.equals("WEIGHT:"))
				{
					weight = Float.parseFloat(words[1]);
					weapon.set_weight(weight);
				}
				else if (category.equals("DAMAGE:"))
				{	
					damage = Integer.parseInt(words[1]);
					weapon.set_damage(damage);
				}
			}

			// add weapon to list, exit if duplicate exists
			for (int i = 0; i < weaponList.size(); i++)
			{
				String n = weaponList.get(i).get_name();
				if (name.equals(n))
				{
					System.err.println("Weapon defined earlier in file. Exiting.");
					System.exit(1);
				}
			}
			weaponList.add(weapon);
 
		}
		catch (Exception e)
		{
			System.err.println("Error when defining weapon.");
		}
		
	}

	public static void defineRoom(BufferedReader reader)
	{
		String line;
		String category;
		String[] words;

		String name = "";
		String description;
		String[] items;
		String mob;

		Room room = new Room();

		try
		{
			// read room definition until "end"
			while (!(line = reader.readLine()).trim().equals("end"))
			{
				line = line.trim();
				words = line.split(" ");
					
				category = words[0].toUpperCase();

				if (category.equals("NAME:"))
				{
					name = line.substring(category.length()+1);
					room.set_name(name);
				}
				else if (category.equals("DESCRIPTION:"))
				{
					description = line.substring(category.length()+1);
					room.set_description(description);
				}
				else if (category.equals("ITEMS:"))
				{	
					items = line.substring(category.length()+1).split(",");
					room.set_items(items);
				}
				else if (category.equals("MOB:"))
				{
					mob = words[1];
					room.set_mob(mob);
				}
			}

			// add room to list, exit if duplicate exists
			for (int i = 0; i < roomList.size(); i++)
			{
				String n = roomList.get(i).get_name();
				if (name.equals(n))
				{
					System.err.println("Room defined earlier in file. Exiting.");
					System.exit(1);
				}
			}
			roomList.add(room);
 
		}
		catch (Exception e)
		{
			System.err.println("Error when defining room.");
		}
		
	}

}
