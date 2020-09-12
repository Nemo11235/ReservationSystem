public class Passenger {
	String name; // name of the passenger
	int service; // 0 for business, 1 for first class
	char preference; // W for window, C for center, and A for aisle
	String group = "N/A";
	int row = -1;
	char col = 'Z';
	
	/**
	 * Description: Default constructor of passenger
	 * 
	 * @return none.
	 */
	public Passenger()
	{
		name = "N/A";
		service = -1;
		preference = 'N';
		group = "N/A";
	}
	
	/**
	 * Description: Constructor for individual passengers
	 * 
	 * @return none.
	 */
	public Passenger(String n, int s, char pref)
	{
		name = n;
		service = s;
		preference = pref;
		group = "N/A";
	}
	
	/**
	 * Description: Constructor for passengers that belongs to a group
	 * 
	 * @return none.
	 */
	public Passenger(String n, int s, String g)
	{
		name = n;
		service = s;
		preference = 'N';
		group = g;
	}
	
	
	/**
	 * Description: Constructor for the individual passengers from reading files
	 * 
	 * @param r - row
	 * @param c - column
	 * @param n - name
	 * @return none.
	 */
	public Passenger(int r, char c, String n)
	{
		name = n;
		row = r;
		col = c;
	}
	
	/**
	 * Description: Constructor for passengers in group from reading files
	 * 
	 * @param r - row
	 * @param c - column
	 * @param n - name
	 * @param g - group name
	 * @return none.
	 */
	public Passenger(int r, char c, String n, String g)
	{
		row = r;
		col = c;
		name = n;
		group = g;
	}
	
	// getter functions
	/**
	 * Description: Getter function for name of passenger
	 * 
	 * @param name - name of the passenger;
	 * @return name.
	 */
	public String getName() { return name; }
	
	/**
	 * Description: Getter function that returns the class of service
	 * 
	 * @param service - class of service;
	 * @return service.
	 */
	public int getService() { return service; }
	
	/**
	 * Description: Getter function that returns the seat preference
	 * 
	 * @param preference - seat preference;
	 * @return preference.
	 */
	public char getpref() { return preference; }
	
	/**
	 * Description: Getter function that returns the name of the group
	 * 
	 * @param group - group name;
	 * @return group.
	 */
	public String getGroup() { return group; }
}
