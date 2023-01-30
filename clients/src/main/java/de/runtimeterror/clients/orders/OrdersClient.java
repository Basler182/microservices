package de.runtimeterror.clients.orders;

import de.runtimeterror.clients.util.Logged;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "orders",
        url = "${clients.orders.url}"
)
public interface OrdersClient {

    @Logged
    @GetMapping("api/v1/orders/{orderId}")
    OrderResponse getOrder(@PathVariable("orderId") Integer orderId);
}
