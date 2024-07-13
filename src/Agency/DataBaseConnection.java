package Agency;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
	private static final String url = "jdbc:mysql://localhost:3306/ticketbooking";
	private static final String username = "root";
	private static final String password = "123456";
	
//	--------------------------------------------------------------------------------------------------------------
	
	public Connection getconnection() {
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");	// Here we add the program which is going to use in the program.
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			Connection c = DriverManager.getConnection(url, username, password);
			System.out.println("Connection Created");
			return c;
		} catch (SQLException e) {
			System.err.println("Connection can't Established!!");
			e.printStackTrace();
			return null;
		}
	}
//	--------------------------------------------------------------------------------------------------------------
	
}
