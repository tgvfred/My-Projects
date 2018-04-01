import java.util.Scanner;

public class Strings {
	
	public static void main (String[] args){
	
		Scanner input = new Scanner(System.in);	
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
			cars[i] = new Car();
			cars[i].setYear(tempYear);
			i++;
		}
		
		for(int j = 0; j < cars.length; j++)
		System.out.println( cars[j] + " 00" + j);
	
	}
}

	
	

	

