import java.io.InputStream;
import java.util.*; 
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileWriter;


/**
 * Description: This is a class that keeps track of the information of each passenger on the plane and it 
 * contains function that allows user to add/remove/check passenger. It will save the reservations
 * in a .txt file after it runs and every time it runs it will read the data from the previous run,
 * so that user can take further action on the previous data.
 *
 */
public class Seat {
	Scanner scan = new Scanner(System.in);
	
	Passenger [][] e = new Passenger[10][6];
	Passenger [][] f = new Passenger[2][4];
	
	Passenger fill = new Passenger();
	
	/**
	 * Description: Default constructor that fills all the seats with default values of 
	 * a passenger object.
	 * 
	 * @return none.
	 */
	public Seat()
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
		ReadFile();
	}
	
	/**
	 * Description: Function that adds one passenger to the plane. 
	 * 
	 * @param name - string that holds the name of the passenger
	 * @param service - integer that holds the class of service of the passenger
	 * @param pref - char that holds the seat preference of the passenger
	 * @return none
	 */
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
		
		
		Passenger temp = new Passenger(name, service, pref);
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
	
	/**
	 *  Description: Function that adds a group of passenger to the plane. The function will find possible row that is sufficient for all the group
	 *  members, if such row doesn't exist, the function will put members in the group into the rows that has the most
	 *  adjacent seats. The function won't seat any passenger if there's not enough seats for all group members.
	 *  
	 *  @param group - name of the group;
	 *  @param nameList - String that holds all the names in the group, separated by comma;
	 *  @param ser - class of service;
	 *  @param v - vector that holds all the names in the group;
	 *  @param starts - holds index of where a name starts in nameList, help the substring function to get the names;
	 *  @param iter - iterator that help traversing the vector v;
	 *  @param max - the max number of adjacent seats in a row;
	 *  @param cur - current maximum adjacent seats in a row;
	 *  @param order - array that holds the order of row in which the group member should be placed, if a 
	 *  row that has sufficient adjacent seats for the whole group doesn't exist, order will be used;
	 *  @param adjacent - array that holds the maximum number of adjacent seats of each row;
	 *  @param fit - boolean that indicates whether a perfect row for a group exist or not;
	 *  @param p - passenger object that will holds the info of a passenger in a group that is going into the seat;
	 *  @return none.
	 */
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
					fit = true;
					for(int j = 0; j < e[i].length; j++)
					{
						if(e[i][j].getName() == "N/A" && iter.hasNext()) 
						{
							Passenger p = new Passenger(iter.next(), service, group);
							e[i][j] = p;
						}
					}
					return;
				}
			}
			if(fit) return; // exit the function if all the group member has been assigned to one row.
			else // if can't fit into one row, put them in the rows that has the largest adjacent seats
			{
				for(int i = 0; i < order.length; i++)
				{
					for(int j = 0; j < e[order[i]].length; j++)
					{
						if(e[order[i]][j].getName() == "N/A" && iter.hasNext()) 
						{
								Passenger p = new Passenger(iter.next(), service, group);
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
					fit = true;
					for(int j = 0; j < f[i].length; j++)
					{
						if(f[i][j].getName() == "N/A" && iter.hasNext()) 
						{
							Passenger p = new Passenger(iter.next(), service, group);
							f[i][j] = p;
						}
					}
					return;
				}
			}
			if(fit) return; // exit the function if all the group member has been assigned to one row.
			else // if can't fit into one row, put them in the rows that has the largest adjacent seats
			{
							for(int i = 0; i < order.length; i++)
							{
								for(int j = 0; j < f[order[i]].length; j++)
								{
									if(f[order[i]][j].getName() == "N/A" && iter.hasNext()) 
									{
											Passenger p = new Passenger(iter.next(), service, group);
											f[order[i]][j] = p;
									}
								}
							}
						}
						// end of putting group into economy class seats
						return;
		}
		
	}
	/**
	 * Description: This function will use bubble sort the given array by ascending order, then apply the same switches
	 * to an array with the same length but contains number correspond to the index.
	 * 
	 * @param arr - array of which the order will be found;
	 * @param copy - a copy of arr since arr is passed by reference;
	 * @param size - length of arr;
	 * @param result - the resulting array to return;
	 * @param notSorted - boolean indicates if copy is sorted, part of bubble sort;
	 * @param temp - temporary variable that helps swapping in copy;
	 * @param temp2 - temporary variable that helps swapping in result;
	 * @return result.
	 */
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
	
	/**
	 * Description: Function that prints all the occupied seats
	 * 
	 * @return none
	 */
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
	
	/**
	 * Description: Function that prints all the available seats.
	 * 
	 * @return none.
	 */
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
	
	/**
	 * Description: Function that returns the number of seats left in first class
	 * 
	 * @param counter - integer that counts the number of seats left int the first class
	 * @return counter
	 */
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
	
	/**
	 * Description: Function that returns the number of seats left in economy class
	 * 
	 * @param counter - integer that counts the number of seats left in economy class;
	 * @return counter.
	 */
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
	
	/**
	 * Description: Function that cancel an individual passenger
	 * 
	 * @param name - the name of passenger that will be canceled;
	 * @return none.
	 */
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
	
	/**
	 * Description: Function that cancels all the members in a group
	 * 
	 * @param name - name of the group that the user wants to cancel;
	 * @return none;
	 */
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
	
	
	/**
	 * Description: Function that reads the data from previous run. If file was not initiated yet, 
	 * this function will create one.
	 * 
	 * @param CL34 - file object; 
	 * @param title - title of the file;
	 * @param data - one line of data;
	 * @return none.
	 */
	public void ReadFile ()
	{
		try 
		{
			File CL34 = new File("CL34.txt");
			Scanner scan = new Scanner(CL34);
			String title = scan.nextLine();
			while(scan.hasNextLine())
			{
				String data = scan.nextLine();
				addFromFile(getInfo(data));
			}
			scan.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File CL34 not found.");
			makeFile();
		}
	}
	
	
	public void addFromFile(Passenger p)
	{
		if(p.row < 10)
		{
			f[p.row - 1][(int)(p.col-65)] = p;
		}
		else
		{
			e[p.row - 10][(int)(p.col-65)] = p;
		}
	}
	
	/**
	 * Description: This function will get the corresponding info of a passenger in each line
	 * of the data file
	 * 
	 * @param str - one line in CL34 data file
	 * @param row - number of row
	 * @param col - character of column 
	 * @param group - name of the group
	 * @param name - name of the passenger
	 * @return toReturn - a passenger object to be added
	 */
	public Passenger getInfo(String str)
	{
		int starts = 0;
		String seat_num = "";
		String group_name = "";
		String name = "";
		Boolean in_group = false;
		int row;
		char col;
		
		
		Vector<Integer> v = new Vector<Integer>();
		
		for(int i = 0; i < str.length(); i++)
		{
			if(str.charAt(i) == ',') v.add(i);
		}
		
		seat_num = str.substring(0, v.get(0));
		
		if(v.size() == 2) // only 2 commas, this is individual
		{
			name = str.substring(v.get(1)+2);
		}
		else // group member
		{
			group_name = str.substring(v.get(1) + 2, v.get(2));
			name = str.substring(v.get(2) + 2);
			in_group = true;
		}
		
		if(seat_num.length() == 3)
		{
			row = Integer.parseInt(seat_num.substring(0,2));
			col = seat_num.charAt(2);
		}
		else
		{
			row = Character.getNumericValue(seat_num.charAt(0));
			col = seat_num.charAt(1);
		}
		
		if(in_group)
		{
			return new Passenger(row, col, name, group_name);
		}
		else
		{
			return new Passenger(row, col, name);
		}
		
	}
	
	/**
	 * Description: This function will create a new file names CL34 with an initiate line
	 * 
	 * @param file - file to read
	 * @param myWriter - FileWriter object that reads the file
	 * @return none;
	 */
	public void makeFile()
	{
		try 
		{
			File file = new File("CL34.txt");
			if(file.createNewFile())
			{
				FileWriter myWriter = new FileWriter("CL34.txt");
				System.out.println("CL34 doesn't exist, empty CL34.txt file created successfully.");
				myWriter.write("First 1-2, Left: A-B, Right: C-D; Economy 10-29, Left: A-C, Right: D-F\n");
				myWriter.close();
			}
			else
			{
				System.out.println("File already exists.");
			}
		}
		catch(IOException e)
		{
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
}
