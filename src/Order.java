import java.util.Date;
import java.util.List;

public class Order {

    private int orderId;
    private Person customer;
    private Date orderDate;
    private List<Product> orderedProducts;


    public Order( Person customer, Date orderDate,List<Product> orderedProducts) {
        this.customer = customer;
        this.orderDate = orderDate;
        this.orderedProducts = orderedProducts;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Person getCustomer() {
        return customer;
    }

    public void setCustomer(Person customer) {
        this.customer = customer;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderedProducts(List<Product> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    public void addProduct(Product product) {
        orderedProducts.add(product);
    }

    public void removeProduct(Product product) {
        orderedProducts.remove(product);
    }

    public List<Product> getOrderedProducts() {
        return orderedProducts;
    }

    public double calculateTotalAmount() {
        double totalAmount = 0;
        for (Product product : orderedProducts) {
            totalAmount += product.getPrice() * product.getQuantityOrdered();
        }
        return totalAmount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customer=" + customer +
                ", orderDate=" + orderDate +
                ", orderedProducts=" + orderedProducts +
                '}';
    }

}

