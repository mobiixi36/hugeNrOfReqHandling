package com.xi.hugeNrOfReqHandling.api;

import com.google.common.util.concurrent.RateLimiter;
import com.xi.hugeNrOfReqHandling.annotation.MyReqRateLimiter;
import com.xi.hugeNrOfReqHandling.model.Order;
import com.xi.hugeNrOfReqHandling.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author xichen created on 10/02/2020
 */

@RequestMapping("api/v1/order")
@RestController
public class OrderController {
    private final OrderService orderService;

    // create 2 tokens per second, it is in another thread
    RateLimiter rateLimiter = RateLimiter.create(2);

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public void addPerson(@RequestBody @Valid @NotNull Order order) {

        orderService.addOrder(order);
    }

    // limit nr of requests. In 500ms if couldn't acquire a token, can't proceed forward
    @MyReqRateLimiter(rate = 1, timeout = 5000)
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping(path = "{id}")
    public Order getOrderById(@PathVariable("id") UUID id) {
        return orderService.getOrderById(id).orElse(null);
    }

    @DeleteMapping(path = "{id}")
    public void deleteOrder(@PathVariable("id") UUID id) {
        orderService.deleteOrder(id);
    }

    @PutMapping(path = "{id}")
    public void updateOrder(@PathVariable("id") UUID id,
                            @RequestBody @Valid @NotNull Order order) {
        boolean result = orderService.updateOrder(id, order);
        System.out.println("update result : " + result);
    }
}
