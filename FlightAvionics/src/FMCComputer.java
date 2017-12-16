
/* Izra Ghebres
 * Flight Management Computer
 * Calculates Performance data for a flight to four major Cities in the world in a McDonnell Douglass MD-11,
 * from Washington Dulles (KIAD) Airport
*/

import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.InputMismatchException;
import java.util.Scanner;
public class FMCComputer {
	
	public static void main (String[]args){
		String destination;
		double costIndex = 0;
		String loadType;
		Scanner inputKey = new Scanner(System.in);
		Scanner inputKey2 = new Scanner(System.in);
		int errorCount = 0;
		
		System.out.println("========================================================================================================");
		System.out.println("\t\t\t\tMcDonnell Douglass MD-11 (FMC) Ver. 96NM");
		System.out.println("BY HONEYWELL");
		//Enter and check for destination entry;
		System.out.println();
		System.out.println();
		System.out.println("Choose Destination Airport from: Miami, London, San Francisco, Seoul");
		System.out.print("Destination: ");
		destination = inputKey.nextLine();
		
		while(destination.isEmpty() && errorCount < 3) {
		
			System.out.println(" It's reccomended that you enter one of the four destinations for accurate performance calculations.");	
		destination = inputKey.nextLine();
		errorCount++;
		}
		
		//Close program or reset error counter;
		if(errorCount == 3){
			System.out.println("Error Destination");
			System.out.println("System Will Exit");
		System.exit(0);
		}
		errorCount = 0;
		
		//check the load type entry
		System.out.println();
		System.out.println();
		System.out.println("Select your load type from: Full, Medium, Light");
		System.out.print("Load Type: ");
		loadType = inputKey.nextLine();
		
		while(loadType.isEmpty() && errorCount < 3){
		System.out.println(" It's reccomended that you enter a Load Type for proper fuel calculations.");	
		loadType = inputKey.nextLine();
		errorCount++;
		}
		
		//Close program or reset error counter;
		if(errorCount == 3){
			System.out.println("Error Load Type");
			System.out.println("System Will Exit");
		System.exit(0);
		}
		errorCount = 0;
		
		//check the cost index entry
		System.out.println();
		System.out.println();
		System.out.println("Select your cost index from: 6.0 | 8.0 | 10 |");
		System.out.print("Cost Index: ");
	
			try{
				
				costIndex = inputKey.nextDouble();
			
			}
			catch(InputMismatchException Exception){
				
				System.out.println("It's Recommended you enter a cost index for aircraft to calculate performance");
				System.out.println("Cost Index: ");
				try{
				costIndex = inputKey2.nextDouble();
				}catch(InputMismatchException Exception2){
				
					System.out.println("Error Cost Index: Exiting System");
					System.exit(0);
				}
			}
			System.out.println();
			System.out.println("Checking Entry Verifications.........");
			System.out.println("====================================================================================================");
			System.out.println("Departing from Washington Dulles...KIAD");
			System.out.println();
			System.out.println();
		
				
		    //Check all variables for input accuracy
			if(destination.toLowerCase().equals("miami") || destination.toLowerCase().equals("london")
			|| destination.toLowerCase().equals("san francisco") || destination.toLowerCase().equals("seoul")){
				System.out.println("..........Destination Verified");
			}
			else{
				System.out.println("Error: Ivalid Destination Reset FMC:.........Exiting");
				System.exit(0);
			}
			System.out.println();	
			if(loadType.equalsIgnoreCase("Full") || loadType.equalsIgnoreCase("Medium") || loadType.equalsIgnoreCase("Light")){
				System.out.println(".................Load Type Verified");
			}
			else{
				System.out.println("Error: Invalid Load Type Reset FMC:..............Exiting");
				System.exit(0);
			}
			
			avionicsCompute avionComp = new avionicsCompute(destination, loadType, costIndex);
			System.out.println();
			System.out.println("\t\t\tExpected Performance");
			System.out.println("==========================================================================================================");
			System.out.println(avionComp.getDestination());
			System.out.println(avionComp.getPerformance());
		}
}
		
	class avionicsCompute{
		String destination;
		String loadType;
		double fuelNeed = 0;
		final int cruiseSpeed = 486;
		int headWind = 0;
		double ETE = 0;
		double performFuel = 0;
 		double distance = 0;
		double costIndex = 0;
		DecimalFormat fmt = new DecimalFormat("####.0");
		
		public avionicsCompute(String dest, String load, double cost){
			
			destination = dest;
			loadType = load;
			costIndex = cost;
		}
		
		public String getDestination(){
			String str = "NO VALUE";
			if(destination.equalsIgnoreCase("miami")){
				str = "Departure: Washington KIAD" + "\nArrival: Miami Intl (KMIA)";
			}
			else if(destination.equalsIgnoreCase("london")){
				str = "Departure: Washington KIAD" + "\nArrival: London Heathrow Intl (EGLL)";
			}
			else if(destination.equalsIgnoreCase("San Francisco")){
				str = "Departure: Washington KIAD" + "\nArrival: San Francisco Intl (KSAN)";
			}
			else if(destination.equalsIgnoreCase("Seoul")){
				str = "Departure: Washington KIAD" + "\nSeoul Incheon Intl (RKSI)";
			}
			
			return str;
		}
		
		public String getPerformance(){
			String str = "";
			
			if(destination.equalsIgnoreCase("miami")){
				fuelNeed = 65000;
				distance = 875;
				headWind = 39;
					if(loadType.equalsIgnoreCase("Full")){
						ETE = (distance / (cruiseSpeed - headWind) * 100);
						performFuel = fuelNeed * (.012 * costIndex);
						fuelNeed = fuelNeed - performFuel;
					}
					if(loadType.equalsIgnoreCase("Medium")){
						ETE = (distance / (cruiseSpeed - headWind) * 100);
						performFuel = fuelNeed * (.008 * costIndex);
						fuelNeed = fuelNeed - performFuel;
					}
					if(loadType.equalsIgnoreCase("Light")){
						ETE = (distance / (cruiseSpeed - headWind) * 100);
						performFuel = fuelNeed * (.005 * costIndex);
						fuelNeed = fuelNeed - performFuel;
					}
			}
			/*else if(destination.equalsIgnoreCase("london")){
					fuelNeed = 210000;
					distance = 3400;
					headWind = 75;
					if(loadType.equalsIgnoreCase("Full")){
						ETE = (distance / (cruiseSpeed + headWind) * 100);
						fuelNeed = fuelNeed * (.012 * costIndex);
					}
					else if(loadType.equalsIgnoreCase("Full")){
						ETE = (distance / (cruiseSpeed - headWind) * 100);
						fuelNeed = fuelNeed * (.012 * costIndex);
					}
					else if(loadType.equalsIgnoreCase("Full")){
						ETE = (distance / (cruiseSpeed - headWind) * 100);
						fuelNeed = fuelNeed * (.012 * costIndex);
					}
			}
			else if(destination.equalsIgnoreCase("san francisco")){
					fuelNeed = 170000;
					distance = 2300;
					headWind = 44;
					if(loadType.equalsIgnoreCase("Full")){
						ETE = (distance / (cruiseSpeed - headWind) * 100);
						fuelNeed = fuelNeed * (.012 * costIndex);
					}
			}
			else if(destination.equalsIgnoreCase("seoul")){
					fuelNeed = 420000;
					distance = 6200;
					headWind = 20;
					if(loadType.equalsIgnoreCase("Full")){
						ETE = (distance / (cruiseSpeed + headWind) * 100);
						fuelNeed = fuelNeed * (.012 * costIndex);
					}
			}*/
			
			str = "=================================================================================================" +
					"\n\nCruising Speed on Route: \t\t\t\t" + fmt.format(cruiseSpeed - headWind) + " (Knts)" +
					"\n\nEstimated Trip Time: \t\t\t\t\t" + fmt.format(ETE) + " (Min)" + 
					"\n\nDistance: \t\t\t\t\t\t" + fmt.format(distance) + " (Nm)" +
				    "\n\nExpected Fuel Burn at cost Index " + "(" + costIndex + "): \t\t" + fmt.format(fuelNeed) + " (Lbs)" +
				    "\n\nAvearage Headwind Along Route: \t\t\t\t" + headWind + " knts" +
				  "\n\n=================================================================================================";  
			
			return str;
		}
		
	}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	