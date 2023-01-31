package de.runtimeterror.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@JsonTest
public class CustomerTest {

    @Autowired
    private JacksonTester<Customer> jacksonTester;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldSerializeObject() throws IOException {
        assertNotNull(objectMapper);

        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setId(1);
        customer.setEmail("test@test.de");

        JsonContent<Customer> result = jacksonTester.write(customer);
        assertThat(result).extractingJsonPathStringValue("@.firstName").isEqualTo("John");
        assertThat(result).extractingJsonPathStringValue("@.lastName").isEqualTo("Doe");
        assertThat(result).doesNotHaveJsonPath("@.id");
        assertThat(result).extractingJsonPathStringValue("@.email").isEqualTo("test@test.de");

    }

}
