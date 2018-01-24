
public class yourCar {
	
	private String model;

	public yourCar(String model){
	 this.model = model;
	}
	
	public String getResults(){
		String result = "";
		if(model.equalsIgnoreCase("mercedes")){
			result = "You are Expensive";
		}
		else{
			result = "Well at least your Frugal";
		}
		
		return result;
	}

}
