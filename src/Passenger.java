import java.text.DecimalFormat;
public class Passenger extends Vehicle{
    DecimalFormat fmt = new DecimalFormat("#0.00");
    private String make;
    private String model;
    private String color;
    private int year;
    private double MSRP;

    public Passenger() {
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMSRP(double msrp) {
        this.MSRP = msrp;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public int getYear() {
        return year;
    }

    public double getMSRP() {
        return MSRP;
    }

    @Override
    public String toString() {
        String priceStatement;
        String vehicleStats;
        double choiceTaxTotal;
        if(getMSRP() >= 39000) {
            priceStatement = "This vehicle comes with a luxury premium of 12%: $" + (getMSRP() * .12);
            choiceTaxTotal = (getMSRP() * .12) + getMSRP();
        }
        else {
            priceStatement = "This vehicle meets the economy reduced rate of 10%: $" + (getMSRP() * .10);
            choiceTaxTotal = getMSRP() - (getMSRP() * .10);
        }

        vehicleStats = priceStatement + "\n==========================================================" + "\n"
                + getType() + "\nMake: " + getMake() + "\nModel: " + getModel() +
                "\nColor: " + getColor() + "\nYear: " + getYear() + "\nMSRP: " + getMSRP() +
                "\nChoice Tax MSRP: "+ fmt.format(choiceTaxTotal) + "\nSeller: " + getDealershipName();

        return vehicleStats;
    }
}
