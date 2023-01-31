package de.runtimeterror.clients.fraud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "fraud",
        url = "${clients.fraud.url:http://localhost:8082/fraud}"
)
public interface FraudClient {

    @GetMapping(path = "api/v1/fraud-check/{customerId}")
    FraudCheckResponse isFraudster(
            @PathVariable("customerId") Integer customerId);
}
