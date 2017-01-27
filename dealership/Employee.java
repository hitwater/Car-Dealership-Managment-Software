/*
 * Car Dealership Managment Software v0.1 
 * * @author Guiming Huang
 */

package dealership;

import java.util.Arrays;

/**
 *
 * @author vangelis
 */
public class Employee extends User {
    private float monthlySalary;
    private int bankAccountNumber;

    /**
     * Default constructor.
     */
    public Employee() {
        this.monthlySalary = 0.0f;
        this.bankAccountNumber = 0;
    }

    /**
     * Constructor initializes an employee object with the provided values.
     * @param id
     * @param firstName
     * @param lastName
     * @param monthlySalary
     * @param bankAccountNumber
     */
    public Employee(int id, String firstName, String lastName, float monthlySalary, int bankAccountNumber) {
        super(id, firstName, lastName);
        this.monthlySalary = monthlySalary;
        this.bankAccountNumber = bankAccountNumber;
    }

    /**
     * Get the monthly salary.
     * @return monthlySalary
     */
    public float getMonthlySalary() {
        return monthlySalary;
    }

    /**
     * Set the monthly salary.
     * @param monthlySalary
     */
    public void setMonthlySalary(float monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    /**
     * Get the bank account number.
     * @return
     */
    public int getBankAccountNumber() {
        return bankAccountNumber;
    }

    /**
     * Set the bank account number.
     * @param bankAccountNumber
     */
    public void setBankAccountNumber(int bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }
    
    /**
     * Print the attributes of the employee, in a formatted fashion.
     */
    @Override
    public void print() {
        System.out.format("| %10s | %9d | %12s | %12s | Salary: %9s, Bank#: %8d | %n", 
                "Employee", id, firstName, lastName, monthlySalary, bankAccountNumber);
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + id + ", firstName=" + firstName 
                + ", lastName=" + lastName + ", monthlySalary=" + monthlySalary 
                + ", bankAccountNumber=" + bankAccountNumber + '}';
    }
    
    @Override
    public String[] toArray() {
        String[] arr = Arrays.copyOf(super.toArray(), 5);
        arr[3] = "Employee";
        return arr;
    }
}
