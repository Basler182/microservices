package de.runtimeterror.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication(
        scanBasePackages = {
                "de.runtimeterror.customer",
                "de.runtimeterror.amqp",
        }
)
@EnableEurekaClient
@EnableFeignClients(
        basePackages = "de.runtimeterror.clients"
)
@PropertySources({
     @PropertySource(value = "classpath:clients-${spring.profiles.active}.properties", ignoreResourceNotFound = true),
})
public class CustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }
}
