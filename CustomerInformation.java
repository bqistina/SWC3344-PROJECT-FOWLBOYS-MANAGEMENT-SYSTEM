import java.util.LinkedList;
import java.util.List;

public class CustomerInformation {
    private int custId;
    private String custName;
    private int tableNumber;
    private List<OrderInformation> orders;

    public CustomerInformation(int custId, String custName, int tableNumber) {
        this.custId = custId;
        this.custName = custName;
        this.tableNumber = tableNumber;
        this.orders = new LinkedList<>();
    }

    public int getCustId() {
        return custId;
    }

    public String getCustName() {
        return custName;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public List<OrderInformation> getOrders() {
        return orders;
    }

    public void addOrder(OrderInformation order) {
        orders.add(order);
    }
}
