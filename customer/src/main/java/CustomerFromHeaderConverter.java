import de.runtimeterror.customer.Customer;
import de.runtimeterror.customer.CustomerNotFoundException;
import de.runtimeterror.customer.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Getter
@Component
public class CustomerFromHeaderConverter implements Converter<Integer, Customer> {

    private CustomerRepository customerRepository;

    @Override
    public Customer convert(@NonNull Integer customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
    }
}
