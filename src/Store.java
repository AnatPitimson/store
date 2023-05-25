import java.util.ArrayList;
import java.util.List;

public class Store {

    private List<Product> products;

    public Store() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public int getAvailableQuantity(Product product) {
        return product.getQuantityOrdered();
    }

    public void updateProductQuantity(Product product, int quantity) {
        product.setQuantityOrdered(quantity);
    }

    public double getTotalInventoryValue() {
        double totalValue = 0;
        for (Product product : products) {
            totalValue += (product.getPrice() * product.getQuantityOrdered());
        }
        return totalValue;
    }

    public List<Product> searchProductsByName(String productName) {
        List<Product> matchingProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(productName)) {
                matchingProducts.add(product);
            }
        }
        return matchingProducts;
    }

    public List<Product> searchProductsByPriceRange(double minPrice, double maxPrice) {
        List<Product> matchingProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getPrice() >= minPrice && product.getPrice() <= maxPrice) {
                matchingProducts.add(product);
            }
        }
        return matchingProducts;
    }

}
