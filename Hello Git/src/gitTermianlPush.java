import java.util.ArrayList;

public class gitTermianlPush {
	public static void main (String[]args) {
		System.out.println("This si a common Fizz Buzz Program");
		
		//Populate an array from 1 to 100
		ArrayList<Integer> fizzArray = new ArrayList<Integer>();
		
		for(int i = 1; i <= 100; i++) {
			fizzArray.add(i);
		}
		 // check array contents for values of only 1-100
		System.out.println(fizzArray.toString());
		
		//Print 'Fizz' for mutiples of 3, 'Buzz' for multiples of 5
		//and 'FizzBuzz' for multiples of 3 and 5
		for(int i = 0; i < fizzArray.size(); i++) {
			if(fizzArray.get(i) % 3 == 0 && fizzArray.get(i) % 5 == 0) {
				System.out.print(fizzArray.get(i));
				System.out.println(" FizzBuzz");
			}
			else if(fizzArray.get(i) % 3 == 0) {
				System.out.print(fizzArray.get(i));
				System.out.println(" Fizz");
			}
			else if(fizzArray.get(i) % 5 == 0) {
				System.out.print(fizzArray.get(i));
				System.out.println(" Buzz");
			}
		}
	}

}
