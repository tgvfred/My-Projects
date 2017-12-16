import java.util.Scanner;
public class EmergencyCard{


	public static void main(String[]args){
		Scanner input = new Scanner(System.in);
	System.out.println("\t\t\t\tEmergency Dispatcher System 2.6 G.E Systems");
	System.out.println("====================================================================================================================================");
	System.out.println();
	System.out.println();
	System.out.println();
	System.out.println("Emergency Type: |Fire|Medical|Police|");
	System.out.print("Enter: ");
	String emerType = input.nextLine();
	System.out.println();
	System.out.println();
	System.out.println("Enter Severity Level from 1-5");
	System.out.print("Enter: ");
	int severity = input.nextInt();
	System.out.println();
	System.out.println();
	System.out.println("Enter Number of Casualties");
	int casualties = input.nextInt();
	RespondingStatus reStat = new RespondingStatus(emerType,casualties,severity);
	EmergencyVehicles getVehicle = new EmergencyVehicles(emerType);
	if(emerType.equalsIgnoreCase("Medical") && severity > 3){
	reStat.severeEmergency();
	}
	else if(emerType.equalsIgnoreCase("Police") && (severity >1 && severity <= 3)){
		reStat.moderateEmergency();
		}
	else if(emerType.equalsIgnoreCase("Fire") && severity <= 1){
		reStat.minorEmergency();
		getVehicle.minorEquipment();
		}
	
	}
}
