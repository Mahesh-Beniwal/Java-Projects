package Agency;

public interface ServicesForOwner {
	public void StartBus();
	public boolean CheckBus();
	public int [] SeatLeft();
	public double TotalSaleOnDate(String date);
	public String DriverName(int BusNo);
	public int BusNumber(String driverName);
	public void InformationOfBuses();
	
}
