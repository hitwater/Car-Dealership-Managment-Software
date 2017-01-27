/*
 * Car Dealership Managment Software v0.1 
 * * @author Guiming Huang
 */
package dealership;

/**
 * Custom Exception type, used to report bad input from user.
 * @author vangelis
 */
public class BadInputException extends Exception {

    /**
     * Constructor, allows custom message assignment for thrown exception.
     * @param message
     */
    public BadInputException(String message) {
        super(message);
    }
}
