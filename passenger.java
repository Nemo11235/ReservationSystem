package cs151hw1;

public class passenger {
	String name; // name of the passenger
	int service; // 0 for business, 1 for first class
	char preference; // W for window, C for center, and A for aisle
	String group;
	
	public passenger()
	{
		name = "N/A";
		service = -1;
		preference = 'N';
		group = "N/A";
	}
	
	// constructor of passenger
	public passenger(String n, int s, char pref)
	{
		name = n;
		service = s;
		preference = pref;
		group = "N/A";
	}
	
	public passenger(String n, int s, String g)
	{
		name = n;
		service = s;
		preference = 'N';
		group = g;
	}
	
	// getter functions
	public String getName() { return name; }
	public int getService() { return service; }
	public char getpref() { return preference; }
	public String getGroup() { return group; }
}


