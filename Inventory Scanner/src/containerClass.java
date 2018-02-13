import java.util.ArrayList;

public class containerClass {
	private ArrayList<String> inventList = new ArrayList<String>();
	private ArrayList<String> selectList = new ArrayList<String>();	
	
	public void addItems(){
	inventList.add(":SKU:123, UPC:123abc, Item: Polo Shirt:");	
	inventList.add(":SKU:234, UPC:234bcd, Item: Levis Jeans:");	
	inventList.add(":SKU:345, UPC:345cde, Item: John Deer Tractor:");
	}
	
	public containerClass(ArrayList<String> aquireList){
		selectList = aquireList;
	}
	
	public ArrayList<String> getInventSelection(){
		ArrayList<String> matchList = new ArrayList<String>();
		for(int i = 0; i < selectList.size(); i++){
			if(inventList.get(i).contains(selectList.get(i))){
				matchList.add(i, inventList.get(i));
			}
			else{
				matchList.add("Item Not Found");
			}
		}
		return matchList;
	}
	
	public ArrayList<String> getAllInventory(){
		
		return inventList;
	}
}


