
public class Store extends ShoppingMall{
    private String storeName;
    private String item;
    private String itemType;
    private double itemPrice;

    public Store() {
    }

    public void setStoreName(String name) {
        this.storeName = name;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public void setPrice(double price) {
        this.itemPrice = price;
    }

    public String getItem() {
        return item;
    }

    public String getItemType() {
        return itemType;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public String getStoreName() {
        return storeName;
    }
}
