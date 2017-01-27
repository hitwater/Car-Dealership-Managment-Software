/*
 * Car Dealership Managment Software v0.1 
 * * @author Guiming Huang
 */

package dealership;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.logging.*;

/*
 * GUI.java
 * A basic graphical user interface (GUI) for the car dealership management
 * software.
 */
public class GUI extends JFrame implements ItemListener {
    private JPanel cards; //a panel that uses CardLayout
    private final static String vehicleMngmt = "Vehicle Managment";
    private final static String userMngmt = "User Management";
    private final static String transMngmt = "Transaction Management";
    
    // Vehicle managment components
    private JRadioButton showVehiclesRadioButton;
    private JRadioButton addVehicleRadioButton;
    private JRadioButton deleteVehicleRadioButton;
    private JRadioButton searchByVinRadioButton;
    private JRadioButton searchByPriceRadioButton;
    private JButton manageVehicles;
    
    // User management components
    private JRadioButton showUsersRadioButton;
    private JRadioButton addUserRadioButton;
    private JRadioButton deleteUserRadioButton;
    private JRadioButton updateUserRadioButton;
    private JButton manageUsers;
    
    // User management components
    private JRadioButton showTransRadioButton;
    private JRadioButton addTransRadioButton;
    private JButton manageTrans;
    
    private Dealership dealership;
    private static final Logger log = Logger.getLogger("Dealership");

    public GUI(Dealership deal) throws HeadlessException {
        super("Dealership");
        log.setLevel(Level.INFO);
        initializeGui();
        initializeEvents();
        dealership = deal;
        log.config("GUI initialized");
    }
    
    private void initializeGui() {
      
        this.setSize(500, 400);
        Dimension windowSize = this.getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(screenSize.width/2 - windowSize.width/2, screenSize.height/2 - windowSize.height/2);
        Container pane = this.getContentPane();
        //pane.setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        
        //Put the JComboBox in a JPanel to get a nicer look.
        JPanel comboBoxPane = new JPanel(); //use FlowLayout
        String comboBoxItems[] = { vehicleMngmt, userMngmt, transMngmt };
        
        JComboBox cb = new JComboBox(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);
        comboBoxPane.add(cb);
        
        //Create the "cards".
        
        JPanel vehicleMngmtPanel = new JPanel(new GridLayout(0, 1));
        showVehiclesRadioButton =  new JRadioButton("Show all vehicles");
        showVehiclesRadioButton.setSelected(true);
        addVehicleRadioButton = new JRadioButton("Add a new vehicle");
        deleteVehicleRadioButton = new JRadioButton("Delete a vehicle");
        searchByVinRadioButton = new JRadioButton("Search vehicle by VIN");
        searchByPriceRadioButton = new JRadioButton("Search vehicles by price");
        
        ButtonGroup group = new ButtonGroup();
        group.add(showVehiclesRadioButton);
        group.add(addVehicleRadioButton);
        group.add(deleteVehicleRadioButton);
        group.add(searchByVinRadioButton);
        group.add(searchByPriceRadioButton);
        
        
        vehicleMngmtPanel.add(showVehiclesRadioButton);
        vehicleMngmtPanel.add(addVehicleRadioButton);
        vehicleMngmtPanel.add(deleteVehicleRadioButton);
        vehicleMngmtPanel.add(searchByVinRadioButton);
        vehicleMngmtPanel.add(searchByPriceRadioButton);
        manageVehicles = new JButton("Go");
        vehicleMngmtPanel.add(manageVehicles);
        
        
        // User management
        JPanel userMngmtPanel = new JPanel(new GridLayout(0, 1));
        showUsersRadioButton =  new JRadioButton("Show all users");
        showUsersRadioButton.setSelected(true);
        addUserRadioButton = new JRadioButton("Add a new user");
        deleteUserRadioButton = new JRadioButton("Delete a user");
        updateUserRadioButton = new JRadioButton("Update user");
        
        ButtonGroup userRadioGroup = new ButtonGroup();
        userRadioGroup.add(showUsersRadioButton);
        userRadioGroup.add(addUserRadioButton);
        userRadioGroup.add(deleteUserRadioButton);
        userRadioGroup.add(updateUserRadioButton);
        
        
        userMngmtPanel.add(showUsersRadioButton);
        userMngmtPanel.add(addUserRadioButton);
        userMngmtPanel.add(deleteUserRadioButton);
        userMngmtPanel.add(updateUserRadioButton);
        
        manageUsers = new JButton("Go");
        userMngmtPanel.add(manageUsers);
        
        
        // Transaction management
        JPanel transMngmtPanel = new JPanel(new GridLayout(0, 1));
        showTransRadioButton =  new JRadioButton("Show all sale transactions");
        showTransRadioButton.setSelected(true);
        addTransRadioButton = new JRadioButton("Add a new sale transaction");
        
        
        ButtonGroup transRadioGroup = new ButtonGroup();
        transRadioGroup.add(showTransRadioButton);
        transRadioGroup.add(addTransRadioButton);
        
        
        transMngmtPanel.add(showTransRadioButton);
        transMngmtPanel.add(addTransRadioButton);
        
        manageTrans = new JButton("Go");
        //manageVehicles.setActionCommand("OUCH!");
        transMngmtPanel.add(manageTrans);
        
        
        cards.add(vehicleMngmtPanel, vehicleMngmt);
        cards.add(userMngmtPanel, userMngmt);
        cards.add(transMngmtPanel, transMngmt);
        
        this.add(comboBoxPane, BorderLayout.PAGE_START);
        this.add(cards, BorderLayout.CENTER);
    }
    
    private void initializeEvents() {
        manageVehicles.addActionListener(new ActionListener () { 
            public void actionPerformed(ActionEvent e) {
                log.info("manageVehicles");
                if (showVehiclesRadioButton.isSelected())
                    showAllVehicles();
                else if (addVehicleRadioButton.isSelected())
                    addNewVehicle();
                else if (deleteVehicleRadioButton.isSelected())
                    deleteVehicle();
                else if (searchByVinRadioButton.isSelected())
                    searchVehicleByVin();
                else if (searchByPriceRadioButton.isSelected())
                    searchVehicleByPrice();
            }
        });
        
        manageUsers.addActionListener(new ActionListener () { 
            public void actionPerformed(ActionEvent e) {
                log.info("manageUsers");
                if (showUsersRadioButton.isSelected())
                    showAllUsers();
                else if (addUserRadioButton.isSelected())
                    addNewUser();
                else if (deleteUserRadioButton.isSelected())
                    deleteUser();
                else if (updateUserRadioButton.isSelected())
                    updateUser();
            }
        });
        
        manageTrans.addActionListener(new ActionListener () { 
            public void actionPerformed(ActionEvent e) {
                log.info("manageTrans");
                if (showTransRadioButton.isSelected())
                    showAllTrans();
                else if (addTransRadioButton.isSelected())
                    addNewTrans();
            }
        });
    }
    
    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)evt.getItem());
    }
    
    private void showAllVehicles() {
        log.info("showAllVehicles");
        JPanel topPanel = new JPanel();
        topPanel.setSize(400, 300);
        topPanel.setLayout(new BorderLayout());
        getContentPane().add(topPanel);

        // Create columns names
        String columnNames[] = {"VIN", "MAKE", "MODEL", "YEAR", "PRICE", "TYPE"};

        // Create some data
        String dataValues[][]= dealership.showAllVehicles();

        // Create a new table instance
        JTable table = new JTable(dataValues, columnNames);
        table.setFillsViewportHeight(true);

        // Add the table to a scrolling pane
        JScrollPane scrollPane = new JScrollPane(table);
        topPanel.add(scrollPane, BorderLayout.CENTER);
        JOptionPane.showMessageDialog(this, topPanel, "Search results", JOptionPane.PLAIN_MESSAGE);
    }
    
    private void showSelectVehicles(String dataValues[][]) {
        log.info("showSelectVehicles");
        
        JPanel topPanel = new JPanel();
        topPanel.setSize(400, 300);
        topPanel.setLayout(new BorderLayout());
        getContentPane().add(topPanel);

        // Create columns names
        String columnNames[] = {"VIN", "MAKE", "MODEL", "YEAR", "PRICE"};

        // Create a new table instance
        JTable table = new JTable(dataValues, columnNames);
        table.setFillsViewportHeight(true);

        // Add the table to a scrolling pane
        JScrollPane scrollPane = new JScrollPane(table);
        topPanel.add(scrollPane, BorderLayout.CENTER);
        JOptionPane.showMessageDialog(this, topPanel, "Search results", JOptionPane.PLAIN_MESSAGE);
    }
    
    
    private void addNewVehicle() {
        log.info("addNewVehicle");
        
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());
        
        JTextField vin = new JTextField(5);
        JTextField make = new JTextField(20);
        JTextField model = new JTextField(20);
        JTextField year = new JTextField(4);
        JTextField mileage = new JTextField(6);
        JTextField price = new JTextField(6);
        
        JRadioButton car = new JRadioButton("Passenger Car");
        JRadioButton truck = new JRadioButton("Truck");
        JRadioButton motorcycle = new JRadioButton("Motorcycle");
        
        JTextField extra1 = new JTextField(20);
        JTextField extra2 = new JTextField(20);
        JLabel extra1L = new JLabel("Body Style");
        JLabel extra2L = new JLabel("N/A");
        
        panel.add(new JLabel("VIN"));
        panel.add(vin);
        panel.add(new JLabel("Make"));
        panel.add(make);
        panel.add(new JLabel("Model"));
        panel.add(model);
        panel.add(new JLabel("Year"));
        panel.add(year);
        panel.add(new JLabel("Mileage"));
        panel.add(mileage);
        panel.add(new JLabel("Price"));
        panel.add(price);
        panel.add(new JLabel("Vehicle type:"));
        
        
        car.setSelected(true);
        extra2.setEnabled(false);
        
        ButtonGroup group = new ButtonGroup();
        group.add(car);
        group.add(truck);
        group.add(motorcycle);
        
        JPanel radiosPanel = new JPanel(new FlowLayout());
        radiosPanel.add(car);
        radiosPanel.add(truck);
        radiosPanel.add(motorcycle);
        
        panel.add(radiosPanel);
        
        panel.add(extra1L);
        panel.add(extra1);
        
        panel.add(extra2L);
        panel.add(extra2);
        
        car.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (car.isSelected()) {
                    extra1L.setText("Body Style");
                    extra2L.setText("N/A");
                    extra2.setEnabled(false);
                }
            }
        });
        
        truck.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (truck.isSelected()) {
                    extra1L.setText("Max Load Weight");
                    extra2L.setText("Length");
                    extra2.setEnabled(true);
                }
            }
        });
        
        motorcycle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (motorcycle.isSelected()) {
                    extra1L.setText("Type");
                    extra2L.setText("Displacement");
                    extra2.setEnabled(true);
                }
            }
        });
        
        if(JOptionPane.showOptionDialog(this, panel, "Add new vehicle", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null) == 
                JOptionPane.OK_OPTION) {
            try{
                if (car.isSelected()) {
                    dealership.addNewVehicle(1, vin.getText(), make.getText(), model.getText(), 
                            year.getText(), mileage.getText(), price.getText(), extra1.getText(),
                            extra2.getText());
                } else if (truck.isSelected()) {
                    dealership.addNewVehicle(2, vin.getText(), make.getText(), model.getText(), 
                            year.getText(), mileage.getText(), price.getText(), extra1.getText(),
                            extra2.getText());
                } else if (motorcycle.isSelected()) {
                    dealership.addNewVehicle(3, vin.getText(), make.getText(), model.getText(), 
                            year.getText(), mileage.getText(), price.getText(), extra1.getText(),
                            extra2.getText());
                }
                JOptionPane.showMessageDialog(null, "Vehicle added", "Success", JOptionPane.INFORMATION_MESSAGE);
                log.info("Added vehicle " + vin.getText());
                dealership.writeDatabase();
            } catch (BadInputException ex) {
                log.warning("addNewVehicle " + ex.getMessage());
                JOptionPane.showMessageDialog(null, ex.getMessage(), "BadInputException", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                log.warning("addNewVehicle Fields in improper format");
                JOptionPane.showMessageDialog(null, "Fields in improper format", "Exception", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void deleteVehicle() {
        log.info("deleteVehicle");
        
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());
        
        JTextField vin = new JTextField(5);
        
        panel.add(new JLabel("Enter VIN"));
        panel.add(vin);
        
        if(JOptionPane.showOptionDialog(this, panel, "Delete vehicle", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null) == 
                JOptionPane.OK_OPTION) {
            if (dealership.deleteVehicle(vin.getText())) {
                JOptionPane.showMessageDialog(null, "Vehicle deleted", "Success", JOptionPane.INFORMATION_MESSAGE);
                log.info("Deleted vehicle " + vin.getText());
                dealership.writeDatabase();
            } else {
                JOptionPane.showMessageDialog(null, "Vehicle not found", "Failure", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void searchVehicleByVin() {
        log.info("searchVehicleByVin");
        
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());
        
        JTextField vin = new JTextField(5);
        
        panel.add(new JLabel("Enter VIN"));
        panel.add(vin);
        
        if(JOptionPane.showOptionDialog(this, panel, "Search vehicle by VIN", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null) == 
                JOptionPane.OK_OPTION) {
            Vehicle v = dealership.searchVehicle(vin.getText());
            if (v != null) {
                String arr[][] = {v.toArray()};
                showSelectVehicles(arr);
            } else {
                JOptionPane.showMessageDialog(null, "Vehicle not found", "Failure", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void searchVehicleByPrice() {
        log.info("searchVehicleByPrice");
        
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());
        
        JTextField minPrice = new JTextField(6);
        JTextField maxPrice = new JTextField(6);
        
        panel.add(new JLabel("Minimum Price"));
        panel.add(minPrice);
        panel.add(new JLabel("Maximum Price"));
        panel.add(maxPrice);
        panel.add(new JLabel("Vehicle type:"));
        
        JRadioButton car = new JRadioButton("Passenger Car");
        car.setSelected(true);
        JRadioButton truck = new JRadioButton("Truck");
        JRadioButton motorcycle = new JRadioButton("Motorcycle");
        ButtonGroup group = new ButtonGroup();
        group.add(car);
        group.add(truck);
        group.add(motorcycle);
        
        JPanel radiosPanel = new JPanel(new FlowLayout());
        radiosPanel.add(car);
        radiosPanel.add(truck);
        radiosPanel.add(motorcycle);
        
        panel.add(radiosPanel);
        
        if(JOptionPane.showOptionDialog(this, panel, "Search vehicles by price", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null) == 
                JOptionPane.OK_OPTION) {
            try{
                if (car.isSelected()) {
                    showSelectVehicles(dealership.showVehiclesByPrice(1, minPrice.getText(), maxPrice.getText()));
                } else if (truck.isSelected()) {
                    showSelectVehicles(dealership.showVehiclesByPrice(2, minPrice.getText(), maxPrice.getText()));
                } else if (motorcycle.isSelected()) {
                    showSelectVehicles(dealership.showVehiclesByPrice(3, minPrice.getText(), maxPrice.getText()));
                }
            } catch (BadInputException ex) {
                log.warning("searchVehicleByPrice " + ex.getMessage());
                JOptionPane.showMessageDialog(null, ex.getMessage(), "BadInputException", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                log.warning("searchVehicleByPrice Fields in improper format");
                JOptionPane.showMessageDialog(null, "Fields in improper format", "Exception", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showAllUsers() {
        log.info("showAllUsers");
        
        JPanel topPanel = new JPanel();
        topPanel.setSize(400, 300);
        topPanel.setLayout(new BorderLayout());
        getContentPane().add(topPanel);

        // Create columns names
        String columnNames[] = {"ID", "FIRST", "LAST", "TYPE"};

        // Create some data
        String dataValues[][]= dealership.showAllUsers();

        // Create a new table instance
        JTable table = new JTable(dataValues, columnNames);
        table.setFillsViewportHeight(true);

        // Add the table to a scrolling pane
        JScrollPane scrollPane = new JScrollPane(table);
        topPanel.add(scrollPane, BorderLayout.CENTER);
        JOptionPane.showMessageDialog(this, topPanel, "Search results", JOptionPane.PLAIN_MESSAGE);
    }
    
    private void addNewUser() {
        log.info("addNewUser");
        
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());
        
        JTextField firstName = new JTextField(20);
        JTextField lastName = new JTextField(20);
        
        JRadioButton employee = new JRadioButton("Employee");
        JRadioButton customer = new JRadioButton("Customer");
        
        JTextField extra2 = new JTextField(20);
        JTextField extra1 = new JTextField(20);
        
        panel.add(new JLabel("First Name"));
        panel.add(firstName);
        panel.add(new JLabel("Last Name"));
        panel.add(lastName);
        panel.add(new JLabel("User type"));
        
        customer.setSelected(true);
        
        ButtonGroup group = new ButtonGroup();
        group.add(customer);
        group.add(employee);
        
        JPanel radiosPanel = new JPanel(new FlowLayout());
        radiosPanel.add(customer);
        radiosPanel.add(employee);
        
        panel.add(radiosPanel);
        
        JLabel extra1L = new JLabel("Phonenumber");
        panel.add(extra1L);
        panel.add(extra1);
        
        JLabel extra2L = new JLabel("Driver Licence Number");
        panel.add(extra2L);
        panel.add(extra2);
        
        customer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (customer.isSelected()) {
                    extra1L.setText("Phonenumber");
                    extra2L.setText("Driver Licence Number");
                }
            }
        });
        
        employee.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (employee.isSelected()) {
                    extra1L.setText("Monthly Salary");
                    extra2L.setText("Bank Account Number");
                }
            }
        });
        
        if(JOptionPane.showOptionDialog(this, panel, "Add new vehicle", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null) == 
                JOptionPane.OK_OPTION) {
            try{
                if (customer.isSelected()) {
                    dealership.addNewUser(1, firstName.getText(), lastName.getText(), extra1.getText(), extra2.getText());
                } else {
                    dealership.addNewUser(2, firstName.getText(), lastName.getText(), extra1.getText(), extra2.getText());
                }
                JOptionPane.showMessageDialog(null, "User added", "Success", JOptionPane.INFORMATION_MESSAGE);
                log.info("Added user " + firstName.getText() + " " + lastName.getText());
                dealership.writeDatabase();
            } catch (BadInputException ex) {
                log.warning("addNewUser " + ex.getMessage());
                JOptionPane.showMessageDialog(null, ex.getMessage(), "BadInputException", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                log.warning("addNewUser Fields in improper format");
                JOptionPane.showMessageDialog(null, "Fields in improper format", "Exception", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void deleteUser() {
        log.info("deleteUser");
        
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());
        
        JTextField id = new JTextField(5);
        
        panel.add(new JLabel("Enter ID"));
        panel.add(id);
        
        if(JOptionPane.showOptionDialog(this, panel, "Delete user", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null) == 
                JOptionPane.OK_OPTION) {
            if (dealership.deleteUser(id.getText())) {
                JOptionPane.showMessageDialog(null, "User deleted", "Success", JOptionPane.INFORMATION_MESSAGE);
                log.info("Deleted user " + id.getText());
                dealership.writeDatabase();
            } else {
                JOptionPane.showMessageDialog(null, "User not found", "Failure", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void updateUser() {
        log.info("updateUser");
        
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());
        
        JTextField id = new JTextField(5);
        
        JTextField firstName = new JTextField(20);
        JTextField lastName = new JTextField(20);
        
        JRadioButton employee = new JRadioButton("Employee");
        JRadioButton customer = new JRadioButton("Customer");
        
        JTextField extra2 = new JTextField(20);
        JTextField extra1 = new JTextField(20);
        
        panel.add(new JLabel("ID"));
        panel.add(id);
        panel.add(new JLabel("First Name"));
        panel.add(firstName);
        panel.add(new JLabel("Last Name"));
        panel.add(lastName);
        panel.add(new JLabel("User type"));
        
        customer.setSelected(true);
        
        ButtonGroup group = new ButtonGroup();
        group.add(customer);
        group.add(employee);
        
        JPanel radiosPanel = new JPanel(new FlowLayout());
        radiosPanel.add(customer);
        radiosPanel.add(employee);
        
        panel.add(radiosPanel);
        
        JLabel extra1L = new JLabel("Phonenumber");
        panel.add(extra1L);
        panel.add(extra1);
        
        JLabel extra2L = new JLabel("Driver Licence Number");
        panel.add(extra2L);
        panel.add(extra2);
        
        customer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (customer.isSelected()) {
                    extra1L.setText("Phonenumber");
                    extra2L.setText("Driver Licence Number");
                }
            }
        });
        
        employee.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (employee.isSelected()) {
                    extra1L.setText("Monthly Salary");
                    extra2L.setText("Bank Account Number");
                }
            }
        });
        
        id.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                User u = dealership.getUser(Integer.parseInt(id.getText()));
                if (u != null) {
                    firstName.setText(u.getFirstName());
                    lastName.setText(u.getLastName());
                    if (u instanceof Customer) {
                        customer.setSelected(true);
                        customer.doClick();
                        extra1.setText(((Customer)u).getPhoneNumber());
                        extra2.setText(Integer.toString(((Customer)u).getDriverLicenceNumber()));
                    } else {
                        employee.setSelected(true);
                        employee.doClick();
                        extra1.setText(Float.toString(((Employee)u).getMonthlySalary()));
                        extra2.setText(Integer.toString(((Employee)u).getBankAccountNumber()));
                    }
                }
            }
        });
        
        if(JOptionPane.showOptionDialog(this, panel, "Add new vehicle", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null) == 
                JOptionPane.OK_OPTION) {
            try{
                if (customer.isSelected()) {
                    dealership.addNewUser(1, firstName.getText(), lastName.getText(), extra1.getText(), extra2.getText());
                } else {
                    dealership.addNewUser(2, firstName.getText(), lastName.getText(), extra1.getText(), extra2.getText());
                }
                JOptionPane.showMessageDialog(null, "User updated", "Success", JOptionPane.INFORMATION_MESSAGE);
                log.info("Updated user " + id.getText());
                dealership.writeDatabase();
            } catch (BadInputException ex) {
                log.warning("updateUser " + ex.getMessage());
                JOptionPane.showMessageDialog(null, ex.getMessage(), "BadInputException", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                log.warning("updateUser Fields in improper format");
                JOptionPane.showMessageDialog(null, "Fields in improper format", "Exception", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showAllTrans() {
        log.info("showAllTrans");
        
        JPanel topPanel = new JPanel();
        topPanel.setSize(400, 300);
        topPanel.setLayout(new BorderLayout());
        getContentPane().add(topPanel);

        // Create columns names
        String columnNames[] = {"Customer ID", "Employee ID", "VIN", "Date", "Price"};

        // Create some data
        String dataValues[][]= dealership.showAllSales();

        // Create a new table instance
        JTable table = new JTable(dataValues, columnNames);
        table.setFillsViewportHeight(true);

        // Add the table to a scrolling pane
        JScrollPane scrollPane = new JScrollPane(table);
        topPanel.add(scrollPane, BorderLayout.CENTER);
        JOptionPane.showMessageDialog(this, topPanel, "Search results", JOptionPane.PLAIN_MESSAGE);
    }
    
    private void addNewTrans() {
        log.info("addNewTrans");
        
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());
        
        JTextField customerId = new JTextField(20);
        JTextField employeeId = new JTextField(20);
        JTextField vin = new JTextField(5);
        JTextField price = new JTextField(10);
        
        panel.add(new JLabel("Customer ID"));
        panel.add(customerId);
        panel.add(new JLabel("Employee ID"));
        panel.add(employeeId);
        panel.add(new JLabel("VIN"));
        panel.add(vin);
        panel.add(new JLabel("Price"));
        panel.add(price);
        
        if(JOptionPane.showOptionDialog(this, panel, "Add new vehicle", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null) == 
                JOptionPane.OK_OPTION) {
            try{
                dealership.sellVehicle(customerId.getText(), employeeId.getText(), vin.getText(), price.getText());
                JOptionPane.showMessageDialog(null, "Transaction Completed", "Success", JOptionPane.INFORMATION_MESSAGE);
                log.info("Added Transaction " + vin.getText());
                dealership.writeDatabase();
            } catch (BadInputException ex) {
                log.warning("addNewTrans " + ex.getMessage());
                JOptionPane.showMessageDialog(null, ex.getMessage(), "BadInputException", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                log.warning("addNewTrans Fields in improper format");
                JOptionPane.showMessageDialog(null, "Fields in improper format", "Exception", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}