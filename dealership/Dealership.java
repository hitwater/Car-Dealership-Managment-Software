/*
 * Car Dealership Managment Software v0.1 
 * * @author Guiming Huang
 */

package dealership;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * This class represents a car dealership software, providing some basic operations.
 * Available operations:
 * 1. Show all existing vehicles in the database.
 * 2. Add a new vehicle to the database. 
 * 3. Delete a vehicle from a database (given its VIN).
 * 4. Search for a vehicle (given its VIN).
 * 5. Show a list of vehicles within a given price range. 
 * 6. Show list of users.
 * 7. Add a new user to the database.
 * 8. Update user info (given their id).
 * 9. Sell a vehicle.
 * 10. Show a list of completed sale transactions        
 * 11. Exit program.
 * @author vangelis
 */
public class Dealership {
    
    private final List<Vehicle> vehicleInventory; 
    private final List<User> users;
    private final List<SaleTransaction> transactions;
    
    private int userIdCounter = 1;

    /**
     * Default constructor. Initializes the inventory, users, and transactions
     * tables.
     */
    public Dealership() {
        this.vehicleInventory = new ArrayList<Vehicle>();
        this.users = new ArrayList<User>();
        this.transactions = new ArrayList<SaleTransaction>();
    }
    

        /**
     * Constructor. Initializes the inventory, users, and transactions to given values.
     */
    public Dealership(List<Vehicle> vehicleInventory, List<User> users, List<SaleTransaction> transactions) {
        this.vehicleInventory = vehicleInventory;
        this.users = users;
        this.transactions = transactions;
    }

    /**
     * This method allows the user to enter a new vehicle to the inventory 
     * database.
     * @param sc The scanner object used to read user input.
     * @throws Dealership.BadInputException
     */
    public void addNewVehicle(int type, String vin, String make, String model, String year, String mileage, String price, String extra1, String extra2) throws BadInputException {
        //1. Passenger car
        //2. Truck
        //3. Motorcycle
        if (type < 1 || type > 3)
            throw new BadInputException("Legal vehicle type values: 1-3.");
        
        if (vin.length() > 10)
            throw new BadInputException("VIN should not be more that 10 characters long.");
        
        if (Integer.parseInt(mileage) < 0)
            throw new BadInputException("Mileage cannot be negative.");
        
        if (Float.parseFloat(price) < 0.0f)
            throw new BadInputException("Price cannot be negative.");
        
        if (type == 1) {
            PassengerCar car = new PassengerCar(vin, make, model, Integer.parseInt(year), Integer.parseInt(mileage), 
                    Float.parseFloat(price), extra1);
            vehicleInventory.add(car);
        } else if (type == 2) {
            float maxLoad = Float.parseFloat(extra1);
            if (maxLoad < 0.0f)
                throw new BadInputException("Max load cannot be negative.");
            
            float tLength = Float.parseFloat(extra2);
            if (tLength < 0.0f)
                throw new BadInputException("Truck length cannot be negative.");
            
            Truck tr = new Truck(vin, make, model, Integer.parseInt(year), Integer.parseInt(mileage), 
                    Float.parseFloat(price), maxLoad, tLength);
            vehicleInventory.add(tr);
        } else if (type == 3) {
            int displacement = Integer.parseInt(extra2);
            if (displacement < 0.0f)
                throw new BadInputException("Displacement cannot be negative.");
            
            Motorcycle mc = new Motorcycle(vin, make, model, Integer.parseInt(year), Integer.parseInt(mileage), 
                    Float.parseFloat(price), extra1, displacement);
            vehicleInventory.add(mc);
        }
    }

    
    public Vehicle searchVehicle(String vin) {
        for (Vehicle v : vehicleInventory) {
            if (v.getVin().equals(vin)) {
                return v;
            }
        }
        return null;
    }
    
    
    /**
     * This method allows the user to delete a vehicle from the inventory database.
     * @param sc The scanner object used to read user input.
     */
    public boolean deleteVehicle(String vin) {
        for (Vehicle v : vehicleInventory) {
            if (v.getVin().equals(vin)) {
                vehicleInventory.remove(v);
                return true;
            }
        }
        return false;
    }

    /**
     * This method prints out all the vehicle currently in the inventory, in a
     * formatted manner.
     */
    public String[][] showAllVehicles() {
        String[][] arr = new String[vehicleInventory.size()][5];
        for(int i = 0; i < vehicleInventory.size(); i++) {
            arr[i] = vehicleInventory.get(i).toArray();
        }
        return arr;
    }

    /**
     * This method allows the user to search for vehicles within a price range.
     * The list of vehicles found is printed out.
     * @param sc The scanner object used to read user input.
     */
    public String[][] showVehiclesByPrice(int type, String minPrice, String maxPrice) throws BadInputException {
        float lowValue = Float.parseFloat(minPrice);
        if (lowValue < 0.0f)
                throw new BadInputException("Low price cannot be negative.");
        
        float highValue = Float.parseFloat(maxPrice);
        if (highValue < 0.0f)
                throw new BadInputException("High price cannot be negative.");
                
        //1. Passenger car
        //2. Truck
        //3. Motorcycle
        int vehicleType = type;
        if (vehicleType < 1 || vehicleType > 3)
            throw new BadInputException("Legal vehicle type values: 1-3.");
        
        ArrayList<String[]> matchingVehicles = new ArrayList<String[]>();
        for (Vehicle v : vehicleInventory) {
            if (v.getPrice() >= lowValue && v.getPrice() <= highValue) {
                if (vehicleType == 1 && v instanceof PassengerCar)
                    matchingVehicles.add(v.toArray());
                else if (vehicleType == 2 && v instanceof Truck)
                    matchingVehicles.add(v.toArray());
                else if (vehicleType == 3 && v instanceof Motorcycle)
                    matchingVehicles.add(v.toArray());
            }
        }
        
        String[][] arr = new String[matchingVehicles.size()][];
        for(int i = 0; i < matchingVehicles.size(); i++)
            arr[i] = matchingVehicles.get(i);
        return arr;
    }

    /**
     * This method allows a new user to be added to the database.
     * @param sc The scanner object used to read user input.
     * @throws Dealership.BadInputException
     */
    public void addNewUser(int type, String firstName, String lastName, String extra1, String extra2) throws BadInputException {
        //1. Customer
        //2. Employee
        int userType = type;
        if (userType < 1 || userType > 3)
            throw new BadInputException("Legal user type values: 1-2.");
        
        if (userType == 1) {
            String phoneNumber = extra1;
            
            int dlnumber = Integer.parseInt(extra2);
            if (dlnumber < 0)
                throw new BadInputException("Driver license number cannot be negative.");
            
            users.add(new Customer(userIdCounter++, firstName, lastName, 
                    phoneNumber, dlnumber));
        } else if (userType == 2) {
            float monthlySalary = Float.parseFloat(extra1);
            if (monthlySalary < 0.0f)
                throw new BadInputException("Monthly salary cannot be negative.");
            
            int bankAccNumber = Integer.parseInt(extra2);
            if (bankAccNumber < 0)
                throw new BadInputException("Driver license number cannot be negative.");
            
            users.add(new Employee(userIdCounter++, firstName, lastName, 
                    monthlySalary, bankAccNumber));
        }
    }
    
    public boolean deleteUser(String idS) {
        int id = Integer.parseInt(idS);
        for (User u : users) {
            if (u.getId() == id) {
                users.remove(u);
                return true;
            }
        }
        return false;
    }
    
    public User getUser(int id) {
        for (User u : users) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    /**
     * This method can be used to update a user's information, given their user ID.
     * @param sc The scanner object used to read user input.
     * @throws Dealership.BadInputException
     */
    public void updateUser(int type, String id, String firstName, String lastName, String extra1, String extra2) throws BadInputException {
        int userID = Integer.parseInt(id);
        
        User user = getUser(userID);
        
        if (user == null) {
            return;
        }
        
        if (user instanceof Customer) {
            String phoneNumber = extra1;
            
            int dlnumber = Integer.parseInt(extra2);
            if (dlnumber < 0)
                throw new BadInputException("Driver license number cannot be negative.");
            
            user.setFirstName(firstName);
            user.setLastName(lastName);
            ((Customer)user).setPhoneNumber(phoneNumber);
            ((Customer)user).setDriverLicenceNumber(dlnumber);
            
        } else { //User is an employee
            float monthlySalary = Float.parseFloat(extra1);
            if (monthlySalary < 0.0f)
                throw new BadInputException("Monthly salary cannot be negative.");
            
            int bankAccNumber = Integer.parseInt(extra2);
            if (bankAccNumber < 0)
                throw new BadInputException("Driver license number cannot be negative.");
            
            user.setFirstName(firstName);
            user.setLastName(lastName);
            ((Employee)user).setMonthlySalary(monthlySalary);
            ((Employee)user).setBankAccountNumber(bankAccNumber);
        }
    }

    /**
     * Prints out a list of all users in the database.
     */
    public String[][] showAllUsers() {
        String[][] arr = new String[users.size()][5];
        for(int i = 0; i < users.size(); i++) {
            arr[i] = users.get(i).toArray();
        }
        return arr;
    }

    /**
     * This method is used to complete a vehicle sale transaction.
     * @param sc The scanner object used to read user input.
     * @throws Dealership.BadInputException
     */
    public void sellVehicle(String customerIdS, String employeeIdS, String vin, String priceS) throws BadInputException {
        int customerId = Integer.parseInt(customerIdS);
        //Check that the customer exists in database
        boolean customerExists = false;
        for (User u : users) {
            if (u.getId() == customerId)
                customerExists = true;
        }
        if (!customerExists) {
            throw new BadInputException("\nThe customer ID you have entered does not exist in the database.\n"
                    + "Please add the customer to the database first and then try again.");
        }
        
        int employeeId = Integer.parseInt(employeeIdS);
        //Check that the employee exists in database
        boolean employeeExists = false;
        for (User u : users) {
            if (u.getId() == employeeId)
                employeeExists = true;
        }
        if (!employeeExists) {
            throw new BadInputException("\nThe employee ID you have entered does not exist in the database.\n"
                    + "Please add the employee to the database first and then try again.");
        }
        
        //Check that the vehicle exists in database
        Vehicle v = findVehicle(vin);
        if (v == null) {
            throw new BadInputException("\nThe vehicle with the VIN you are trying to sell "
                    + "does not exist in the database. Aborting transaction.");
        }
        
        Date currentDate = new Date(System.currentTimeMillis());
        
        float price = Float.parseFloat(priceS);
        if (price < 0.0f)
            throw new BadInputException("Sale price cannot be negative.");
        
        SaleTransaction trans = new SaleTransaction(customerId, employeeId, vin, 
                currentDate, price);
        transactions.add(trans);
        vehicleInventory.remove(v); //Sold vehicles are automatically removed from the inventory.
    }
    
    /**
     * Prints out a list of all recorded transactions.
     */
    public String[][] showAllSales() {
        String[][] arr = new String[transactions.size()][5];
        for(int i = 0; i < transactions.size(); i++) {
            arr[i] = transactions.get(i).toArray();
        }
        return arr;
    }
    
    /**
     * Auxiliary method used to find a vehicle in the database, given its
     * VIN number.
     * @param vin
     * @return The vehicle found, or otherwise null.
     */
    public Vehicle findVehicle(String vin) {
        for (Vehicle v : vehicleInventory) {
            if (v.getVin().equals(vin))
                return v;
        }
        return null;
    }
    
    /**
     * This method is used to read the database from a file, serializable objects.
     * @return A new Dealership object.
     */
    @SuppressWarnings("unchecked") // This will prevent Java unchecked operation warning when
    // convering from serialized Object to Arraylist<>
    public static Dealership readDatabase() {
        Dealership cds = null;

        // Try to read existing dealership database from a file
        InputStream file = null;
        InputStream buffer = null;
        ObjectInput input = null;
        try {
            file = new FileInputStream("DealershipDatabase.ser");
            buffer = new BufferedInputStream(file);
            input = new ObjectInputStream(buffer);
            
            // Read serilized data
            List<Vehicle> vehicleInventory = (ArrayList<Vehicle>) input.readObject();
            List<User> users = (ArrayList<User>) input.readObject();
            List<SaleTransaction> transactions = (ArrayList<SaleTransaction>) input.readObject();
            cds = new Dealership(vehicleInventory, users, transactions);
            cds.userIdCounter = input.readInt();
            
            input.close();
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.toString());
        } catch (FileNotFoundException ex) {
            System.err.println("Database file not found.");
        } catch (IOException ex) {
            System.err.println(ex.toString());
        } finally {
            close(file);
        }
        
        return cds;
    }
    
    /**
     * This method is used to save the Dealership database as a 
     * serializable object.
     * @param cds
     */
    public void writeDatabase() {
        //serialize the database
        OutputStream file = null;
        OutputStream buffer = null;
        ObjectOutput output = null;
        try {
            file = new FileOutputStream("DealershipDatabase.ser");
            buffer = new BufferedOutputStream(file);
            output = new ObjectOutputStream(buffer);
            
            output.writeObject(vehicleInventory);
            output.writeObject(users);
            output.writeObject(transactions);
            output.writeInt(userIdCounter);
            
            output.close();
        } catch (IOException ex) {
            System.err.println(ex.toString());
        } finally {
            close(file);
        }
    }
    
    /**
     * Auxiliary convenience method used to close a file and handle possible
     * exceptions that may occur.
     * @param c
     */
    public static void close(Closeable c) {
        if (c == null) {
            return;
        }
        try {
            c.close();
        } catch (IOException ex) {
            System.err.println(ex.toString());
        }
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        /* Use an appropriate Look and Feel */
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        
         Dealership ddb = Dealership.readDatabase();
         if (ddb == null) ddb = new Dealership();
        //Create and set up the window.
        GUI frame = new GUI(ddb);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
     
    public static void main(String[] args) {
         
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
