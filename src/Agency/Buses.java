package Agency;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Buses {
	int busNumber;
	String dateBooked;
	String source;
	String destination;
	String type ; // 'AC' or 'NON-AC'
	String fuelType; // 'CNG' OR 'PETRROL' OR 'DIESEL' OR 'ELECTRIC'
	String driverName;
	double prize;
	int earning;
	Capacity C;
	public Buses() {}
//	--------------------------------------------------------------------------------------------------------------
	
	public Buses( int busNumber ,String dateBooked , String source , String destination , String driverName , String type , String fuelType , double prize){
		this.busNumber = busNumber;
		this.dateBooked = dateBooked;
		this.source = source;
		this.destination = destination;
		this.type = type;
		this.fuelType = fuelType;
		this.driverName = driverName;
		this.prize = prize;
	
		if(setBusData( busNumber, dateBooked , source , destination , type , fuelType, driverName ))
		System.out.println("Bus is Ready for booking on date :"+ this.dateBooked);
		else
		System.out.println("There is some problem Please Try again !!");
	}
	
	public boolean setBusData( int busNumber, String dateBooked , String source , String destination , String type , String fuelType , String driverName) {
		
		
		try {
			Connection	connection = new DataBaseConnection().getconnection();
			String Query  = "Insert into buses ( BUS_NO ,SOURCE , DESTINATION , TYPE , FUEL_TYPE , LEAVING_DATE , TICKET_PRIZE , DRIVER_NAME) VALUES (?,?,?,?,?,?,?,?)";
			
			SimpleDateFormat Df = new SimpleDateFormat("yyyy-mm-dd");
			java.util.Date Date = Df.parse(dateBooked);
			
			
			PreparedStatement statement = connection.prepareStatement(Query);
			statement.setInt(1, busNumber);
			statement.setString(2, source);
			statement.setString(3, destination);
			statement.setString(4, type);
			statement.setString(5, fuelType);
			statement.setDate(6, new java.sql.Date(Date.getTime()));
			statement.setDouble(7, prize);
			statement.setString(8, driverName);
			// Till here the First Query is ready to Execute.
			
			if(statement.executeUpdate() > 0) {
				this.C = new Capacity(busNumber);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	
//	--------------------------------------------------------------------------------------------------------------
	
	public void getbusDetails() {
		System.out.println("|-----------------------|-------------------------|");
		System.out.println("|       Bus Number      |          "+busNumber+"		  |");
		System.out.println("|       Date            |          "+dateBooked+"  	  |");
		System.out.println("|       Starting        |          "+source+"    	  |");
		System.out.println("|       Destination     |          "+destination+ "         |");
		System.out.println("|       Type            |          "+type+"    	  |");
		System.out.println("|       Ticket Prize    |          "+prize+"    	  |") ;
		System.out.println("|       Driver          |          "+driverName+"      |");
		System.out.println("|-----------------------|-------------------------|");
	}
	
}

