package me.yassu.bookstore.catalog.domain;

import me.yassu.bookstore.catalog.ApplicationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ApplicationProperties appProperties;

    ProductService(ProductRepository productRepository, ApplicationProperties appProperties) {
        this.productRepository = productRepository;
        this.appProperties = appProperties;
    }

    public Pagedresult<Product> getProducts(int pageNo) {
        pageNo = pageNo <= 1 ? 0 : pageNo - 1;
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        Pageable pageable = PageRequest.of(pageNo, appProperties.pageSize(), sort);
        var pageResult = productRepository.findAll(pageable).map(ProductMapper::toProduct);
        return new Pagedresult<>(
                pageResult.getContent(),
                pageResult.getTotalElements(),
                pageResult.getNumber() + 1,
                pageResult.getTotalPages(),
                pageResult.isFirst(),
                pageResult.isLast(),
                pageResult.hasNext(),
                pageable.hasPrevious());
    }

    public Product getProductByCode(String code) {
        return productRepository
                .findByCode(code)
                .map(ProductMapper::toProduct)
                .orElseThrow(() -> ProductNotFoundException.forCode(code));
    }
}
