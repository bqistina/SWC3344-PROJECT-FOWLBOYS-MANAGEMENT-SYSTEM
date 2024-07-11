import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class OrderProcessor {
    private Queue<CustomerInformation> counter1;
    private Queue<CustomerInformation> counter2;
    private Queue<CustomerInformation> counter3;
    private Stack<CustomerInformation> completeStack;

    public OrderProcessor() {
        counter1 = new LinkedList<>();
        counter2 = new LinkedList<>();
        counter3 = new LinkedList<>();
        completeStack = new Stack<>();
    }

    public void addCustomerToQueue(CustomerInformation customer) {
        int totalItems = customer.getOrders().size();
        if (totalItems <= 5) {
            if (counter1.size() <= counter2.size()) {
                counter1.add(customer);
            } else {
                counter2.add(customer);
            }
        } else {
            counter3.add(customer);
        }
    }

    public void processOrders() {
        // Processing logic for each counter
        processCounter(counter1);
        processCounter(counter2);
        processCounter(counter3);
    }

    private void processCounter(Queue<CustomerInformation> counter) {
        while (!counter.isEmpty()) {
            CustomerInformation customer = counter.poll();
            completeStack.push(customer);
            // Processing details omitted for brevity (already implemented)
        }
    }

    public int getCounter1Size() {
        return counter1.size();
    }

    public int getCounter2Size() {
        return counter2.size();
    }

    public int getCounter3Size() {
        return counter3.size();
    }

    public int getTotalCustomerSize() {
        return counter1.size() + counter2.size() + counter3.size();
    }

    public Stack<CustomerInformation> getCompleteStack() {
        return completeStack;
    }
}
