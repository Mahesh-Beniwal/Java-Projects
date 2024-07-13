package Agency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;



public class User extends CommonServices implements ServicesForUser{

	String UserName;
	int age;
	long phone;
	
	public static ServicesForUser getUserObject(String UserName , int age , long phone) {
		return new User(UserName , age , phone );
	}
	
//	--------------------------------------------------------------------------------------------------------------
	
	private User(String UserName , int age , long phone) {
		this.age = age;
		this.phone = phone;
		this.UserName = UserName;
	}
	
	
//	--------------------------------------------------------------------------------------------------------------

	@Override
	public int showTicketPrize(int busNumber) {
		try {
			Connection conn = new DataBaseConnection().getconnection();
			String query = "SELECT TICKET_PRIZE FROM BUSES WHERE BUS_NO = ?";
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, busNumber);
			ResultSet output = statement.executeQuery();
			if(output.next()) {
				System.out.println("The Prize of the tickect is : " +output.getInt(1) + " Rs");
				return output.getInt(1);
			}
			return 0;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

//	--------------------------------------------------------------------------------------------------------------

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
			else return 0 ;
				
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

//	--------------------------------------------------------------------------------------------------------------

	// These function perform the transactions.
	@Override
	public boolean bookTicket(String source , String destination , String date) {
		int busNumber = getBusNumber(source, destination , date);
		if(busNumber > 0 ) {
			int seatNumber = 0;
			Connection conn = new DataBaseConnection().getconnection();
			String query = "SELECT SEAT_NO FROM CAPACITY WHERE AVAILABLE = ?";
			try {
				PreparedStatement statement = conn.prepareStatement(query);
				statement.setBoolean(1, true);
				ResultSet output = statement.executeQuery();
				if(output.next()) seatNumber = output.getInt(1);
				else {
					System.out.println("No Seats are available Sorry !!");
					return false;
				}
				return bookTicket(source, destination, seatNumber, date);
			}
			catch(SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		else 
			System.out.println("NO bus is available for this particular Route !!!");
		return false;
	}

//	--------------------------------------------------------------------------------------------------------------

	@Override
	public boolean bookTicket(String source , String destination,int seatNumber , String date) {
//		System.out.println("step2");
		int prize =showTicketPrize(source , destination);
		int busNumber = getBusNumber(source , destination , date);
		boolean isSeatAvailable = checkSeatAvailability(seatNumber, busNumber);
		Connection connection = new DataBaseConnection().getconnection();
		if(prize > 0 && isSeatAvailable)
		 try {
			 
			 connection.setAutoCommit(false);
			 if(age < 12) {
				 prize = prize/2;			// Giving a half ticket for the child based on the age.
			 }
			 int userId = Objects.hash(age , phone ,UserName,date);
			 int busId = getBusId(busNumber);
			 String AddMoneyToTotalEarning = "UPDATE AGENCY SET TOTAL_EARNING = TOTAL_EARNING + ? WHERE LOCATION = ?";
			 String AddMoneyToBusAccount = "UPDATE BUSES SET EARNING = EARNING + ? WHERE BUS_NO = ?";
			 String AddSeatNumber = "UPDATE CAPACITY SET AVAILABLE = ? WHERE SEAT_NO = ? AND BUS_NO = ?";
			 String AddUser = "INSERT INTO USER (USER_Id ,USER_NAME , BUS_No , SEAT_NO , AGE , PHONE_NO , FINAL_TICKET_PRIZE) VALUES (? , ? , ? , ? , ? , ? , ?)";
			 
			 
			 
			 PreparedStatement statement = connection.prepareStatement(AddMoneyToTotalEarning);
			 statement.setInt(1, prize);
			 statement.setString(2, source);
			 int i = statement.executeUpdate();
			 if(i < 1) {
				 connection.rollback();
				 return false;
			 }
//			 1st Query 
			 
			 statement = connection.prepareStatement(AddMoneyToBusAccount);
			 statement.setInt(1, prize);
			 statement.setInt(2, busNumber);
			 i = statement.executeUpdate();
			 if(i < 1) {
				 connection.rollback();
				 return false;
			 }
//			 2nd Query
			 
			 statement = connection.prepareStatement(AddSeatNumber);
			 statement.setBoolean(1, false);
			 statement.setInt(2, seatNumber);
			 statement.setInt(3, busNumber);
			 i = statement.executeUpdate();
			 if(i < 1) {
				 connection.rollback();
				 return false;
			 }
//			 3rd Query
			 
			 statement = connection.prepareStatement(AddUser);
			 statement.setInt(1, userId);
			 statement.setString(2, UserName.toUpperCase());
			 statement.setInt(3, busId);
			 statement.setInt(4, seatNumber);
			 statement.setInt(5, age);
			 statement.setLong(6, phone);
			 statement.setInt(7, prize);
			 i = statement.executeUpdate();
			 if(i < 1) {
				 connection.rollback();
				 return false;
			 }
//			4th Query
			 
			 
			 connection.commit();
			 return true;
			 
			 
		 }catch(SQLException e) {
			 System.out.println("Not able to Book a Ticket for You !!!");
				
			 try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			 e.printStackTrace();
		 }
		return false;
		
	}

//	--------------------------------------------------------------------------------------------------------------

	private int getBusNumber(String source , String destination, String date) {
		
		
		// Here we actually changing the string dataformate into DATE formate.
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date leavingDate ;
		try {

            java.util.Date utilDate = dateFormat.parse(date);

            leavingDate = new java.sql.Date(utilDate.getTime());
        } catch (ParseException e ) {
            e.printStackTrace();
            System.out.println("Enter date in this formate yyyy-MM-dd");
            return 0 ;
        }
//	---------------------------------------------------------	
		
		try {
			Connection connection = new DataBaseConnection().getconnection();
			String query = "SELECT BUS_NO FROM BUSES WHERE SOURCE = ? AND DESTINATION = ? AND LEAVING_DATE = ?";
			
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, source);
			statement.setString(2, destination);
			statement.setDate(3, leavingDate);
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
	
	private int getBusId(int busNumber) {
		try {
			String query = "SELECT BUS_ID FROM BUSES WHERE BUS_NO = ?";
			Connection conn = new DataBaseConnection().getconnection();
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, busNumber);
			ResultSet output = statement.executeQuery();
			if(output.next()) return output.getInt("bus_id");
		}catch(SQLException e){
			e.printStackTrace();
		}
		return -1;
	}
	
//	--------------------------------------------------------------------------------------------------------------

//	
//	private boolean isAvailable(int busNumber , int seatNumber) {
//		try {
//			Connection connection = new DataBaseConnection().getconnection();
//			String query = "SELECT AVAILABLE FROM CAPACITY WHERE BUS_NO = ? AND SEAT_NO = ? ";
//			
//			PreparedStatement statement = connection.prepareStatement(query);
//			statement.setInt(1, busNumber);
//			statement.setInt(2, seatNumber);
//			ResultSet output = statement.executeQuery();
//			if(output.next()) {
//				return output.getBoolean(1);
//			}
//			return false;
//		}
//		catch(SQLException e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
	
//	--------------------------------------------------------------------------------------------------------------

}
