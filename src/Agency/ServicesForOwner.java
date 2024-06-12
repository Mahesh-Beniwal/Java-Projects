package Agency;

public interface ServicesForOwner {
	public void StartBus();
	public boolean CheckBus(String source , String destination);
	public void NumberOfSeatLeft(int busNumber);
	public double TotalSaleOnDate(String date);
	public String DriverName(int BusNo);
	public int getBusNumber(String driverName);
	public void InformationOfBuses();
	public boolean isAgency(String source);
	public boolean createAgency(String location , String managerName);
	public boolean checkSeatAvailability (int seatNumber , int busNumber );
	public void ShowSeatsOfthebus(int busNumber);
}
