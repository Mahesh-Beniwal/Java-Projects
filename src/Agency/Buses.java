package Agency;

public class Buses {
	int busNumber;
	String dateBooked;
	String source;
	String destination;
	String type ; // 'AC' or 'NON-AC'
	String fuelType; // 'CNG' OR 'PETRROL' OR 'DIESEL' OR 'ELECTRIC'
	String driverName;
	
	public Buses(int busNumber , String dateBooked , String source , String destination , String driverName , String type , String fuelType){
		this.busNumber = busNumber;
		this.dateBooked = dateBooked;
		this.source = source;
		this.destination = destination;
		this.type = type;
		this.fuelType = fuelType;
		this.driverName = driverName;
	
		if(sendData(busNumber , dateBooked , source , destination , type , fuelType, driverName ))
		System.out.println("Bus is Ready for booking on date :"+ this.dateBooked);
		else
		System.out.println("There is some problem Please Try again !!");
	}
	
	public boolean sendData(int busNumber , String dateBooked , String source , String destination , String type , String fuelType , String driverName) {
		
		return false;
	}
	
}

