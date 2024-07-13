package Agency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Scanner;



public class Owner extends CommonServices implements ServicesForOwner{
//	private static ArrayList<Buses> ListOfBuses = new ArrayList<Buses>();
	private Owner() {}

	public static ServicesForOwner getOwnerObject() {
		return new Owner();
	}
//	--------------------------------------------------------------------------------------------------------------
	
	@Override
	public void StartBus() {						// Here we Insert the data into the buses table ( Means Starting a Bus ).
		Scanner sc = new Scanner (System.in);
		System.out.println("\nFor Creating a Route we must have a Agency so that it will be the source to bus. \n");
		System.out.print("Enter the Bus Starting location : ");
		String source = sc.nextLine();
		
		// Here before insertion we check Weather there is a Agency or not from where we are starting a Route of the 
		if(!isAgency(source)) {
			System.out.println("We can't Create a route because there is no Agency for the loction!!");
			return;
		}
		else {
			try {
				System.out.print("Enter the bus Number : ");
				int busNumber = sc.nextInt();
				System.out.print("Enter the Date for Bookings (In such a formate yyyy-mm-dd): ");
//				System.out.println(busNumber);
				String date = sc.next();
				System.out.print("Enter the destination Location : ");
//				System.out.println(date);
				String destination = sc.next();
//				System.out.println(destination);
				System.out.print("Enter the type of bus such that ( A - AC Or N - NON-AC : )");
				String type = sc.next();
//				System.out.println(type);
				System.out.print("Enter the fuel type of the bus ( P - Petrol Or D - Diesel Or C - CNG Or E - Electrice : )");
				String fuel = sc.next();
//				System.out.println(fuel);
				System.out.print("Enter the Driver Name : ");
				String DriverName = sc.next();
//				System.out.println(DriverName);
				System.out.print("Enter the fair for the Bus ticket : ");
				double prize = sc.nextDouble();
//				System.out.println(prize);
				
				// Here we Validate Some Critical inputs of the owner.
				if(!checkAllValues(date , type , fuel)){
					System.out.println("You Enter the Wrong values Please try again !!  ");
//					StartBus();
					return;
				}
				else {
					fuel = (fuel.charAt(0) == 'P' || fuel.charAt(0) == 'p')? "PETROL" : (fuel.charAt(0) == 'C' || fuel.charAt(0) == 'c')? "CNG" :"ELECTRIC";
					type = (type.charAt(0) == 'A' || type.charAt(0) == 'a')? "AC" : "NON-AC";
					Buses B1 = new Buses( busNumber, date, source, destination, DriverName, type, fuel , prize);
//					ListOfBuses.add(B1);
				}
			}
			catch(Exception e) {
				System.out.println("Entered the wrong values please try again !!");
			}
		}
	}
	
//	--------------------------------------------------------------------------------------------------------------
	@Override
	public boolean isAgency(String source) {
		Connection connection = new DataBaseConnection().getconnection();
		String query = "Select location from agency where location = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, source);
			ResultSet result = statement.executeQuery();
			if(result.next()) return true;
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}	
	}
	
//	--------------------------------------------------------------------------------------------------------------
	@Override
	public boolean createAgency(String location , String managerName) { 	// HERE WE ARE Inserting the data into the Agency table. 
		Connection connection = new DataBaseConnection().getconnection();
		String query = "INSERT INTO AGENCY (LOCATION , MANAGER_NAME) VALUES (? , ?)";
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, location);
			statement.setString(2, managerName);
			if(statement.executeUpdate() > 0) return true;
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
//	--------------------------------------------------------------------------------------------------------------
	// The logic of this function is correct..
	private boolean checkAllValues(String date , String type , String fuel ) {
		SimpleDateFormat DF = new SimpleDateFormat("yyyy-mm-dd");
		try {
			java.util.Date Date = DF.parse(date);
		}
		catch(ParseException e){
			e.printStackTrace();
			System.out.println("kajfkajk");
			return false;	
		}
		if(!(type.charAt(0) == 'A' || type.charAt(0) == 'N' || type.charAt(0) == 'a' || type.charAt(0) == 'n')) {
			System.out.println("here");
			return false;
		}
		if(!(fuel.charAt(0) == 'P'||fuel.charAt(0) == 'D'||fuel.charAt(0) == 'E'||fuel.charAt(0) == 'C' || fuel.charAt(0) == 'p'||fuel.charAt(0) == 'd'||fuel.charAt(0) == 'e'||fuel.charAt(0) == 'c')) {
			System.out.println("theere");
			return false;
		}
		return true;
	}

//	--------------------------------------------------------------------------------------------------------------

	@Override
	public double TotalSaleOnDate(String date) {
		LocalDate Ldate = LocalDate.parse(date);
		Connection conn = new DataBaseConnection().getconnection();
		String query = "SELECT SUM(EARNING) FROM BUSES WHERE LEAVING_DATE10 = ?";
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setObject(1, Ldate);
			ResultSet output = statement.executeQuery();
			if(output.next()) return output.getDouble(1);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
//	--------------------------------------------------------------------------------------------------------------
	
	@Override
	public String DriverName(int BusNo) {
		try {
			Connection conn = new DataBaseConnection().getconnection();
			String query = "SELECT DRIVER_NAME FROM BUSES WHERE BUS_NO = ?";
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, BusNo);
			ResultSet output = statement.executeQuery();
			if(output.next())
				return output.getString(1);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}

//	--------------------------------------------------------------------------------------------------------------
	
//	@Override
//	public int getBusNumber(String driverName) {
//		try {
//			Connection conn = new DataBaseConnection().getconnection();
//			String query = "SELECT BUS_NO FROM BUSES WHERE DRIVER_NAME = ?";
//			PreparedStatement statement = conn.prepareStatement(query);
//			statement.setString(1, driverName);
//			ResultSet output = statement.executeQuery();
//			if(output.next())
//				return output.getInt(1);
//		}
//		catch(SQLException e)
//		{
//			e.printStackTrace();
//		}
//		return 0;
//	}
//	
//	--------------------------------------------------------------------------------------------------------------

	
//	--------------------------------------------------------------------------------------------------------------
//	 Can't use this method.
	
	// This function provide a vacant seat for the Customer
//	public static int getPossibleseat(int busNumber) {
//		for(Buses B : ListOfBuses) {
//			if(B.busNumber == busNumber) {
//				return B.C.getFirstPossibleSeat();
//			}
//		}
//	}
//	--------------------------------------------------------------------------------------------------------------
	
}
