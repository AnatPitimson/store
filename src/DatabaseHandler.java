import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {

    public static void insertPerson(Person person) {
        String insertQuery = "INSERT INTO customers (firstName, lastName, familyMembers, phone) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/store",
                "root", "1234");
             PreparedStatement statement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setInt(3, person.getFamilyMembers());
            statement.setString(4, person.getPhone());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    int generatedId = resultSet.getInt(1);
                    person.setId(generatedId);
                    System.out.println("Generated ID: " + generatedId);
                }
                System.out.println("Person inserted successfully.");
            } else {
                System.out.println("Failed to insert person.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertProduct(Product product) {
        String insertQuery = "INSERT INTO products (productId, productName, price, quantity, quantityNeeded) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/store",
                "root", "1234");
             PreparedStatement statement = connection.prepareStatement(insertQuery)) {

            statement.setInt(1, product.getId());
            statement.setString(2, product.getName());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getQuantityOrdered());
            statement.setInt(5, product.getQuantityNeeded());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Product inserted successfully.");
            } else {
                System.out.println("Failed to insert product.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertOrderProduct(int orderId, int productId, int quantity) {
        String insertQuery = "INSERT INTO orderedproducts (orderId, productId, quantity) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/store",
                "root", "1234");
             PreparedStatement statement = connection.prepareStatement(insertQuery)) {

            statement.setInt(1, orderId);
            statement.setInt(2, productId);
            statement.setInt(3, quantity);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Order product inserted successfully.");
            } else {
                System.out.println("Failed to insert order product.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertOrder(Order order) {
        String insertOrderQuery = "INSERT INTO orders (customerId, orderDate) VALUES ( ?, ?)";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/store",
                "root", "1234");
            PreparedStatement orderStatement = connection.prepareStatement(insertOrderQuery, Statement.RETURN_GENERATED_KEYS)) {

            orderStatement.setInt(1, order.getCustomer().getId());
            orderStatement.setDate(2, (Date) order.getOrderDate());

            int rowsAffected = orderStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet resultSet = orderStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int generatedId = resultSet.getInt(1);
                    order.setOrderId(generatedId);
                    System.out.println("Generated ID: " + generatedId);
                }
                List<Product> orderedProducts = order.getOrderedProducts();
                for (Product product : orderedProducts) {
                    insertOrderProduct(order.getOrderId(), product.getId(), product.getQuantityOrdered());
                }
                System.out.println("Order inserted successfully.");
            } else {
                System.out.println("Failed to insert Order.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Product> getProducts() {
        List<Product> list = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/store",
                    "root", "1234");

            PreparedStatement statement = connection.prepareStatement("SELECT productId, productName, price, quantity," +
                    " quantityNeeded FROM products");

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int productId = result.getInt("productId");
                String productName = result.getString("productName");
                double price = result.getDouble("price");
                int quantity = result.getInt("quantity");
                int quantityNeeded = result.getInt("quantityNeeded");
                Product product=new Product(productId, productName, price, quantity, quantityNeeded);
                list.add(product);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public static void updateAmount(int productId, int newQuantityNeeded) {

        String updateQuery = "UPDATE products SET quantityNeeded = ? WHERE productId = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/store",
                    "root", "1234");

            PreparedStatement statement = connection.prepareStatement(updateQuery)) {

            statement.setInt(1, newQuantityNeeded);
            statement.setInt(2, productId);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Quantity needed updated successfully.");
            } else {
                System.out.println("Failed to update quantity needed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}




