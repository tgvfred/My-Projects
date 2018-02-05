import java.util.ArrayList;
import java.util.Scanner;
public class shipInterface {
	
	public static void main(String[] args){
		Scanner SC = new Scanner(System.in);
		int count = 1;
		int loopCount = 0;
		int itemAmount = 0;
		String itemType = "";
		ArrayList<String> itemList = new ArrayList<String>(); 
		
		System.out.println("========================================================================================================");
		System.out.print("Enter the amount of items you want to put in your shipping contianer: ");
		itemAmount = SC.nextInt();
		
		
		System.out.println("========================================================================================================");
		System.out.println("Enter each item for a total of " + itemAmount + " that you want to place in your container.");
	
		//pass Items into the ArrayList
		while(loopCount < itemAmount && itemAmount > 1){
			System.out.print("Item " + count + ": ");
			itemType = SC.next();
			itemList.add(itemType);
			loopCount++;
			count++;
		};
		
		
		//Return Reprint all items with detailed descriptions
		shipContainer myContain = new shipContainer(itemList);
		System.out.println(itemList.toString());
		System.out.println("=====================================================================");
		System.out.println(myContain.getContainerItemList());
	}	
	
}
