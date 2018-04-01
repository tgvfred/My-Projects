
public class printSets {
	public static void main(String[] args) {

		String letters = "abcdefg";

		brakeString bk = new brakeString();
		bk.setString(letters);
		System.out.println(bk.getString());
	}
}

class brakeString {

	private String let = "";
	private String newLet = "";
	/*
	 * public brakeString(String Let){ this.let = Let; }
	 */
	public void setString(String letters) {
		this.let = letters;
		for(int i = 0; i < letters.length()-2 ; i++){
			if(letters.substring(i, i+3).equalsIgnoreCase("fg")){
			newLet = letters.substring(i, i+3);	
		
		/*for (int i = 0; i < letters.length(); i++) {
			for (int k = 0; k < letters.length() - i; k++) {
				System.out.print(letters.substring(i, k + i + 1) + ",");*/

			}

		}

	}
	
	public String getString(){
		return newLet;
	}

}
