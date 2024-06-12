package Agency;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.xdevapi.Result;

public class Owner extends CommonServices implements ServicesForOwner{
	static ArrayList<Buses> ListOfBuses = new ArrayList<Buses>();
	
//	--------------------------------------------------------------------------------------------------------------
	
	@Override
	public void StartBus() {						// Here we Insert the data into the buses table ( Means Starting a Bus ).
		Scanner sc = new Scanner (System.in);
		System.out.println("For Creating a Route we must have a Agency to handle the source \n");
		System.out.print("Enter the Bus Starting location : ");
		String source = sc.nextLine();
		
		// Here before insertion we check Weather there is a Agency or not from where we are starting a Route of the 
		if(!isAgency(source)) {
			System.out.println("We can't Create a route because there is no Agency for the loction!!");
			return;
		}
		else {
			try {
				System.out.print("Enter the bus number : ");
				int busNumber = sc.nextInt();
				System.out.print("Enter the Date for Bookings (In such a formate yyyy-mm-dd): ");
				String date = sc.nextLine();
				System.out.print("Enter the destination Location : ");
				String destination = sc.nextLine();
				System.out.print("Enter the type of bus such that ( A - AC Or N - NON-AC : )");
				String type = sc.nextLine();
				System.out.print("Enter the fuel type of the bus ( P - Petro Or D - Diesel Or C - CNG Or E - Electrice : )");
				String fuel = sc.nextLine();
				System.out.print("Enter the Driver Name : ");
				String DriverName = sc.nextLine();
				System.out.print("Enter the fair for the Bus ticket : ");
				double prize = sc.nextDouble();
				
				// Here we Validate Some Critical inputs of the owner.
				if(!checkAllValues(date , type , fuel)){
					System.out.println("You Enter the Wrong values Please try again !!  ");
					StartBus();
					return;
				}
				else {
					Buses B1 = new Buses(busNumber, date, source, destination, DriverName, type, fuel , prize);
					ListOfBuses.add(B1);
				}
			}
			catch(Exception e) {
				
			}
		}
	}
	
//	--------------------------------------------------------------------------------------------------------------
	
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
	
	public boolean checkAllValues(String date , String type , String fuel ) {
		SimpleDateFormat DF = new SimpleDateFormat("yyyy-mm-dd");
		try {
			java.util.Date Date = DF.parse(date);
		}
		catch(ParseException e){
			e.printStackTrace();
			return false;	
		}
		if(!(type.charAt(0) == 'A' || type.charAt(0) == 'N' || type.charAt(0) == 'a' || type.charAt(0) == 'n')) return false;
		if(!(fuel.charAt(0) == 'P'||fuel.charAt(0) == 'D'||fuel.charAt(0) == 'E'||fuel.charAt(0) == 'C')) return false;
		return true;
	}

//	--------------------------------------------------------------------------------------------------------------

	@Override
	public double TotalSaleOnDate(String date) {
		
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
	
	@Override
	public int getBusNumber(String driverName) {
		try {
			Connection conn = new DataBaseConnection().getconnection();
			String query = "SELECT BUS_NO FROM BUSES WHERE DRIVER_NAME = ?";
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, driverName);
			ResultSet output = statement.executeQuery();
			if(output.next())
				return output.getInt(1);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	
//	--------------------------------------------------------------------------------------------------------------

	@Override
	public void InformationOfBuses() {
		for(Buses bus : ListOfBuses) {
			bus.getbusDetails();
		}
	}

//	--------------------------------------------------------------------------------------------------------------
	
}
