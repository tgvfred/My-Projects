import java.util.ArrayList;

public class shipContainer {
	@SuppressWarnings("unchecked")
	private ArrayList <String> objectContain = new ArrayList<String>();
	private String containRepres = "";
	public shipContainer(ArrayList <String> objList){
		objectContain = objList;	
	}
	
	//Match Item Name or SKU to the items listed in the container, then return string represenation;
	public String getContainerItemList(){
		for(int i = 0; i < objectContain.size(); i++){
			if(objectContain.get(i).contains("pants")){
				containRepres += "\nObject: " + objectContain.get(i) +
				"\nItem: Wrangler Jeans\nUPC:79665\nItem Conatiner Position Number" + "(" + i + ")"; 
			}
			else if(objectContain.get(i).contains("shirt")){
				containRepres += "\nObject: " + objectContain.get(i) +
				"\nItem: Polo shirt\nUPC:87365\nItem Conatiner Position Number" + "(" + i + ")"; 	
			}
			else if(objectContain.get(i).contains("coat")){
				containRepres += "\nObject: " + objectContain.get(i) +
				"\nItem: H&M Leather Fall Coat\nUPC:587665\nItem Conatiner Position Number" + "(" + i + ")"; 
			}
			else{
				containRepres += "\n\n Item Unlisted";
			}
		}
		
		return containRepres;
	}

}

			
		
		
	

