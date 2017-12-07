/*
* David Weinflash
* Tim Root
* Section 001B
*
* Assignment #12 - The Adventure, CSc 210, Fall 2017
* This class acts as a base class for players.
*/

import java.util.*;

public class Player {

	private int carry_limit;
	private int power_level;
	private int moves;
	private int score_denom;
	private int carry_amount;
	private ArrayList<Item> inventory;

	Player()
	{
		/*
		* Constructor
		* Creates a Player object as base class.
		*
		* Arguments: None
		*
		* Return value - Player object with default attributes.
		*/

		carry_limit = 20;
		power_level = 0;
		moves = 0;
		score_denom = 1;
		carry_amount = 0;
		inventory = new ArrayList<Item>();
	}

	public boolean pickup(Item obj)
	{
		/*
		* pickup
		* Adds item to players inventory. Return true if successful,
		* false if otherwise.
		*
		* Arguments: Item obj - Treasure, Weapon
		*
		* Return value - True if successful, false if otherwise.
		*/

		// return true if player can carry item,
		// otherwise return false

		float total_weight = obj.get_weight() + carry_amount;

		if (total_weight < carry_limit)
		{
			inventory.add(obj);
			carry_amount += obj.get_weight();
			return true;
		}
		else
		{
			System.out.println("I cannot carry this item.");
			return false;
		}
	}

	public Item drop(String item_name)
	{
		/*
		* drop
		* Returns an item from player's inventory. Return item
		* if successful, otherwise return null.
		*
		* Arguments: String item_name - Treasure, Weapon
		*
		* Return value - Item obj if successful, null if not.
		*/

		Item obj;

		for (int i = 0; i < inventory.size(); i++)
		{
			obj = inventory.get(i);			
			if (obj.get_name().toUpperCase().equals(item_name))
			{
				inventory.remove(i);
				carry_amount -= obj.get_weight();
				return obj;
			}
		}
		
		// item not found in inventory	
		System.out.println("Invalid item.");
		return null;
	}

	public void get_inventory()
	{
		/*
		* get_inventory
		* Print inventory in alphabetical order.
		*
		* Arguments: None.
		*
		* Return value - None. Print to stdout.
		*/
		
		String[] item_names = new String[inventory.size()];

		// add names to item_names
		for (int i = 0; i < inventory.size(); i++)
			item_names[i] = inventory.get(i).get_name();

		Arrays.sort(item_names);
		
		// print out inventory in order of item_names
		int i = 0;
		while (i != inventory.size())
		{
			for (int j = 0; j < inventory.size(); j++)
			{
				if (inventory.get(j).get_name().equals(item_names[i]))
				{
					inventory.get(j).print();
					System.out.println("");
					i++;
					break;
				}
			}
		}

	}

	public Weapon get_weapon(String item_name)
	{
		/*
		* get_weapons
		* Get the specified weapon from the player's inventory.
		* Print error msg and return null if weapon not found.
		*
		* Arguments: String item_name - Weapon
		*
		* Return value - Weapon object. Null if weapon not
		* in player's inventory.
		*/

		Item obj = null;

		// find weapon in inventory
		for (int i = 0; i < inventory.size(); i++)
		{
			if (inventory.get(i).get_name().toUpperCase().equals(item_name))
				obj = inventory.get(i);
		}
		
		// invalid weapon
		if ((obj == null) || !(obj instanceof Weapon))
		{
			System.out.println("Invalid weapon.");
			return null;
		}
		else
		{
			Weapon weapon = (Weapon) obj;
			return weapon;	
		}

	}

	public void set_carry_limit(int carry)
	{
		carry_limit += carry;
	}
	
	public void set_power_level(int power)
	{
		power_level += power;
	}

	public void set_score_denom(int denom)
	{
		score_denom = denom;
	}

	public void move()
	{
		moves++;
	}

	public int get_carry_limit()
	{
		return carry_limit;
	}

	public int get_score_denom()
	{
		return score_denom;
	}

	public int get_power_level()
	{
		return power_level;
	}

	public int get_moves()
	{
		return moves;
	}

	public int get_carry_amount()
	{
		return carry_amount;
	}

	public int get_score(Room stashRoom)
	{
		/*
		* get_score
		* Get player's score based on moves and treasure
		* in stash room.
		*
		* Arguments: Room stashRoom
		*
		* Return value - int representing total score.
		*/

		int total_moves = (int) Math.floor(moves / score_denom);
		int total_values = 0;
		String[] stash = stashRoom.get_items();
		String stash_item;
		
		Item obj;
		// total treasure value
		for (int i = 0; i < stash.length; i++)
		{
			stash_item = stash[i];
			obj = stashRoom.get_item(stash_item);
			if (obj instanceof Treasure)
			{
				Treasure t = (Treasure) obj;
				total_values += t.get_value();
			}
		}

		if (total_moves == 0)
			return 0;
		else
			return (total_values / total_moves);
	}

}
