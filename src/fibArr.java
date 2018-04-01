import java.util.Arrays;

public class fibArr {

	
public static void main (String[]args){
	
		int numStart = 1;
		int fibArr[] = new int [10];
		
		for(int i = 0; i < fibArr.length; i++){
			
			if(i == 0){
				fibArr[i] = numStart;
			}
			if(i == 1){
				fibArr[i] = fibArr[i-1];
			}
			if(i >= 2){
				fibArr[i] = fibArr[i-2] + fibArr[i-1];
			}
			
		}

			System.out.println(Arrays.toString(fibArr));
	}

}
