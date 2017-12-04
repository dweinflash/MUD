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
		carry_limit = 20;
		power_level = 0;
		moves = 0;
		score_denom = 1;
		carry_amount = 0;
		inventory = new ArrayList<Item>();
	}

	public boolean pickup(Item obj)
	{
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

	public int get_score()
	{
		int total_moves = (int) Math.floor(moves / score_denom);
		int total_values = 0;

		Item obj;
		// total treasure value
		for (int i = 0; i < inventory.size(); i++)
		{
			obj = inventory.get(i);
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
