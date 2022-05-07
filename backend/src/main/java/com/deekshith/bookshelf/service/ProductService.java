package com.deekshith.bookshelf.service;

import com.deekshith.bookshelf.model.Product;
import com.deekshith.bookshelf.payload.request.ProductRequest;

import java.util.List;

// Interface for product related services
public interface ProductService {
    Product saveProduct(Product product);
    void deleteProduct(String id);
    Product updateProduct(String id, ProductRequest productRequest);
    Product getProduct(String id);
    List<Product> getProducts();
}
