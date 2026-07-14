package me.yassu.bookstore.order.web.controllers;

import jakarta.validation.Valid;
import java.util.List;
import me.yassu.bookstore.order.domain.OrderNotFoundException;
import me.yassu.bookstore.order.domain.OrderService;
import me.yassu.bookstore.order.domain.SecurityService;
import me.yassu.bookstore.order.domain.models.CreateOrderRequest;
import me.yassu.bookstore.order.domain.models.CreateOrderResponse;
import me.yassu.bookstore.order.domain.models.OrderDTO;
import me.yassu.bookstore.order.domain.models.OrderSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;
    private final SecurityService securityService;

    OrderController(OrderService orderService, SecurityService securityService) {
        this.orderService = orderService;
        this.securityService = securityService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateOrderResponse createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest) {
        String userName = securityService.getLoginUserName();
        log.info("Creating order for user {}", userName);
        return orderService.createOrder(userName, createOrderRequest);
    }

    @GetMapping
    List<OrderSummary> getOrders() {
        String userName = securityService.getLoginUserName();
        log.info("Retrieving orders for user {}", userName);
        return orderService.findOrders(userName);
    }

    @GetMapping("/{orderNumber}")
    OrderDTO getOrder(@PathVariable("orderNumber") String orderNumber) {
        String userName = securityService.getLoginUserName();
        return orderService
                .findUserOrder(userName, orderNumber)
                .orElseThrow(() -> new OrderNotFoundException(orderNumber));
    }
}
