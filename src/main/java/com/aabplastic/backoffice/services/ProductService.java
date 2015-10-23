package com.aabplastic.backoffice.services;

import com.aabplastic.backoffice.models.Product;

public interface ProductService {

    Product getProductById(long id);

    Product create(Product product);

    Product update(long id, Product product);

    void delete(long id);

    Iterable<Product> listProducts();
}

