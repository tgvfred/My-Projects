
public class MainCarShop {
    public static void main(String[] args) {
        String dealerName = "CryoStatus Motors";
        String vehicleType = "Commercial";
        String make = "Mercedes";
        String model = "S650";
        String color = "Black";
        int year = 2018;
        double msrp = 151800.00;

        if(vehicleType.equalsIgnoreCase("passenger")){
            Passenger myNewCar = new Passenger();
            myNewCar.setType(vehicleType);
            myNewCar.setMake(make);
            myNewCar.setModel(model);
            myNewCar.setColor(color);
            myNewCar.setYear(year);
            myNewCar.setMSRP(msrp);
            myNewCar.setDealershipName(dealerName);

            System.out.println(myNewCar.toString());
        }
        else if(vehicleType.equalsIgnoreCase("commercial")) {
            Commercial myNewTransport = new Commercial();
            myNewTransport.setType(vehicleType);
            myNewTransport.setMake(make);
            myNewTransport.setModel(model);
            myNewTransport.setColor(color);
            myNewTransport.setYear(year);
            myNewTransport.setMSRP(msrp);
            myNewTransport.setDealershipName(dealerName);

            System.out.println(myNewTransport.toString());
        }
        else {
            System.out.println("Not A Valid Vehicle");
        }



    }

}
