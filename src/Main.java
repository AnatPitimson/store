import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        /*  try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/store", "root", "1234");

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from products");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

       */

        Frame frame=new Frame();

        DatabaseHandler.getProducts();
        /*DatabaseHandler.insertProduct(product1);
        DatabaseHandler.insertProduct(product2);
        DatabaseHandler.insertProduct(product3);
        DatabaseHandler.insertProduct(product4);
        DatabaseHandler.updateAmount(1,100);
        DatabaseHandler.updateAmount(2,100);
        DatabaseHandler.updateAmount(3,100);
        DatabaseHandler.updateAmount(4,100);
         */

    }


}