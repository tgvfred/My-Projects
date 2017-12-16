
public class RespondingStatus{
	String emerType;
	int casAmount, sevLevel;
	
	public RespondingStatus(String eType, int cAmt, int sLev){
		emerType = eType;
		casAmount = cAmt;
		sevLevel = sLev;
	}
	
	

	public void severeEmergency(){
		System.out.println("SEVERE " + emerType + " Emergency");
		System.out.println("====================================================================================================================================");
		System.out.println("Incident reported with high casualty counts" +
		"\nRapid response procedures currently being initiated...");
		System.out.println();
		System.out.println("System will report upon first responder arrival");
		System.out.println("*******BE ADVISED*** The reported incident could involve" +
		"\n a threat to National Security...Federal Notification Procedures should be Implemented");
		
		System.out.println("====================================================================================================================================");
	}

	public void moderateEmergency(){
		System.out.println("Moderate " + emerType + " Emergency");
		System.out.println("====================================================================================================================================");
		System.out.println("Incident reported with moderate casualty counts" +
		"\nStandard response procedures currently being initiated...");
		System.out.println();
		System.out.println("System will report upon first responder arrival");
		System.out.println("====================================================================================================================================");
	}
	
	public void minorEmergency(){
		System.out.println("Minor " + emerType + " Emergency");
		System.out.println("====================================================================================================================================");
		System.out.println("Incident reported with low/no casualty counts" +
				"\nMild response procedures currently being initiated...");
		System.out.println();
		System.out.println("System will report upon first responder arrival");
		System.out.println("====================================================================================================================================");
	}
	

	
	
}
