package de.runtimeterror.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @MockBean
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private CustomerDTOMapper customerDTOMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @BeforeEach
    void setupMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(new CustomerController(customerService, customerRepository, customerDTOMapper))
                .build();
    }

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    @Test
    void getAllCustomerTest() throws Exception {
        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void shouldGetEmptyArrayWhenNoCustomersExists() throws Exception {
        this.mockMvc
                .perform(get("/api/v1/customers").header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
    }

    @Test
    void shouldNotReturnXML() throws Exception {
        this.mockMvc
                .perform(get("/api/v1/customers")
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML))
                .andExpect(status().isNotAcceptable());
    }


    @Test
    void shouldGetCustomersWhenServiceReturnsCustomers() throws Exception {

        Customer customerOne = createCustomer(1, "Kilian", "Schneider", "ks@runtimeterror.de");
        Customer customerTwo = createCustomer(2, "Andrea", "Florea", "af@runtimeterorr.de");
        when(customerRepository.findAll()).thenReturn(List.of(customerOne, customerTwo));

        this.mockMvc
                .perform(get("/api/v1/customers")
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$[0].firstName", is("Kilian")))
                .andExpect(jsonPath("$[0].lastName", is("Schneider")))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].email", is("ks@runtimeterror.de")))
                .andExpect(jsonPath("$[1].firstName", is("Andrea")))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    private Customer createCustomer(Integer id, String firstName, String lastName, String email) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        return customer;
    }
}
