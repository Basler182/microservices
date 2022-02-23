package de.runtimeterror.customer;

import de.runtimeterror.clients.fraud.FraudCheckResponse;
import de.runtimeterror.clients.fraud.FraudClient;
import de.runtimeterror.clients.notification.NotificationClient;
import de.runtimeterror.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final NotificationClient notificationClient;

    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRequest.firstName())
                .lastName(customerRegistrationRequest.lastName())
                .email(customerRegistrationRequest.email())
                .build();
        // todo : check if email valid
        // todo: check if email not taken
        customerRepository.saveAndFlush(customer);
        // todo: check if fraudster

        FraudCheckResponse fraudCheckResponse  = fraudClient.isFraudster(customer.getId());

        if(fraudCheckResponse == null ||fraudCheckResponse.isFraudster()){
            throw new IllegalStateException("fraudster");
        }
        // todo: make it async. i.e add to queue
        notificationClient.sendNotification(
                new NotificationRequest(
                        customer.getId(),
                        customer.getEmail(),
                        String.format("Hi %s, welcome to service...",
                                customer.getFirstName())
                )
        );
    }
}
