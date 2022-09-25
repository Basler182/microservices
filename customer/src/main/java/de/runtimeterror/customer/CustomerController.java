package de.runtimeterror.customer;

import de.runtimeterror.clients.orders.OrdersClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("api/v1/customers")
public record CustomerController(CustomerService customerService, CustomerRepository customerRepository, OrdersClient ordersClient) {



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
    @GetMapping(produces = "application/json")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Operation(summary = "Get a customer by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)})
    @GetMapping(path = "/{customerId}", produces = "application/json")
    public Customer getCustomer(@PathVariable @NotNull Integer customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
    }
}
