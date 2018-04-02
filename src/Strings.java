import java.util.Scanner;
import java.util.ArrayList;

public class Strings {
	
	public static void main (String[] args){
		
		//declare items for the bag, and the bagobject
		String[] items = new String[] {"car", "bus", "plane"};
		double[] cost = new double[] {25.00, 50.00, 154.00};
		ArrayList<BagObject> myBag = new ArrayList<BagObject>();
		
		//populate the bag
		if(items.length == cost.length){
		for(int i = 0; i < items.length; i++){
			BagObject pack = new BagObject();
			pack.setItem(items[i]);
			pack.setCost(cost[i]);
			myBag.add(pack);
			}
		}
		
		//Get items from the bag
		BagObject unPack = new BagObject();
		for(int i = 0; i < myBag.size(); i++){
			unPack = myBag.get(i);
			System.out.println(unPack);
		}
		
		
		/*Scanner input = new Scanner(System.in);	
		Car [] cars = new Car[3];
		int i = 0;
		String tempMod; 
		int tempYear = 0;
		//cars[0] = new Car();
		while(i < cars.length){
			System.out.println("Enter the car model please: ");
			tempMod = input.next();
			cars[i] = new Car();
			cars[i].setModel(tempMod);
			
			System.out.println("Enter the model year please: ");
			tempYear = input.nextInt();
			//cars[i] = new Car();
			cars[i].setYear(tempYear);
			i++;
		}
		
		for(int j = 0; j < cars.length; j++)
		System.out.println( cars[j] + " 00" + j);
	*/
	}
}

	
	

	

