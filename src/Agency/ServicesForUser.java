package Agency;

public interface ServicesForUser {
	public void ShowSeatsOfthebus(int busNumber);
	public boolean CheckBus(String source , String destination);
	public void NumberOfSeatLeft(int busNumber);
	public boolean checkSeatAvailability (int seatNumber , int busNumber );
	public int showTicketPrize(int busNumber);
	public int showTicketPrize(String source , String destination);
	public boolean bookTicket(String source , String destination);
	private boolean bookTicket(String source , String destination,int seatNumber);
	
}
