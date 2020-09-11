package cs151hw1;
import java.io.InputStream;
import java.util.*; 

public class seat {
	Scanner scan = new Scanner(System.in);
	
	passenger [][] e = new passenger[10][6];
	passenger [][] f = new passenger[2][4];
	
	passenger fill = new passenger();
	
	
	public seat()
	{
		for(int r = 0; r < e.length; r++)
		{
			for(int c = 0; c < e[r].length; c++)
			{
				e[r][c] = fill;
			}
		}
		
		for(int r = 0; r < f.length; r++)
		{
			for(int c = 0; c < f[r].length; c++)
			{
				f[r][c] = fill;
			}
		}
	}
	
	public void add_one() 
	{
		// declare variables and prompt user for info of one passenger
		String name;
		int service;
		char pref;
		System.out.print("Name: ");
		name = scan.nextLine();
		
		System.out.print("Service Class: ");
		String ser = scan.nextLine();
		
		if(ser.equals("First")) service = 1;
		else service = 0;
		
		System.out.print("Seat Preference: ");
		pref = scan.nextLine().charAt(0);
		
		
		passenger temp = new passenger(name, service, pref);
		// if economy class
		if (service == 0)
		{
			if(pref == 'W')
			{
				// loop to find the seat
				for(int r = 0; r < e.length; r++)
				{
					for(int c = 0; c < e[r].length; c++)
					{
						if ((c == 0 || c == 5) && (e[r][c].getName() == "N/A"))
						{
							e[r][c] = temp;
							System.out.println("Seat reserved successfully, your seat is: "  + (r+10)+ (char)(c+65));
							return;
						}
					}
				}
				System.out.println("No empty window seat.");
				return;
			}
			else if(pref == 'C')
			{
				// loop to find the seat
				for(int r = 0; r < e.length; r++)
				{
					for(int c = 0; c < e[r].length; c++)
					{
						if ((c == 1 || c == 4) && (e[r][c].getName() == "N/A"))
						{
							e[r][c] = temp;
							System.out.println("Seat reserved successfully, your seat is: " + (r+10) + (char)(c+65));
							return;
						}
					}
				}
				System.out.println("No empty center seat.");
				return;
			}
			else if(pref == 'A')
			{
				for(int r = 0; r < e.length; r++)
				{
					for(int c = 0; c < e[r].length; c++)
					{
						if ((c == 2 || c == 3) && (e[r][c].getName() == "N/A"))
						{
							e[r][c] = temp;
							System.out.println("Seat reserved successfully, your seat is: " + (r+10) + (char)(c+65));
							return;
						}
					}
				}
				System.out.println("No empty aisle seat.");
				return;
			}
		}
		else if(service == 1) // reservation for first class
		{
			if(pref == 'W')
			{
				for(int r = 0; r < f.length; r++)
				{
					for(int c = 0; c < f[r].length; c++)
					{
						if ((c == 0 || c == 3) && (f[r][c].getName() == "N/A"))
						{
							f[r][c] = temp;
							System.out.println("Seat reserved successfully, your seat is: " + (r+1) + (char)(c+65));
							return;
						}
					}
				}
				System.out.println("No empty window seat.");
				return;
			}
			else if(pref == 'A')
			{
				for(int r = 0; r < f.length; r++)
				{
					for(int c = 0; c < f[r].length; c++)
					{
						if ((c == 1 || c == 2) && (f[r][c].getName() == "N/A"))
						{
							f[r][c] = temp;
							System.out.println("Seat reserved successfully, your seat is: " + (r+1) + (char)(c+65));
							return;
						}
					}
				}
				System.out.println("No empty aisle seat.");
				return;
			}
		}
	} // end of add_one();
	
	// function that adds a group of people
	public void add_group()
	{
		int service;
		
		System.out.print("Group Name: ");
		String group = scan.nextLine();
		System.out.print("Names: ");
		String nameList = scan.nextLine();
		System.out.print("Service Class: ");
		String ser = scan.nextLine();
		
		if(ser.equals("First")) service = 1;
		else service = 0;
		
		Vector<String> v = new Vector<String>();
		int starts = 0;
		
		// Separate the names by commas
		for(int i = 0; i < nameList.length(); i++)
		{
			if(nameList.charAt(i) == ',')
			{
				v.add(nameList.substring(starts, i));
				starts = i+1;
			}
		}
		v.add(nameList.substring(starts)); // add the last name which doesn't end with a comma
		
		// check if the remaining seats are enough for all the group members
		if(v.size() > e_left() && service == 0) 
		{
			System.out.println("Sorry, there's not enough seats for your group.");
			return; 
		}
		if(v.size() > f_left() && service == 1)
		{
			System.out.println("Sorry, there's not enough seats for your group.");
			return; 
		}
		
		// assign group members to the seats
		Iterator<String> iter = v.iterator();
		
		// start of trying to put group into economy class
		if(service == 0)
		{
			// passenger p = new passenger(iter.next(), service, group);
			int[] adjacent = new int[e.length];
			int max, cur;
			int index = 0;
			for(int r = 0; r < e.length; r++)
			{
				max = 0;
				cur = 0;
				for(int c = 0; c < e[r].length; c++)
				{
					if(e[r][c].getName() == "N/A")
					{
						cur++;
						if(cur > max) max = cur;
					}
					else
						cur = 0;
				}
				adjacent[r] = max;
			}
			
			
			int[] order = getOrder(adjacent);
			Boolean fit = false;
			for(int i = 0; i < adjacent.length; i++)
			{
				if(adjacent[i] >= v.size())
				{
					System.out.println("It fits, adjacent[" + i + "] is: " + adjacent[i] + "v.size() is: " + v.size());
					fit = true;
					for(int j = 0; j < e[i].length; j++)
					{
						if(e[i][j].getName() == "N/A" && iter.hasNext()) 
						{
							passenger p = new passenger(iter.next(), service, group);
							e[i][j] = p;
						}
					}
					return;
				}
			}
			if(fit) return; // exit the function if all the group member has been assigned to one row.
			else // if can't fit into one row, put them in the rows that has the largest adjacent seats
			{
				System.out.println("It doesn't fit");
				for(int i = 0; i < order.length; i++)
				{
					for(int j = 0; j < e[order[i]].length; j++)
					{
						if(e[order[i]][j].getName() == "N/A" && iter.hasNext()) 
						{
								passenger p = new passenger(iter.next(), service, group);
								e[order[i]][j] = p;
						}
					}
				}
			}
			// end of putting group into economy class seats
			return;
		}
		else // start of trying to put group into first class -------------------------------------------------------------------
		{
			System.out.println("Adding stuff for first class");
			int[] adjacent = new int[f.length];
			int max, cur;
			int index = 0;
			for(int r = 0; r < f.length; r++)
			{
				max = 0;
				cur = 0;
				for(int c = 0; c < f[r].length; c++)
				{
					if(f[r][c].getName() == "N/A")
					{
						cur++;
						if(cur > max) max = cur;
					}
					else
						cur = 0;
				}
				adjacent[r] = max;
			}

			
			int[] order = getOrder(adjacent);
			Boolean fit = false;
			for(int i = 0; i < adjacent.length; i++)
			{
				if(adjacent[i] >= v.size())
				{
					System.out.println("It fits, adjacent[" + i + "] is: " + adjacent[i] + "v.size() is: " + v.size());
					fit = true;
					for(int j = 0; j < f[i].length; j++)
					{
						if(f[i][j].getName() == "N/A" && iter.hasNext()) 
						{
							passenger p = new passenger(iter.next(), service, group);
							f[i][j] = p;
						}
					}
					return;
				}
			}
			if(fit) return; // exit the function if all the group member has been assigned to one row.
			else // if can't fit into one row, put them in the rows that has the largest adjacent seats
			{
				System.out.println("It doesn't fit");
							for(int i = 0; i < order.length; i++)
							{
								for(int j = 0; j < f[order[i]].length; j++)
								{
									if(f[order[i]][j].getName() == "N/A" && iter.hasNext()) 
									{
											passenger p = new passenger(iter.next(), service, group);
											f[order[i]][j] = p;
									}
								}
							}
						}
						// end of putting group into economy class seats
						return;
		}
		
	}
	
	public int[] getOrder(int []arr)
	{
		int []copy = new int[arr.length];
		for(int i = 0; i < arr.length; i++)
		{
			copy[i] = arr[i];
		}
		int size = copy.length;
		int []result = new int[copy.length];
		for(int j = 0; j < copy.length; j++)
		{
			result[j] = j;
		}
		Boolean notSorted = true;
		while(notSorted)
		{
			notSorted = false;
			for(int i = 0; i < size - 1; i++)
			{
				if(copy[i] < copy[i + 1])
				{
					notSorted = true;
					int temp = copy[i];
					int temp2 = result[i];
					
					copy[i] = copy[i + 1];
					result[i] = result[i + 1];
					
					copy[i + 1] = temp;
					result[i + 1] = temp2;
				}
			}
			size--;
		}
		return result;
	}
	
	// function that prints all the occupied seats
	public void p_manifest()
	{
		System.out.println("First: \n");
		for(int r = 0; r < f.length; r++)
		{
			for(int c = 0; c < f[r].length; c++)
			{
				if(f[r][c].getName() != "N/A")
				{
					System.out.println("" + (r+1) + (char)(c+65) + ": " + f[r][c].getName());
				}
			}
		}
		
		System.out.println("\nEconomy: \n");
		for(int r = 0; r < e.length; r++)
		{
			for(int c = 0; c < e[r].length; c++)
			{
				if(e[r][c].getName() != "N/A")
				{
					System.out.println("" + (r+10) + (char)(c+65) + ": " + e[r][c].getName());
				}
			}
		}
	} // end of manifest();
	
	// function that prints all the available seats
	public void p_avalible()
	{
		System.out.println("First: \n");
		for(int r = 0; r < f.length; r++)
		{
			System.out.print("\n" + (r+1)+ ": ");
			for(int c = 0; c < f[r].length; c++)
			{
				if(f[r][c].getName() == "N/A") System.out.print((char)(c+65) + ",");
			}
		}
		
		System.out.println("\nEconomy: \n");
		for(int r = 0; r < e.length; r++)
		{
			System.out.print("\n" + (r+10) + ": ");
			for(int c = 0; c < e[r].length; c++)
			{
				if(e[r][c].getName() == "N/A") System.out.print((char)(c+65) + ",");
			}
		}
	}
	
	// function that returns the number of seats left in first class
	public int f_left()
	{
		int counter = 0;
		for(int r = 0; r < f.length; r++)
		{
			for(int c = 0; c < f[r].length; c++)
			{
				if(f[r][c].getName() == "N/A") counter++;
			}
		}
		return counter;
	}
	
	// function that returns the number of seats left in economy class
	public int e_left()
	{
		int counter = 0;
		for(int r = 0; r < e.length; r++)
		{
			for(int c = 0; c < e[r].length; c++)
			{
				if(e[r][c].getName() == "N/A")  counter++; 
			}
		}
		return counter;
	}
	
	public void cancel_indiv(String name)
	{
		for(int r = 0; r < e.length; r++)
		{
			for(int c = 0; c < e[r].length; c++)
			{
				if(e[r][c].getName().equals(name)) e[r][c] = fill;
			}
		}
		
		for(int r = 0; r < f.length; r++)
		{
			for(int c = 0; c < f[r].length; c++)
			{
				if(f[r][c].getName().equals(name)) f[r][c] = fill;
			}
		}
	}
	
	public void cancel_group(String name)
	{
		for(int r = 0; r < e.length; r++)
		{
			for(int c = 0; c < e[r].length; c++)
			{
				if(e[r][c].getGroup().equals(name)) e[r][c] = fill;
			}
		}
		
		for(int r = 0; r < f.length; r++)
		{
			for(int c = 0; c < f[r].length; c++)
			{
				if(f[r][c].getGroup().equals(name)) f[r][c] = fill;
			}
		}
	}
	
}
