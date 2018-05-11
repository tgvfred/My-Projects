import java.text.DecimalFormat;
public class Customer extends Store {
    private String customerName;
    private String paymentType;
    DecimalFormat fmt = new DecimalFormat("#0.00");

    public Customer() {
    }

    public void setCustomerName(String name) {
        this.customerName = name;
    }

    public void setPaymentType(String payType) {
        this.paymentType = payType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPaymentType() {
        if(paymentType.equalsIgnoreCase("debit") || paymentType.equalsIgnoreCase("credit")) {
            paymentType = "Debit/Credit Transaction Total +Tax: $" + fmt.format(((getItemPrice() * .065) + getItemPrice()));
        }
        else if(paymentType.equalsIgnoreCase("cash")){
            paymentType = "Cash Transaction Total +Tax: $" + fmt.format(((getItemPrice() * .065) + getItemPrice()));
        }
        else {
            paymentType = "Invalid Method";
        }
        return paymentType;
    }

}
