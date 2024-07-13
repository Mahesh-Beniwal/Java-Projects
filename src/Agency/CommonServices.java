package Agency;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CommonServices {

	public boolean CheckBus(String source , String destination) {
			Connection conn = new DataBaseConnection().getconnection();
			String query = "SELECT * FROM BUSES WHERE SOURCE = ?  AND DESTINATION = ? ";
			try {
				PreparedStatement statement = conn.prepareStatement(query);
				statement.setString(1, source);
				statement.setString(2, destination);
				ResultSet output = statement.executeQuery();
				while(output.next()) {
					int busNumber = output.getInt("BUS_NO");
					String driverName = output.getString("DRIVER_NAME");
					int ticketPrize = output.getInt("TICKET_PRIZE");
					Date date = output.getDate("LEAVING_DATE");
					String LeavingDate = date.toString();
					System.out.println("|-----------------|---------------------|");
					System.out.println("|     Bus No      |"+busNumber+"                 |");
					System.out.println("|-----------------|---------------------|");
					System.out.println("|     Driver name |"+driverName+"               |");
					System.out.println("|-----------------|---------------------|");
					System.out.println("|	 Prize       |"+ticketPrize+"                 |");
					System.out.println("|-----------------|---------------------|");
					System.out.println("|     Date        |"+LeavingDate+"           |");
					System.out.println("|-----------------|---------------------|");
					return true;	
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		return false;
	}
	
//	--------------------------------------------------------------------------------------------------------------
	
	public void NumberOfSeatLeft(int busNumber) {
		try {
			Connection conn = new DataBaseConnection().getconnection();
			String query = "SELECT COUNT(*) FROM CAPACITY WHERE BUS_NO = ? AND AVAILABLE = ?";
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, busNumber);
			statement.setBoolean(2, true);
			ResultSet output = statement.executeQuery();
			if(output.next())
				System.out.println("The number of seat Available are :  " + output.getInt(1));
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
//	--------------------------------------------------------------------------------------------------------------
	
	public boolean checkSeatAvailability (int seatNumber  ,int busNumber ) {
		int BusCapacity = 30;
		if(seatNumber < 1 || seatNumber > BusCapacity ) return false;
		Connection conn = new DataBaseConnection().getconnection();
		String query = "SELECT AVAILABLE from CAPACITY WHERE BUS_NO = ? AND SEAT_NO = ?";
		
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, busNumber);
			statement.setInt(2, seatNumber);
			ResultSet Output = statement.executeQuery();
			if(Output.next())
			return Output.getBoolean("AVAILABLE");
			else return false;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}	
	}
	
//	--------------------------------------------------------------------------------------------------------------
	
	public void ShowSeatsOfthebus(int busNumber) {
		Connection conn = new DataBaseConnection().getconnection();
		String query = "SELECT AVAILABLE , SEAT_NO from CAPACITY WHERE BUS_NO = ? ORDER BY SEAT_NO";
		
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, busNumber);
			ResultSet Output = statement.executeQuery();
			System.out.println("|-------------------------------------------------------|");
			int i = 1;
			while(Output.next()) {
				boolean status = Output.getBoolean("AVAILABLE");
				int seatNumber = Output.getInt("SEAT_NO");
				if(!status) {
					if(seatNumber < 10)
					System.out.println("| Seat No :  "+seatNumber+"      |         Already Booked          |");
					else
					System.out.println("| Seat No : "+seatNumber+"      |         Already Booked          |");
				}
				else {
					if(seatNumber<10)
					System.out.println("| Seat No :  "+seatNumber+"      |            Available            |");
					else
					System.out.println("| Seat No : "+seatNumber+"      |            Available            |");
					
				}
			System.out.println("|-------------------------------------------------------|" );	
			}
		}
		catch(SQLException e) {
			System.err.println("There is some Issue Please try again !!!");
			e.printStackTrace();
		}
	}

	
	public void InformationOfBuses() {
		Connection conn = new DataBaseConnection().getconnection();
		try {
			String query = "Select * from buses";
			PreparedStatement statement = conn.prepareStatement(query); 
			
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				Buses B = new Buses();
				B.busNumber = result.getInt("Bus_No");
				B.source  = result.getString("Source");
				B.destination = result.getString("Destination");
				B.type = result.getString("TYpe");
				B.fuelType = result.getString("Fuel_Type");
				B.driverName = result.getString("Driver_name");
				B.dateBooked = result.getDate("Leaving_date").toString();
				B.prize = result.getInt("Ticket_Prize");
//				B.earning = result.getInt("Earning");
				B.getbusDetails();
				
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

//	--------------------------------------------------------------------------------------------------------------

	
}
