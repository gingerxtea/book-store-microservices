package me.yassu.bookstore.order.domain;

import me.yassu.bookstore.order.client.Product;
import me.yassu.bookstore.order.client.ProductServiceClient;
import me.yassu.bookstore.order.domain.models.CreateOrderRequest;
import me.yassu.bookstore.order.domain.models.OrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class OrderValidator {

    private static final Logger log = LoggerFactory.getLogger(OrderValidator.class);
    
    private final ProductServiceClient productServiceClient;

    OrderValidator(ProductServiceClient productServiceClient) {
        this.productServiceClient = productServiceClient;
    }

    void validateOrder(CreateOrderRequest request) {
        if (request != null && request.items() != null && !request.items().isEmpty()) {
            for (OrderItem item : request.items()) {
                Product product = productServiceClient.getProductByCode(item.code())
                        .orElseThrow(() -> new InvalidOrderException("Invalid Product Code: " + item.code()));

                if (item.price().compareTo(product.price()) != 0) {
                    log.error("Product price not matching");
                    throw new InvalidOrderException("Product price not matching");
                }
            }
        }
    }
}
