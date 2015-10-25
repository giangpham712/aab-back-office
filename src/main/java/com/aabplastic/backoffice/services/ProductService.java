package com.aabplastic.backoffice.services;

import com.aabplastic.backoffice.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface ProductService {

    Product getProductById(long id);

    Product create(Product product);

    Product update(long id, Product product);

    void delete(long id);

    Iterable<Product> listProducts();

    Page<Product> listProducts(int pageIndex, int limit, String sortBy, Sort.Direction sortDirection);
}

