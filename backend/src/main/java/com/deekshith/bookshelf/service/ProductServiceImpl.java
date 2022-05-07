package com.deekshith.bookshelf.service;

import com.deekshith.bookshelf.model.Product;
import com.deekshith.bookshelf.payload.request.ProductRequest;
import com.deekshith.bookshelf.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Custom implementation of the ProductService interface with adapter pattern
@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product updateProduct(String id, ProductRequest productRequest) {
        Optional<Product> retrievedProduct = productRepository.findById(id);

        if (retrievedProduct.isPresent()) {
            Product product = retrievedProduct.get();
            if(productRequest.getName() != null){
                product.setName(productRequest.getName());
            }
            if(productRequest.getImage() != null){
                product.setImage(productRequest.getImage());
            }
            if(productRequest.getBrand() != null){
                product.setBrand(productRequest.getBrand());
            }
            if(productRequest.getCategory() != null){
                product.setCategory(productRequest.getCategory());
            }
            if(productRequest.getDescription() != null){
                product.setDescription(productRequest.getDescription());
            }
            if(productRequest.getRating() != null){
                product.setRating(productRequest.getRating());
            }
            if(productRequest.getNumReviews() != null){
                product.setNumReviews(productRequest.getNumReviews());
            }
            if(productRequest.getPrice() != null){
                product.setPrice(productRequest.getPrice());
            }
            if(productRequest.getCountInStock() != null){
                product.setCountInStock(productRequest.getCountInStock());
            }
            return productRepository.save(product);
        }
        return null;
    }

    @Override
    public Product getProduct(String id) {
        Optional<Product> retrievedProduct = productRepository.findById(id);
        if (retrievedProduct.isPresent()) {
            Product product = retrievedProduct.get();
            return product;
        }
        return null;
    }

    @Override
    public List<Product> getProducts() {
        List<Product> retrievedProducts = productRepository.findAll();
        if (!retrievedProducts.isEmpty()) {
            return  retrievedProducts;
        }
        return null;
    }
}
