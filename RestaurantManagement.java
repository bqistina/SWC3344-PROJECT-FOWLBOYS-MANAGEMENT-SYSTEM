import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RestaurantManagement {
    private List<CustomerInformation> customerList;

    public RestaurantManagement() {
        customerList = new ArrayList<>();
    }

    public void loadCustomerData(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int custId = Integer.parseInt(parts[0]);
                String custName = parts[1];
                int tableNumber = Integer.parseInt(parts[2]);
                int orderCount = Integer.parseInt(parts[3]);
                CustomerInformation customer = new CustomerInformation(custId, custName, tableNumber);
                for (int i = 0; i < orderCount; i++) {
                    String[] orderParts = br.readLine().split(",");
                    int orderId = Integer.parseInt(orderParts[0]);
                    String itemName = orderParts[1];
                    double itemPrice = Double.parseDouble(orderParts[2]);
                    int quantity = Integer.parseInt(orderParts[3]);
                    OrderInformation order = new OrderInformation(orderId, itemName, itemPrice, quantity);
                    customer.addOrder(order);
                }
                customerList.add(customer);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public List<CustomerInformation> getCustomerList() {
        return customerList;
    }

    public void addCustomer(CustomerInformation customer) {
        customerList.add(customer);
    }

    public CustomerInformation getCustomerByName(String name) {
        for (CustomerInformation customer : customerList) {
            if (customer.getCustName().equals(name)) {
                return customer;
            }
        }
        return null;
    }
}
