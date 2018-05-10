
public class Vehicle extends Dealership {

    private String type;

    public Vehicle() {
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        String vehicleType;
        if(this.type.equalsIgnoreCase("passenger")) {
            vehicleType = "Passenger Vehicle\n==========================================================";
        }
        else if(this.type.equalsIgnoreCase("commercial")) {
            vehicleType = "Commercial Vehicle\n=========================================================";
        }
        else {
            vehicleType = "Not in the System";
        }

        return vehicleType;
    }
}
