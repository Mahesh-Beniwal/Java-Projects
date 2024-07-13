package Agency;

import java.util.Scanner;

public class OwnerAccess { 
	public static void main(String[] args) {
		System.err.println("---------------------Welcome into the Bus Agency---------------------");
		System.out.println();
		ServicesForOwner owner = Owner.getOwnerObject();
		System.out.println();
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println();
			System.out.println("|---------------------------------Choose the Service from the menu.--------------------------------------|");
			System.out.println("|												 	 |");
			System.out.println("|Press 1  : To See a Bus details for a Routee.		|  Press 2   : To Get Number of Available Seats	 |");
			System.out.println("|Press 3  : To Check a Seat Number is Availablity	|  Press 4   : To See all Seats of the bus	 |");
			System.out.println("|Press 5  : To Start a new bus Route.			|  Press 6   : To Check where is the Agency.	 |");
			System.out.println("|Press 7  : To Create a Agency.				|  Press 8   : To See sales of the tickets.	 |");
			System.out.println("|Press 9  : To Check the Driver name of the bus.    	|  Press 10  : To See all the Details of buses.	 |");
			System.out.println("|												 	 |");
			System.out.println("|--------------------------------------- Press 0   : To Exit---------------------------------------------|");
			
			int input = sc.nextInt();
			if( input == 0) break;
			
			
			
			
			
			switch(input) {
			case 1:{
				System.out.println("Enter the Source : ");
				String source = sc.next();
				System.out.println("Enter the Destination : ");
				String destination = sc.next();
				if(owner.CheckBus(source, destination)) {
					System.out.println("|				These are the bus details for this route				|");
				}
				else {
					System.out.println("|				Their is no bus ready for this route					|");
				}
				break;
			}
			case 2:{
				System.out.println("Enter the Bus Number : ");
				int busNumber = sc.nextInt();
				owner.NumberOfSeatLeft(busNumber);
				break;
			}
			case 3:{
				System.out.println("Enter the Bus Number : ");
				int busNumber = sc.nextInt();
				System.out.println("Enter the Seat Number want to check : ");
				int seatNumber = sc.nextInt();
				if(owner.checkSeatAvailability(seatNumber, busNumber)) {
					System.out.println("This Seat is Available for Booking :) ");
				}
				else {
					System.out.println("Sorry, this seat is already booked :( ");
				}
				break;
			}
			case 4:{
				System.out.println("Enter the bus Number : ");
				int busNumber = sc.nextInt();
				owner.ShowSeatsOfthebus(busNumber);
				break;
			}
			case 5:{
				owner.StartBus();
				break;
			}
			case 6:{
				System.out.print("Enter the Location : ");
				String source = sc.next();
				if(owner.isAgency(source)) {
					System.out.println("Yes There is a Agency already Present at this Location.");
				}
				else {
					System.out.println("There is no Agency Present at this location Please Create one.");
				}
				break;
			}
			case 7:{
				System.out.println("Enter the Location of the Agency : ");
				String location = sc.next();
				System.out.println("Enter the Manager name to Manage the Agency : ");
				String managerName = sc.next();
				if(owner.createAgency(location, managerName)) {
					System.out.println("Agency Created Successfully You can now start buses from " + location);
				}
				else {
					System.out.println("There is some Issue Please Try again Later!!! ");
				}
				break;
			}
			case 8:{
				System.out.println("Enter the DATE of with Total Sales required (Must be in yyyy-mm-dd formate): ");
				String date = sc.next();
				double sales = owner.TotalSaleOnDate(date);
				if(sales == 0.0) {
					System.out.println("Either You entered the wrong date the sale of !!"+date +" = 0.0");
				}
				else {
					System.out.println("Total sale is of : " + sales +" RS");
				}
			}
			case 9:{
				System.out.println("Enter the Bus Number : ");
				int busNumber = sc.nextInt();
				String DriverName = owner.DriverName(busNumber);
				if(DriverName == null) {
					System.out.println("No such bus found");
					break;
				}
				System.out.println("Mr. "+ DriverName + " is the Driver of the bus number " + busNumber);
				break;
			}
			case 10:{
				owner.InformationOfBuses();
				break;
			}
			default :{
				System.out.println("Please Enter a Valid Service Number !!!");
			}
			
			}
		}
		
	}
}
