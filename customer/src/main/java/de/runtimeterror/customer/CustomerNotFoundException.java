package de.runtimeterror.customer;

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException(Integer id) {
        super(String.format("Customer with id %d not found", id));
    }

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
