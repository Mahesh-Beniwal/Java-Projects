package Agency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;



public class Capacity {
	static final int BusCapacity = 30;
//	boolean [] arr = new boolean[BusCapacity+ 1];
	
//	--------------------------------------------------------------------------------------------------------------
	
	public Capacity(int busNumber) {
		readyTheSeats(busNumber );
//		arr[0] = false;
//		Arrays.fill(arr, true);
	}
	
//	--------------------------------------------------------------------------------------------------------------
	
	public void readyTheSeats(int busNumber) {
		Connection conn = new DataBaseConnection().getconnection(); // Here we create the Anonomus object.
		String query = "INSERT INTO CAPACITY (SEAT_NO , BUS_NO) VALUES(? , ? ) ";
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			for(int i = 1 ; i <= BusCapacity ; i++ ) {
				statement.setInt(1	, i);
				statement.setInt(2, busNumber);
				statement.addBatch();
			}
			int [] results = statement.executeBatch();
			if(results.length > 0) {
				System.out.println("Seats are ready for Booking for the Bus Number : " + busNumber );
			}
			else {
				System.out.println("There is some issue for the Seat Booking !!! Sorry for Inconvenience ");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
//	--------------------------------------------------------------------------------------------------------------
//	Can't use this method.
	
//	public int getFirstPossibleSeat() {
//		
//		for(int i = 1 ; i <= BusCapacity ; i++) {
//			if(arr[i] == true) {
//				return i; 
//			}
//		}
//		return 0 ;
//	}
	
	
//	public boolean checkSeatNo(int seatNumber) {
//		if(seatNumber > 0 && seatNumber < 31) return arr[seatNumber];
//		return false;
//	}
//	
//	
//	public void updateSeats(int seatNumber) {
//		arr[seatNumber] = false;
//	}
	
}
