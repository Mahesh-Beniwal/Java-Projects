package Agency;

import java.util.Scanner;

public class UserAccess {
	public static void main(String[] args) {
		System.err.println("---------------------Welcome into the Bus Agency---------------------");
		System.out.println("Please Enter the Details!!!");
		System.out.println();
		System.out.println();
		
		String name = getUserName();
		int age = getAgeOfUser();
		long phoneNumber = getNumber();
		
		
		
		ServicesForUser user = User.getUserObject(name, age, phoneNumber);

		while(true) {
			System.out.println();
			System.out.println("|------------------------------------Choose the Service from the menu.------------------------------------------|");
			System.out.println("|														|");
			System.out.println("|Press 1  : To See a Bus details for a Routee.		|  Press 2   : To Get Number of Available Seats	 	|");
			System.out.println("|Press 3  : To Check a Seat Number is Availablity   	|  Press 4   : To See all Seats of the bus		|");
			System.out.println("|Press 5  : To Get the Ticket Prize with bus Number 	|  Press 6   : To Get Ticket Prize of a Journey		|");
			System.out.println("|Press 7  : To Get a Book a general Seat  		|  Press 8   : To Book a Particular Seat		|");
			System.out.println("|Press 9  : To see all The Buses details 		|							|");
			System.out.println("|														|");
			System.out.println("|------------------------------------------- Press 0   : To Exit------------------------------------------------|");
			Scanner sc = new Scanner(System.in);
			char input = sc.next().charAt(0);
			if( input == '0') break;
			
			switch(input) {
			case '1':{
				System.out.print("Enter the Source Loctaion : ");
				String source = sc.next();
				System.out.print("Enter the Destination Location : ");
				String destination = sc.next();
				if(user.CheckBus(source, destination)) {
					System.out.println("|				These are the bus details for this route					|");
				}
				else {
					System.out.println("|				Their is no bus ready for this route						|");
				}
				break;
			}
			case '2':{
				System.out.println("Enter the Bus Number : ");
				int busNumber = sc.nextInt();
				user.NumberOfSeatLeft(busNumber);
				break;
			}
			case '3':{
				System.out.println("Enter the Bus Number : ");
				int busNumber = sc.nextInt();
				System.out.println("Enter the Seat Number want to check : ");
				int seatNumber = sc.nextInt();
				if(user.checkSeatAvailability(seatNumber, busNumber)) {
					System.out.println("This Seat is Available for Booking :) ");
				}
				else {
					System.out.println("Sorry, this seat is already booked :( ");
				}
				break;
			}
			case '4':{
				System.out.println("Enter the bus Number : ");
				int busNumber = sc.nextInt();
				user.ShowSeatsOfthebus(busNumber);
				break;
			}
			case '5':{
				System.out.println("Enter the Bus Numbere : ");
				int busNumber = sc.nextInt();
				int price = user.showTicketPrize(busNumber);
				System.out.println("There will be a discount of 50% for kids then this ticket will be of "+price/2);
				break;
			}
			case '6':{
				System.out.println("Enter the Source : ");
				String source = sc.next();
				System.out.println("Enter the Destination : ");
				String destination = sc.next();
				
				int price = user.showTicketPrize(source, destination);
				if(price < 0 ) {
					System.out.println("There is no bus for this Route !!");
					break;
				}
//				System.out.println("The price of the Ticket is : "+price);
				System.out.println();
				System.out.println("There will be a discount of 50% for kids then this ticket will be of "+price/2);
				break;
			}
			case '7':{
				System.out.println("Enter the Source : ");
				String source = sc.next();
				System.out.println("Enter the Destination : ");
				String destination = sc.next();
				System.out.println("Enter the Date (yyyy-mm-dd) : ");
				String date = sc.next();
				if(user.bookTicket(source, destination, date)) {
					System.out.println("Seat Booked Successfully !!!");
				}
				else {
					System.out.println("Booking Failed !!!");
				}
				break;
			}
			case '8':{
				System.out.println("Enter the Source : ");
				String source = sc.next();
				System.out.println("Enter the Destination : ");
				String destination = sc.next();
				System.out.println("Enter the Date (yyyy-mm-dd) : ");
				String date = sc.next();
				System.out.println("Enter the Seat Number : ");
				int seatNumber = sc.nextInt();
				if(user.bookTicket(source, destination, seatNumber,date)) {
					System.out.println("Seat Booked Successfully !!!");
				}
				else {
					System.out.println("Booking Failed !!!");
				}
				break;
			}
			case '9':{
				User obj = (User)user;
				obj.InformationOfBuses();
				break;
			}
			default :{
				System.out.println("Please Enter a Valid Service Number !!!");
			}

			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	

	
	private static String getUserName() {
		System.out.println("Enter Your Name : ");
		Scanner sc = new Scanner(System.in);
		String name = sc.nextLine();
		if(name.equals("\n")) {
			System.out.println("Entered the Wrong name !!! ");
			return getUserName();
		}
		return name;
	}
	
	private static int  getAgeOfUser() {
		System.out.println("Enter Your age : ");
		Scanner sc = new Scanner(System.in);
		try {
			int age  = sc.nextInt();
			if(age < 0 || age > 120) {
				System.out.println("Please Enter a Valid Age !!!");
				return getAgeOfUser();
			}
			return age;
		}
		catch(Exception e) {
			System.out.println("Please Enter a Valid AGE !!!");
		}
		return getAgeOfUser();
	}
	
	private static long getNumber() {
		System.out.println("Enter Your 6 digit Mobile Number : ");
		Scanner sc = new Scanner(System.in);
		try {
		
			long number = sc.nextLong();
			if(number <99999 || number > 999999 ) {
				System.out.println("Please Enter a Valid Mobile Number !!!");
				return getNumber();
			}
				return number;
		}
		catch (Exception e) {
				System.out.println("Please Enter a Valid Mobile Number !!!");
				return getNumber();
		}
	}
}
