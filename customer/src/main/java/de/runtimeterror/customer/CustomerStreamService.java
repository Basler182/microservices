package de.runtimeterror.customer;

import com.speedment.jpastreamer.application.JPAStreamer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerStreamService {

    private final JPAStreamer jpaStreamer;

    public CustomerStreamService(JPAStreamer jpaStreamer) {
        this.jpaStreamer = jpaStreamer;
    }

    public Customer getCustomerById(Integer id){
        return jpaStreamer.stream(Customer.class)
                .filter(e-> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    public Customer getCustomerByEmail(String email){
        return jpaStreamer.stream(Customer.class)
                .filter(e-> e.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new CustomerNotFoundException(email));
    }

    public List<Customer> getCustomersByLastName(String lastName){
        return jpaStreamer.stream(Customer.class)
                .filter(e-> e.getLastName().equals(lastName))
                .toList();
    }

}
