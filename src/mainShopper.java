
public class mainShopper {
    public static void main(String[] args) {
        Customer shoppers = new Customer();

        shoppers.setMallName("Pentagon City");
        shoppers.setMallLocation("Arlington, Virginia");
        shoppers.setStoreName("Saks 5fth Avenue");
        shoppers.setItem("Gold Weston Watch 20K.");
        shoppers.setItemType("Apparel");
        shoppers.setPrice(1744.99);
        shoppers.setCustomerName("Bhektru Temesghen");
        shoppers.setPaymentType("credit");

        System.out.println(shoppers.getMallName()
                + "\n" + shoppers.getStoreName()
                + "\nItem: " + shoppers.getItem()
                + " (" + shoppers.getItemType() + ")"
                + "\nPrice: $" + shoppers.getItemPrice()
                + "\n" + shoppers.getPaymentType()
                + "\nCustomer Name: " + shoppers.getCustomerName()
                + "\n" + shoppers.getMallLocation());
    }
}
