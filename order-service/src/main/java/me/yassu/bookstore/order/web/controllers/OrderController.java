package me.yassu.bookstore.order.web.controllers;

import jakarta.validation.Valid;
import me.yassu.bookstore.order.domain.OrderService;
import me.yassu.bookstore.order.domain.SecurityService;
import me.yassu.bookstore.order.domain.models.CreateOrderRequest;
import me.yassu.bookstore.order.domain.models.CreateOrderResponse;
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
}
