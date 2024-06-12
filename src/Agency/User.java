package Agency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User extends CommonServices implements ServicesForUser{

	String UserName;
	int age;
	long phone;
	
	public User(String UserName , int age , long phone) {
		this.age = age;
		this.phone = phone;
		this.UserName = UserName;
	}
	
	@Override
	public int showTicketPrize(int busNumber) {
		try {
			Connection conn = new DataBaseConnection().getconnection();
			String query = "SELECT TICKET_PRIZE FROM BUSES WHERE BUS_NO = ?";
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, busNumber);
			ResultSet output = statement.executeQuery();
			if(output.next()) {
				System.out.println("The Prize of the tickect is : " +output.getInt(1) + "Rs");
				return output.getInt(1);
			}
			return 0;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int showTicketPrize(String source, String destination) {
		try {
			Connection conn = new DataBaseConnection().getconnection();
			String query = "SELECT TICKET_PRIZE FROM BUSES WHERE SOURCE = ? AND DESTINATION = ?";
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, source);
			statement.setString(2, destination);
			ResultSet output = statement.executeQuery();
			if(output.next()) {
				System.out.println("The Prize of the tickect is : " +output.getInt(1) + "Rs");
				return output.getInt(1);
			}
				
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	// These function perform the transactions.
	@Override
	public boolean bookTicket(String source , String destination) {
		
		return false;
	}

	@Override
	public boolean bookTicket(String source , String destination,int seatNumber) {
		
		int prize =showTicketPrize(source , destination);
		int busNumber = getBusNumber(source , destination);
		boolean isSeatAvailable = isAvailable(busNumber , seatNumber);
		if(prize > 0 && isSeatAvailable)
		 try {
			 
			 Connection connection = new DataBaseConnection().getconnection();
			 if(age < 12) {
				 prize = prize/2;			// Giving a half ticket for the child based on the age.
			 }
			 String AddMoneyToTotalEarning = "INSERT INTO AGENCY TOTAL_EARNING VALUES ?";
			 String AddMoneyToBusAccount = "INSERT INTO BUSES EARNING VALUES ?";
			 String AddSeatNumber = "INSERT INTO CAPACITY AVAILABLE VALUES ? WHERE SEAT_NO = ? AND BUS_NO = ?";
			 String AddUser = "INSERT INTO USER USER_NAME , BUS_NO , SEAT_NO , AGE , PHONE_NO , FINAL_TICKET_PRIZE VALUES ? , ? , ? , ? , ? , ?";
			 
			 
			 
			 PreparedStatement statement = connection.prepareStatement(AddMoneyToBusAccount);
			 statement.setInt(1, prize);
			 statement.addBatch();
//			 1st Query 
			 
			 statement = connection.prepareStatement(AddMoneyToTotalEarning);
			 statement.setInt(1, prize);
			 statement.addBatch();
//			 2nd Query
			 
			 statement = connection.prepareStatement(AddSeatNumber);
			 statement.setBoolean(1, false);
			 statement.setInt(2, seatNumber);
			 statement.setString(3, source);
			 statement.setString(4, destination);
			 statement.addBatch();
//			 3rd Query
			 
			 
			 
			 
		 }catch(SQLException e) {
			 e.printStackTrace();
		 }
		System.out.println("Not able to Book a Ticket for You !!!");
		return false;
	}
	private int getBusNumber(String source , String destination) {
		try {
			Connection connection = new DataBaseConnection().getconnection();
			String query = "SELECT BUS_NO FROM BUSES WHERE SOURCE = ? AND DESTINATION = ? ";
			
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, source);
			statement.setString(2, destination);
			ResultSet output = statement.executeQuery();
			if(output.next()) {
				return output.getInt(1);
			}
			return 0;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	private boolean isAvailable(int busNumber , int seatNumber) {
		try {
			Connection connection = new DataBaseConnection().getconnection();
			String query = "SELECT AVAILABLE FROM CAPACITY WHERE BUS_NO = ? AND SEAT_NO = ? ";
			
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, busNumber);
			statement.setInt(2, seatNumber);
			ResultSet output = statement.executeQuery();
			if(output.next()) {
				return output.getBoolean(1);
			}
			return false;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
