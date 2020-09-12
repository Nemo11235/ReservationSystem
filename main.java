
import java.io.InputStream;
import java.util.Scanner; 

/**
 * Description: This is a program that simulates the reservation system for a plane. Users will be
 *  able to reserve or cancel seats for individual or groups, print the available seats, and print
 *   the occupied seats. There are economy class seats and first class seats, and when user is trying
 *    to reserve for an individual, he can pick the seat preference, like window seat, aisle seat,
 *     or center seat.
 * 
 * @author Zhexian Liu
 * @version 1.0
 * @assignment  CS151 Assignment 1
 */

public class main {
	
	public static void main(String[] args) {
		
		Seat s = new Seat();
		
		/*prompt the user to choose an action by entering corresponding input character*/
		System.out.println("Welcome to the reservation systrem! Please choose your action: \nAdd [P]assenger, Add [G]roup, [C]ancel Reservations, Print Seating [A]vailability Chart, "
				+ "Print [M]anifest, [Q]uit");
		
		Scanner scan = new Scanner(System.in);
		
		char choice = scan.next().charAt(0); 
		
		
		while(choice != 'Q')
		{
			if(choice == 'P')
			{
				s.add_one();
			}
			else if(choice == 'G')
			{
				s.add_group();
			}
			else if(choice == 'C')
			{
				System.out.println("Cancel [I]ndividual or [G]roup?");
				scan.nextLine();
				char cancel = scan.nextLine().charAt(0);
				if(cancel == 'I')
				{
					System.out.println("Name: ");
					String name = scan.nextLine();
					s.cancel_indiv(name);
				}
				else
				{
					System.out.println("Group Name: ");
					String group_name = scan.nextLine();
					s.cancel_group(group_name);
				}
			}
			else if(choice == 'A')
			{
				s.p_avalible();
			}
			else if(choice == 'M')
			{
				s.p_manifest();
			}
			System.out.println("\nPlease choose your action: \nAdd [P]assenger, Add [G]roup, [C]ancel Reservations, Print Seating [A]vailability Chart, "
					+ "Print [M]anifest, [Q]uit");
			choice = scan.next().charAt(0); 
		}
		
		// s.save();
		
		System.out.println("Thanks for using the reservation system! Bye!");
	}

}
