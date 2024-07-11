import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RestaurantManagementGUI extends JFrame {
    private RestaurantManagement restaurantManagement;
    private OrderProcessor orderProcessor;
    
    private JTextArea displayArea;
    private JLabel counter1Label;
    private JLabel counter2Label;
    private JLabel counter3Label;
    private JLabel totalCustomersLabel;
    private JComboBox<String> customerComboBox;

    public RestaurantManagementGUI() {
        restaurantManagement = new RestaurantManagement();
        orderProcessor = new OrderProcessor();

        setTitle("Restaurant Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1, 4));

        JButton loadCustomersButton = new JButton("Load Customers");
        loadCustomersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadCustomers();
            }
        });
        controlPanel.add(loadCustomersButton);

        JButton addCustomerButton = new JButton("Add Customer");
        addCustomerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addCustomer();
            }
        });
        controlPanel.add(addCustomerButton);

        JButton removeCustomerButton = new JButton("Remove Customer");
        removeCustomerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeCustomer();
            }
        });
        controlPanel.add(removeCustomerButton);

        JButton processOrdersButton = new JButton("Process Orders");
        processOrdersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processOrders();
            }
        });
        controlPanel.add(processOrdersButton);

        JButton generateReceiptButton = new JButton("Generate Receipt");
        generateReceiptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generateReceipt();
            }
        });
        controlPanel.add(generateReceiptButton);

        add(controlPanel, BorderLayout.SOUTH);

        JPanel counterPanel = new JPanel();
        counterPanel.setLayout(new GridLayout(1, 4));

        counter1Label = new JLabel("Counter 1: 0");
        counter2Label = new JLabel("Counter 2: 0");
        counter3Label = new JLabel("Counter 3: 0");
        totalCustomersLabel = new JLabel("Total Customers: 0");

        counterPanel.add(counter1Label);
        counterPanel.add(counter2Label);
        counterPanel.add(counter3Label);
        counterPanel.add(totalCustomersLabel);

        add(counterPanel, BorderLayout.NORTH);

        JPanel customerPanel = new JPanel();
        customerPanel.setLayout(new GridLayout(1, 1));

        customerComboBox = new JComboBox<>();
        customerPanel.add(new JLabel("Select Customer:"));
        customerPanel.add(customerComboBox);

        add(customerPanel, BorderLayout.WEST);
    }

    private void loadCustomers() {
        restaurantManagement.loadCustomerData("customer_orders.txt");
        for (CustomerInformation customer : restaurantManagement.getCustomerList()) {
            orderProcessor.addCustomerToQueue(customer);
            displayArea.append("Customer loaded: " + customer.getCustName() + "\n");
            customerComboBox.addItem(customer.getCustName());
        }
        updateCounters();
    }

    private void addCustomer() {
        JTextField custIdField = new JTextField(5);
        JTextField custNameField = new JTextField(10);
        JTextField tableNumberField = new JTextField(5);
        JTextField orderCountField = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(0, 2));
        myPanel.add(new JLabel("Customer ID:"));
        myPanel.add(custIdField);
        myPanel.add(new JLabel("Customer Name:"));
        myPanel.add(custNameField);
        myPanel.add(new JLabel("Table Number:"));
        myPanel.add(tableNumberField);
        myPanel.add(new JLabel("Number of Orders:"));
        myPanel.add(orderCountField);

        int result = JOptionPane.showConfirmDialog(null, myPanel, 
                 "Please Enter Customer Details", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int custId = Integer.parseInt(custIdField.getText());
                String custName = custNameField.getText();
                int tableNumber = Integer.parseInt(tableNumberField.getText());
                int orderCount = Integer.parseInt(orderCountField.getText());

                CustomerInformation customer = new CustomerInformation(custId, custName, tableNumber);

                for (int i = 0; i < orderCount; i++) {
                    JTextField orderIdField = new JTextField(5);
                    JTextField itemNameField = new JTextField(10);
                    JTextField itemPriceField = new JTextField(5);
                    JTextField quantityField = new JTextField(5);

                    JPanel orderPanel = new JPanel();
                    orderPanel.setLayout(new GridLayout(0, 2));
                    orderPanel.add(new JLabel("Order ID:"));
                    orderPanel.add(orderIdField);
                    orderPanel.add(new JLabel("Item Name:"));
                    orderPanel.add(itemNameField);
                    orderPanel.add(new JLabel("Item Price:"));
                    orderPanel.add(itemPriceField);
                    orderPanel.add(new JLabel("Quantity:"));
                    orderPanel.add(quantityField);

                    int orderResult = JOptionPane.showConfirmDialog(null, orderPanel, 
                        "Please Enter Order Details for Order #" + (i + 1), JOptionPane.OK_CANCEL_OPTION);

                    if (orderResult == JOptionPane.OK_OPTION) {
                        int orderId = Integer.parseInt(orderIdField.getText());
                        String itemName = itemNameField.getText();
                        double itemPrice = Double.parseDouble(itemPriceField.getText());
                        int quantity = Integer.parseInt(quantityField.getText());

                        OrderInformation order = new OrderInformation(orderId, itemName, itemPrice, quantity);
                        customer.addOrder(order);
                    }
                }

                restaurantManagement.addCustomer(customer);
                orderProcessor.addCustomerToQueue(customer);
                displayArea.append("Customer added: " + custName + "\n");
                customerComboBox.addItem(custName);
                updateCounters();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter correct values.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removeCustomer() {
        String custIdStr = JOptionPane.showInputDialog(this, "Enter Customer ID to remove:");
        try {
            int custId = Integer.parseInt(custIdStr);
            List<CustomerInformation> customerList = restaurantManagement.getCustomerList();
            boolean removed = customerList.removeIf(customer -> customer.getCustId() == custId);

            if (removed) {
                displayArea.append("Customer with ID " + custId + " removed.\n");
                updateCounters();
                customerComboBox.removeItemAt(custId);  // Adjust this line to remove by name instead of index if needed
            } else {
                displayArea.append("Customer with ID " + custId + " not found.\n");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid customer ID.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void processOrders() {
        orderProcessor.processOrders();
        displayArea.append("Orders processed.\n");
        updateCounters();
    }

    private void updateCounters() {
        counter1Label.setText("Counter 1: " + orderProcessor.getCounter1Size());
        counter2Label.setText("Counter 2: " + orderProcessor.getCounter2Size());
        counter3Label.setText("Counter 3: " + orderProcessor.getCounter3Size());
        totalCustomersLabel.setText("Total Customers: " + orderProcessor.getTotalCustomerSize());
    }

    private void generateReceipt() {
        String selectedCustomerName = (String) customerComboBox.getSelectedItem();
        if (selectedCustomerName != null) {
            CustomerInformation customer = restaurantManagement.getCustomerByName(selectedCustomerName);
            if (customer != null) {
                StringBuilder receipt = new StringBuilder();
                receipt.append("Receipt for ").append(customer.getCustName()).append(":\n");
                double total = 0;
                for (OrderInformation order : customer.getOrders()) {
                    receipt.append("Order ID: ").append(order.getOrderId())
                            .append(", Item: ").append(order.getItemName())
                            .append(", Price: ").append(order.getItemPrice())
                            .append(", Quantity: ").append(order.getQuantity())
                            .append(", Subtotal: ").append(order.getItemPrice() * order.getQuantity()).append("\n");
                    total += order.getItemPrice() * order.getQuantity();
                }
                receipt.append("Total: ").append(total).append("\n");
                displayArea.append(receipt.toString());
            } else {
                displayArea.append("Customer not found.\n");
            }
        } else {
            displayArea.append("No customer selected.\n");
        }
    }

    public static void main(String[] args) {
        RestaurantManagementGUI gui = new RestaurantManagementGUI();
        gui.setVisible(true);
    }
}
