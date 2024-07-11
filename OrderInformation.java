import java.time.LocalDateTime;

public class OrderInformation {
    private int orderId;
    private String itemName;
    private double itemPrice;
    private int quantity;
    private LocalDateTime orderTime;

    public OrderInformation(int orderId, String itemName, double itemPrice, int quantity) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.orderTime = LocalDateTime.now();
    }

    public int getOrderId() {
        return orderId;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public double getTotalPrice() {
        return itemPrice * quantity;
    }
}
