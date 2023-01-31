package de.runtimeterror.customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ApplicationTest {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    @Test
    void contextLoads(){
        assertNotNull(customerController);
        assertNotNull(customerRepository);
        assertNotNull(customerService);
    }
}
