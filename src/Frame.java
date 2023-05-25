import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class Frame extends JFrame {

    List<Product> personsOrder = new ArrayList<>();
    JButton registerButton, orderButton, storeButton;
    JLabel helloLabel;
    JPanel mainPanel, mainPanel1;
    Person person;
    Order order;
    JPanel JOrder;
    JTextArea textArea;
    Calendar currentDay;
    JTextField firstNameField;
    JTextField lastNameField;
    JTextField familyMembersField;
    JTextField phoneField;
    JDatePickerImpl datePicker;


    Frame() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setTitle("Store");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.white);
        this.setLayout(new BorderLayout());

        JPanel mainPanelTitle = new JPanel();
        mainPanelTitle.setLayout(new GridLayout(1, 2, 100, 0));
        mainPanelTitle.setBackground(Color.WHITE);

        JPanel titlePanel = createTitlePanel();
        mainPanelTitle.add(titlePanel);

        JPanel helloPanel = createTitlesPanel();
        mainPanelTitle.add(helloPanel);

        this.add(mainPanelTitle, BorderLayout.NORTH);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2, 30, 0));
        mainPanel.setBorder(new EmptyBorder(100, 300, 100, 300));
        mainPanel.setBackground(Color.WHITE);

        JPanel register = createRegisterPanel();
        mainPanel.add(register);

        mainPanel1 = new JPanel();
        mainPanel1.setLayout(new GridLayout(1, 2, 30, 0));
        mainPanel1.setBorder(new EmptyBorder(30, 30, 30, 30));
        mainPanel1.setBackground(Color.WHITE);

        JPanel store = createStorePanel();
        mainPanel1.add(store);

        JPanel order = createOrderPanel();
        mainPanel1.add(order);

        this.add(mainPanel, BorderLayout.CENTER);

        this.setVisible(true);
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);

        JLabel jLabel = new JLabel();
        jLabel.setText("Welcome to our store");
        //Border border = BorderFactory.createLineBorder(Color.lightGray, 5);
        ImageIcon imageIcon = new ImageIcon("pictures/logo.png");
        jLabel.setIcon(imageIcon);
        jLabel.setFont(new Font("MV Boli", Font.PLAIN, 40));
        jLabel.setVerticalAlignment(SwingConstants.TOP);

        titlePanel.add(jLabel);
        return titlePanel;
    }

    private JPanel createRegisterPanel() {
        JPanel register = new JPanel();
        register.setBackground(Color.WHITE);
        register.setLayout(new BorderLayout());
        JLabel registerLabel = new JLabel("Register");
        registerLabel.setFont(new Font("MV Boli", Font.PLAIN, 30));
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        registerLabel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 3));
        register.add(registerLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 50, 50));
        inputPanel.setBackground(Color.WHITE);
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameField = new JTextField(20);
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameField = new JTextField(20);
        JLabel familyMembersLabel = new JLabel("Family Members:");
        familyMembersField = new JTextField(20);
        JLabel phoneLabel = new JLabel("Phone:");
        phoneField = new JTextField(20);
        inputPanel.setBorder(new EmptyBorder(20, 100, 20, 100));


        inputPanel.add(firstNameLabel);
        inputPanel.add(firstNameField);
        inputPanel.add(lastNameLabel);
        inputPanel.add(lastNameField);
        inputPanel.add(familyMembersLabel);
        inputPanel.add(familyMembersField);
        inputPanel.add(phoneLabel);
        inputPanel.add(phoneField);
        register.add(inputPanel);

        registerButton = new JButton("Register");
        registerButton.setFocusable(false);
        registerButton.addActionListener(e -> {
            if (validateInput()) {
                person = new Person( firstNameField.getText(), lastNameField.getText()
                        , Integer.parseInt(familyMembersField.getText()), phoneField.getText());
                String firstName = firstNameField.getText();
                helloLabel.setText("Hello " + firstName);
                helloLabel.setVisible(true);
                mainPanel.setVisible(false);
                this.add(mainPanel1, BorderLayout.CENTER);
                mainPanel1.setVisible(true);
                orderButton.setEnabled(false);
                DatabaseHandler.insertPerson(person);
                this.revalidate();
            }
        });
        registerButton.setFont(new Font("MV Boli", Font.PLAIN, 20));
        register.add(registerButton, BorderLayout.SOUTH);
        register.setBorder(BorderFactory.createLineBorder(Color.lightGray, 5));

        return register;
    }

    private JPanel createStorePanel() {
        List<Product> list = DatabaseHandler.getProducts();

        JPanel store = new JPanel();
        store.setBackground(Color.WHITE);
        store.setLayout(new BorderLayout());
        JLabel storeLabel = new JLabel("Store");
        storeLabel.setFont(new Font("MV Boli", Font.PLAIN, 30));
        storeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        store.add(storeLabel, BorderLayout.NORTH);

        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };

        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Amount");
        tableModel.addColumn("Available");

        for (Product product : list) {
            tableModel.addRow(new Object[]{
                    product.getId(),
                    product.getName(),
                    0,
                    product.getQuantityNeeded()
            });
        }

        JTable productTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(productTable);


        store.add(scrollPane, BorderLayout.CENTER);

        storeButton = new JButton("Submit");
        storeButton.setFocusable(false);
        storeButton.addActionListener(e -> {
            int rowCount = tableModel.getRowCount();

            for (int i = 0; i < rowCount; i++) {
                int amount = Integer.parseInt(tableModel.getValueAt(i, 2).toString());
                if (amount > 0) {
                    int id = Integer.parseInt((tableModel.getValueAt(i, 0).toString()));
                    String name = (String) tableModel.getValueAt(i, 1);
                    Product product = new Product(id, name, amount);

                    if (Integer.parseInt((tableModel.getValueAt(i, 3).toString()))
                            - product.getQuantityOrdered() < 0) {
                        System.out.println(Integer.parseInt((tableModel.getValueAt(i, 3).toString()))
                                +" "+ product.getQuantityOrdered());
                        JOptionPane.showMessageDialog(this, "You can't order more than the quantity.", "Invalid input", JOptionPane.ERROR_MESSAGE);
                        tableModel.setValueAt(0,i,2);
                    } else {
                        personsOrder.add(product);
                        DatabaseHandler.updateAmount(product.getId(),
                                Integer.parseInt((tableModel.getValueAt(i, 3).toString())) - product.getQuantityOrdered());
                    }
                }
            }


            if(personsOrder.size()==0)
            {
                JOptionPane.showMessageDialog(this, "Your order list is empty.", "Invalid List", JOptionPane.ERROR_MESSAGE);
            }
            else {
                SqlDateModel model = new SqlDateModel();
                Properties properties = new Properties();
                properties.put("text.day", "Day");
                properties.put("text.month", "Month");
                properties.put("text.year", "Year");
                JDatePanelImpl panel = new JDatePanelImpl(model, properties);
                datePicker = new JDatePickerImpl(panel, new JFormattedTextField.AbstractFormatter() {
                    @Override
                    public Object stringToValue(String text) throws ParseException {
                        return "";
                    }

                    @Override
                    public String valueToString(Object value) throws ParseException {
                        if (value != null) {
                            Calendar calendar = (Calendar) value;
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-dd-MM");
                            String strDate = format.format(calendar.getTime());
                            return strDate;
                        }
                        return "";
                    }
                });

                JOrder.add(datePicker, BorderLayout.CENTER);
                JOrder.revalidate();


                datePicker.getModel().addChangeListener(e1 -> {
                    Date selectedDate = (Date) datePicker.getModel().getValue();

                    if (selectedDate != null) {
                        if (!isDateAfterCurrentDay(selectedDate)) {
                            JOptionPane.showMessageDialog(this, "Please select a date after the current day.", "Invalid Date", JOptionPane.ERROR_MESSAGE);
                            Calendar desiredDate = Calendar.getInstance();
                            desiredDate.setTime(currentDay.getTime());
                            desiredDate.add(Calendar.DAY_OF_MONTH, 1);
                            datePicker.getModel().setDate(desiredDate.get(Calendar.YEAR),
                                    desiredDate.get(Calendar.MONTH), desiredDate.get(Calendar.DAY_OF_MONTH));
                        } else {
                            order.setOrderDate(selectedDate);
                        }
                    }
                    selectedDate = (Date) datePicker.getModel().getValue();
                    order.setOrderDate(selectedDate);
                });


                order = new Order( person, new Date(), personsOrder);


                storeButton.setVisible(false);
                orderButton.setEnabled(true);

                this.revalidate();
            }
        });


        storeButton.setFont(new Font("MV Boli", Font.PLAIN, 20));
        store.add(storeButton, BorderLayout.SOUTH);
        store.setBorder(BorderFactory.createLineBorder(Color.lightGray, 5));

        return store;
    }

    private JPanel createOrderPanel() {
        JOrder = new JPanel();
        JOrder.setBackground(Color.WHITE);
        JOrder.setLayout(new BorderLayout());
        JLabel orderLabel = new JLabel("Order");
        orderLabel.setFont(new Font("MV Boli", Font.PLAIN, 30));
        orderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JOrder.add(orderLabel, BorderLayout.NORTH);
        orderButton = new JButton("Submit");
        orderButton.setFocusable(false);
        orderButton.setFont(new Font("MV Boli", Font.PLAIN, 20));
        JOrder.add(orderButton, BorderLayout.SOUTH);
        JOrder.setBorder(BorderFactory.createLineBorder(Color.lightGray, 5));

        orderButton.addActionListener(e -> {
            Date selectedDate = (Date) datePicker.getModel().getValue();

            if (selectedDate != null) {
                textArea = new JTextArea();
                textArea.setEditable(false);
                textArea.setFont(new Font("MV Boli", Font.PLAIN, 20));

                textArea.append("Order Details\n\n");
                textArea.append("Order ID: " + order.getOrderId() + "\n\n");
                textArea.append("Customer: " + order.getCustomer().getFirstName()
                        + " " + order.getCustomer().getLastName() + "\n\n");
                textArea.append("Order Date: " + order.getOrderDate() + "\n\n");
                textArea.append("Ordered Products:\n");
                for (Product product : order.getOrderedProducts()) {
                    textArea.append("~ " + product.getName() + ": " + product.getQuantityOrdered() + "\n");
                }

                DatabaseHandler.insertOrder(order);

                JScrollPane orderText = new JScrollPane(textArea);

                JOrder.add(orderText, BorderLayout.CENTER);
                orderButton.setEnabled(false);
                datePicker.setVisible(false);
                JOrder.revalidate();
            }
            else {
                JOptionPane.showMessageDialog(this, "Please enter a valid Date.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }

        });

        return JOrder;
    }

    private JPanel createTitlesPanel() {
        helloLabel = new JLabel("Hello");
        helloLabel.setFont(new Font("MV Boli", Font.PLAIN, 20));
        helloLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        helloLabel.setVisible(false);
        JPanel helloPanel = new JPanel();
        helloPanel.setBackground(Color.WHITE);
        helloPanel.add(helloLabel);
        return helloPanel;
    }

    private boolean validateInput() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String familyMembersText = familyMembersField.getText().trim();
        String phone = phoneField.getText().trim();

        if (firstName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid first name.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (lastName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid last name.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (familyMembersText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number of family members.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        int familyMembers;
        try {
            familyMembers = Integer.parseInt(familyMembersText);
            if (familyMembers <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number of family members.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!isValidPhoneNumber(phone)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid phone number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }


        return true;
    }

    private boolean isValidPhoneNumber(String phone) {
        String regex = "^\\d{10}$"; // Assumes phone number is 10 digits long

        return phone.matches(regex);
    }

    private boolean isDateAfterCurrentDay(Date date) {
        currentDay = Calendar.getInstance();
        currentDay.set(Calendar.HOUR_OF_DAY, 0);
        currentDay.set(Calendar.MINUTE, 0);
        currentDay.set(Calendar.SECOND, 0);
        currentDay.set(Calendar.MILLISECOND, 0);

        Calendar selectedDay = Calendar.getInstance();
        selectedDay.setTime(date);
        selectedDay.set(Calendar.HOUR_OF_DAY, 0);
        selectedDay.set(Calendar.MINUTE, 0);
        selectedDay.set(Calendar.SECOND, 0);
        selectedDay.set(Calendar.MILLISECOND, 0);

        return selectedDay.after(currentDay);
    }


}
