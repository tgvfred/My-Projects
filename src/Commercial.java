import java.text.DecimalFormat;


public class Commercial extends Vehicle {
    DecimalFormat fmt = new DecimalFormat("#0.00");
    private String make;
    private String model;
    private String color;
    private int year;
    private double MSRP;

    public Commercial() {
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
        if(getMSRP() <= 142000) {
            priceStatement = "This vehicle comes with a transport vehicle tax rate of 8%: $" + (getMSRP() * .08);
            choiceTaxTotal = (getMSRP() * .08) + getMSRP();
        }
        else {
            priceStatement = "This vehicle complies with the premiun Transport vehicle reduced rate of 15%: $" + (getMSRP() * .15);
            choiceTaxTotal = getMSRP() - (getMSRP() * .15);
        }

        vehicleStats = priceStatement + "\n==========================================================" + "\n"
                + getType() + "\nMake: " + getMake() + "\nModel: " + getModel() +
                "\nColor: " + getColor() + "\nYear: " + getYear() + "\nMSRP: " + getMSRP() +
                "\nChoice Tax MSRP: "+ fmt.format(choiceTaxTotal) + "\nSeller: " + getDealershipName();

        return vehicleStats;
    }
}
