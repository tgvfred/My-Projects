
public class BagObject {
	
	private String itemName;
	private double  itemCost;
	
	/*public BagObject(String item, int cost){
		this.itemName = item;
		this.itemCost = cost;
	}
	*/
	public void setItem(String items){
		this.itemName = items;
		
	}
	
	public void setCost(double cost){
		this.itemCost = cost;
	}
	
	public double getCost(){
		return itemCost;
	}
	
	public String getItem(){
		return itemName;
	}
	
	public String toString(){
		return "This Item:  " + getItem() + " Cost:  $" + getCost();
	}
}
