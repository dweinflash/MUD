/*
* David Weinflash
* Tim Root
* Section 001B
*
* Assignment #12 - The Adventure, CSc 210, Fall 2017
* This class will parse the game file and act as the REPL
* for the Adventure game.
*/

import java.io.*;
import java.util.*;

public class Game {

	private static ArrayList<Mob> mobList = new ArrayList<Mob>();
	private static ArrayList<Treasure> treasureList = new ArrayList<Treasure>();	
	private static ArrayList<Weapon> weaponList = new ArrayList<Weapon>();
	private static ArrayList<Room> roomList = new ArrayList<Room>();
	private static Room stashRoom;
	private static Room curRoom;
	private static Player player;

	public static void main(String[] args)
	{
		/*
		* main
		* Parse the game file to define the dungeon. Ask the player to 
		* pick a character and begin the REPL. 
		*
		* Arguments: String[] args - command line arguments
		*
		* Return value - None.
		*/

		// exit if no filename in cmd line
		if (args.length < 1)
		{
			System.err.println("Invalid game file. Exiting.");
			System.exit(1);
		}
	
		// define the dungeon with game file	
		String filename = args[0];
		read_file(filename);
		stashRoom = roomList.get(0);
		curRoom = roomList.get(0);

		// introduction - choose player
		System.out.println("Welcome to the adventure!");
		System.out.println("What class would you like to play as (Warrior, Dwarf, or Ranger)?");

		Scanner sc = new Scanner(System.in);
		String line = sc.nextLine().toUpperCase();

		while (!(line.equals("WARRIOR") || line.equals("DWARF") || line.equals("RANGER")))
		{
			System.out.println("Invalid choice.");
			line = sc.nextLine().toUpperCase();
		}

		// create player
		if (line.equals("WARRIOR"))
			player = new Warrior();
		else if (line.equals("DWARF"))
			player = new Dwarf();
		else if (line.equals("RANGER"))
			player = new Ranger();

		// begin REPL
		System.out.println("Starting the adventure...");		

		String cmd;
		String[] words;
		String detail = "";
		// detail is the string after the cmd

		while (sc.hasNextLine())
		{
			line = sc.nextLine().toUpperCase();
			words = line.split(" ");
			cmd = words[0];

			if (words.length > 1)
				detail = line.substring(cmd.length()+1);
			else
				detail = "";
			
			System.out.print("> ");
			
			if (cmd.equals("LOOK"))
				curRoom.print();
			else if (cmd.equals("EXAMINE"))
				examine(detail);
			else if (cmd.equals("MOVE"))
				move(detail);
			else if (cmd.equals("PICKUP"))
				pickup(detail);
			else if (cmd.equals("DROP"))
				drop(detail);
			else if (cmd.equals("STASH"))
				stash(detail);
			else if (cmd.equals("FIGHT"))
				fight(detail);
			else if (cmd.equals("INVENTORY"))
				player.get_inventory();
			else if (cmd.equals("QUIT"))
			{
				quit();
				break;
			}
			else
				System.out.println("Invalid command.");	
		}	
		

	}
	
	public static void quit()
	{
		/*
		* quit
		* Determine final score based on character and treasure
		* in stash room. 
		*
		* Arguments: None.
		*
		* Return value - None. Prints to stdout.
		*/

		int score;
		
		score = player.get_score(stashRoom);

		System.out.println("Finishing game...");
		System.out.println("Final score: " + String.valueOf(score));
	}

	public static void fight(String detail)
	{
		/*
		* fight
		* Fight the mob in the current room with the weapon name
		* specified in argument. Print error and return if no mob 
		* found or weapon invalid. 
		*
		* Arguments: String detail - weapon name
		*
		* Return value - None. Prints to stdout the result of the fight
		* or any errors.
		*/

		Weapon weapon;
		
		// check if mob in room
		if (curRoom.get_mob().equals("none"))
		{
			System.out.println("No mob found.");
			return;
		}

		weapon = player.get_weapon(detail);

		// check if invalid weapon
		if (weapon == null)
			return;
		
		int weapon_damage = weapon.get_damage();
		weapon_damage += player.get_power_level();

		int mob_power = curRoom.get_mob_obj().get_power();
		String mob_name = curRoom.get_mob_obj().get_name();

		if (weapon_damage > mob_power)
		{
			System.out.println("You destroyed " + mob_name + " with power level " 
						+ String.valueOf(mob_power) + "!");
			curRoom.set_mob("none");
			curRoom.set_mob_obj(null);
			player.move();
		}
		else
		{
			player.move();
			System.out.println(mob_name + " defeated you! Try a different weapon.");
		}	

	}

	public static void stash(String detail)
	{
		/*
		* stash
		* Stash the item specified in argument. Return invalid command
		* if player not in stash room. 
		*
		* Arguments: String detail - item name.
		*
		* Return value - None. Prints to stdout an error or asks player
		* to drop an item in the room.
		*/

		String stashRoom_name = stashRoom.get_name();
		String curRoom_name = curRoom.get_name();

		// check if in stash room
		if (!(stashRoom_name.equals(curRoom_name)))
		{
			System.out.println("Invalid command.");
			return;
		}
		else
		{
			drop(detail);
		}
		
	}

	public static void drop(String detail)
	{
		/*
		* drop
		* Drop the item specified in argument in the cur room.
		*
		* Arguments: String detail - item name.
		*
		* Return value - None. Gets the item from player and adds
		* to room.
		*/

		Item drop_item;
		drop_item = player.drop(detail);

		if (drop_item != null)
			curRoom.add(drop_item);
	}

	public static void pickup(String detail)
	{
		/*
		* pickup
		* Pickup an item from the current room so long as no
		* mob exists and item found in room.
		*
		* Arguments: String detail - item name.
		*
		* Return value - None. Adds the item to player inventory
		* or prints error to stdout.
		*/

		String [] cur_items = curRoom.get_items();
		Item item_pickup;

		// check if item in room
		int found = 0;
		for (int i = 0; i < cur_items.length; i++)
		{
			if (cur_items[i].toUpperCase().equals(detail))
				found++;
		}

		if (found == 0)
		{
			System.out.println("Invalid item.");
			return;
		}

		// check if mob in room
		if (!(curRoom.get_mob().equals("none")))
		{
			System.out.println("You must destroy the mob first.");
			return;
		}

		// get item
		item_pickup = curRoom.get_item(detail);

		// remove item from room
		if (player.pickup(item_pickup))
			curRoom.remove(item_pickup);
	}

	public static void move(String detail)
	{
		/*
		* move
		* Move the player to room connection so long as connection
		* exists and direction is valid.
		*
		* Arguments: String detail - direction.
		*
		* Return value - None. Adjusts curRoom if command valid.
		*/

		// invalid direction
		if (!(detail.equals("NORTH") || detail.equals("SOUTH") || 
			detail.equals("EAST") || detail.equals("WEST")))
		{
			System.out.println("Invalid command.");
			return;
		}

		// reassign curRoom if connection exists
		if (curRoom.valid_connect(detail))
		{
			curRoom = curRoom.get_connection(detail);
			player.move();
		}	
		else
			System.out.println("Invalid command.");	
	
	}

	public static void examine(String detail)
	{
		/*
		* examine
		* Examine an item or mob in the current room. Print error
		* if invalid command.
		*
		* Arguments: String detail - TYPE + NAME.
		*
		* Return value - None. Prints description or error to stdout.
		*/

		// no TYPE or NAME provided
		if (detail.equals(""))
		{
			System.out.println("Invalid command.");
			return;
		}

		String[] words = detail.split(" ");

		// not enough arguments in command
		if (words.length < 2)
		{
			System.out.println("Invalid command.");
			return;
		}

		String type = words[0];
		String name = detail.substring(type.length()+1);
	
		// incorrect type
		if (!(type.equals("MOB") || type.equals("ITEM")))
		{
			System.out.println("Invalid command.");
			return;
		}

		// examine MOB
		int found = 0;
		if (type.equals("MOB"))
		{
			if (curRoom.get_mob().toUpperCase().equals(name))
			{
				for (int i = 0; i < mobList.size(); i++)
				{
					if (mobList.get(i).get_name().toUpperCase().equals(name))
					{
						mobList.get(i).print();
						found++;
						return;
					}
				}
			}
		}

		// check if item in room
		String [] cur_items = curRoom.get_items();

		int item_in_room = 0;
		for (int i = 0; i < cur_items.length; i++)
		{
			if (cur_items[i].toUpperCase().equals(name))
				item_in_room++;
		}
		
		if (item_in_room == 0)
		{
			System.out.println("Invalid command.");
			return;
		}

		// examine ITEM
		if (type.equals("ITEM"))
		{
			// search Treasure
			for (int i = 0; i < treasureList.size(); i++)
			{
				if (treasureList.get(i).get_name().toUpperCase().equals(name))
				{
					treasureList.get(i).print();
					found++;
				}
			}

			// search Weapon
			for (int i = 0; i < weaponList.size(); i++)
			{
				if (weaponList.get(i).get_name().toUpperCase().equals(name))
				{
					weaponList.get(i).print();
					found++;
				}
			}
		}

		// NAME not found
		if (found == 0)
			System.out.println("Invalid command.");

	}

	public static void read_file(String filename)
	{
		/*
		* read_file
		* Parse the game file and run different 'define' functions
		* based on first word (cmd) of each line.
		*
		* Arguments: String filename - game file.
		*
		* Return value - None. Creates a dungeon based on game file.
		*/

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
		/*
		* defineConnections
		* Define the room connections from the game file.
		*
		* Arguments: String line - line from game file.
		*
		* Return value - None. Creates a room connection based on game file.
		*/

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
		/*
		* defineMob
		* Define the mob from the game file.
		*
		* Arguments: BufferedReader reader - reader position in game file.
		*
		* Return value - None. Creates a mob obj based on game file.
		*/

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
		/*
		* defineTreasure
		* Define the treasure from the game file.
		*
		* Arguments: BufferedReader reader - reader position in game file.
		*
		* Return value - None. Creates a treasure obj based on game file.
		*/

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
		/*
		* defineWeapon
		* Define the weapon from the game file.
		*
		* Arguments: BufferedReader reader - reader position in game file.
		*
		* Return value - None. Creates a weapon obj based on game file.
		*/

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
		/*
		* defineRoom
		* Define the room from the game file.
		*
		* Arguments: BufferedReader reader - reader position in game file.
		*
		* Return value - None. Creates a room obj based on game file.
		*/

		String line;
		String category;
		String[] words;

		String name = "";
		String description;
		String[] items;
		String mob;
		Mob mob_obj = null;

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
					room.set_items(items, treasureList, weaponList);
				}
				else if (category.equals("MOB:"))
				{
					mob = words[1];
					
					// find mob obj from list
					for (int i = 0; i < mobList.size(); i++)
					{
						if (mobList.get(i).get_name().equals(mob))
							mob_obj = mobList.get(i);
					}							

					room.set_mob(mob);
					room.set_mob_obj(mob_obj);
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
