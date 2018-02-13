import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
public class inventoryUI {

	
	public static void main (String[] args){
		ArrayList<String>searchIdentNum = new ArrayList<String>();
		Scanner LK = new Scanner(System.in);
		int itemCount = 0;
		int count = 0;
		String UPC = "";
		String SKU = "";System.out.println("============================================================================");
		System.out.println("Enter The Number of Items you want to lookup:");
		itemCount = LK.nextInt();
		
		
		
		for(int i = 0;i < itemCount; i++){
			count++;
			System.out.println("============================================================================");
			System.out.println("Enter the SKU number of item " + count + " you want to search or enter 'UPC': ");
			SKU = LK.next();
			
			
			if(SKU.equalsIgnoreCase("UPC")){
				System.out.println("============================================================================");
				System.out.println("You indicated you have the UPC..Please enter the UPC of item " + count + ": ");
				UPC = LK.next();
				searchIdentNum.add(UPC);
				
			}
			else{
				searchIdentNum.add(SKU);
			}
			
		}
		
		containerClass CoC = new containerClass(searchIdentNum);
		CoC.addItems();
		
			
			System.out.println(CoC.getInventSelection());
	}
}
