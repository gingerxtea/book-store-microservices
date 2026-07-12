package me.yassu.bookstore.catalog.web.controllers;

import me.yassu.bookstore.catalog.domain.Pagedresult;
import me.yassu.bookstore.catalog.domain.Product;
import me.yassu.bookstore.catalog.domain.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
class ProductController {

    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    Pagedresult<Product> getProducts(@RequestParam(name = "page", defaultValue = "1") int pageNo) {
        return productService.getProducts(pageNo);
    }

    @GetMapping("/{code}")
    ResponseEntity<Product> getProduct(@PathVariable(name = "code") String code) {
        return ResponseEntity.ok(productService.getProductByCode(code));
    }
}
