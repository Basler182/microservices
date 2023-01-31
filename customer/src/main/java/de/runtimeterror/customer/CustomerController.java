package de.runtimeterror.customer;

import de.runtimeterror.customer.rights.CustomerRights;
import de.runtimeterror.customer.rights.Secured;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    private final CustomerDTOMapper customerDTOMapper;

    public CustomerController(CustomerService customerService, CustomerRepository customerRepository, CustomerDTOMapper customerDTOMapper) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
        this.customerDTOMapper = customerDTOMapper;
    }

    @Operation(summary = "Register a new customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer registered successful"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)})
    @PostMapping(consumes = "application/json")
    public void registerCustomer(@RequestBody CustomerRegistrationRequest customerRegistrationRequest) {
        log.info("new customer registration {}", customerRegistrationRequest);
        customerService.registerCustomer(customerRegistrationRequest);
    }

    @Operation(summary = "Get all customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customers found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)})
    @Secured(CustomerRights.VIEW_CUSTOMER_LIST)
    @GetMapping(produces = "application/json")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Operation(summary = "Get a customer by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)})
    @Secured(CustomerRights.VIEW_CUSTOMER)
    @GetMapping(path = "/{customerId}", produces = "application/json")
    public CustomerDTO getCustomer(@PathVariable @NotNull Integer customerId, @RequestHeader("customerId") Customer customer) {
        return customerRepository.findById(customerId).map(customerDTOMapper).orElseThrow(() -> new CustomerNotFoundException(customerId));
    }
}
