package de.runtimeterror.customer.rights;

import java.util.Arrays;

public class CustomerNotAllowedException extends RuntimeException{

    public CustomerNotAllowedException(Integer id, CustomerRights[] customerRight) {
        super(String.format("Customer with id %d not found: %s", id, Arrays.toString(customerRight)));
    }

    public CustomerNotAllowedException(String message) {
        super(message);
    }
}
