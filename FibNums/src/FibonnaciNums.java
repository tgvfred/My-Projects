
/*Frederick Miles Customized Fibonnaci Number Sequence,
  and Prime first ten-digit prime number ten sequential digit finder. 
  7/27/2017
  Winston-Salem, NC
  
  	Written Language: JAVA Eclipse IDE SE8.
*/

import java.util.Arrays;
public class FibonnaciNums {
	
	public static void main (String[] args){
		
		//Create a fibonnaci sequence in an array;
		int count = 0; 
		Long prime10 = (long) 0;
		Long prime10Arr[] = new Long[90];
		Long fibArr[] = new Long[90];
		fibArr[0] = (long) 1;
		fibArr[1] = fibArr[0] + fibArr[0];
		
		for(int i = 2; i < fibArr.length; i++){
			fibArr[i] = fibArr[i-1] + fibArr[i-2]; 
		}
		System.out.println("The Fibbonnacci Array ");
		System.out.println("==========================================================================================================");
		System.out.print(Arrays.toString(fibArr));
		//populate the prime 10 array;
		for(int i = 0; i < fibArr.length; i++){
			if(fibArr[i].toString().length() == 10){
				prime10Arr[i] = fibArr[i];
				count++;
			}
		}
		
	
		//Return first 10 digit values of prime numbers in Fibonnaci Sequence for each index.
		//I Believe this math and the algorithm is off here with trying to get a 10 digit prime!!! After 10 hours I stopped here,
		//for issues with time and Log(n) errors and trouble finding null removal method.
		count = 0;
		for(int i = 0; i < prime10Arr.length; i++){
			if(prime10Arr[i] != null && count < 1){
				prime10 = prime10Arr[i];
				count++;
			}
		
		}
		
		System.out.println();
		System.out.println();
		
		System.out.println("Array contains all 10 digit index values sorted in Fibbonacci Sequence");
		System.out.println("Check Matches With Above Array");
		System.out.println("===================================================================================================================");
		System.out.println(Arrays.toString(prime10Arr));
	 	System.out.println(prime10);	
	}
}