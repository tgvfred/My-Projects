
public class Car {
	private String model;
	private int year;
	public void setModel(String tempMod) {
		// TODO Auto-generated method stub
		 this.model = tempMod;
	}

	public String getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	public void setYear(int tempYear) {
		// TODO Auto-generated method stub
		this.year = tempYear;
	}

	public int getYear() {
		// TODO Auto-generated method stub
		return year;
	}
	
	public String toString(){
		
		String result = "[Model Type:" + getModel() + " Year of Model: " + getYear() + "]";
		return result;
	}

}
