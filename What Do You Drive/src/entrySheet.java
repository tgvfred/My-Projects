
import java.util.Scanner;
public class entrySheet {
	
	public static void main(String[] args){
		@SuppressWarnings("resource")
		Scanner SC = new Scanner(System.in);
		
		String carType = "";
		System.out.println("What do you drive?");
		carType = SC.next();
		
		yourCar myVehicle = new yourCar(carType);
			
		System.out.println("============================================================");
		System.out.println("Ok Lets find out your personality");
		System.out.println(myVehicle.getResults());
	
	}

}
