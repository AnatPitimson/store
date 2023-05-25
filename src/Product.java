public class Product {

    private static int nextProductId = 1;
    private int id;
    private String name;
    private double price;
    private int quantityOrdered;
    private int quantityNeeded;

    public Product(String name, double price, int quantity,int quantityNeeded) {
        this.id = nextProductId++;
        this.name = name;
        this.price = price;
        this.quantityOrdered = quantity;
        this.quantityNeeded=quantityNeeded;
    }

    public Product(int id,String name, double price, int quantity,int quantityNeeded) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantityOrdered = quantity;
        this.quantityNeeded=quantityNeeded;
    }

    public Product(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantityOrdered = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(int quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public int getQuantityNeeded() {
        return quantityNeeded;
    }

    public void setQuantityNeeded(int quantityNeeded) {
        this.quantityNeeded = quantityNeeded;
    }

    @Override
    public String toString() {
        return "Product id=" + id +
                ", name=" + name +
                ", price=" + price +
                ", quantity=" + quantityOrdered
                + ", quantity Needed=" + quantityNeeded
                ;
    }

}
