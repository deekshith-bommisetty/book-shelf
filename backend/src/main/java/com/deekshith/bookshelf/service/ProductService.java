package com.deekshith.bookshelf.service;

import com.deekshith.bookshelf.model.Product;
import com.deekshith.bookshelf.payload.request.ProductRequest;
import com.deekshith.bookshelf.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public Product updateProduct(String id, ProductRequest productRequest){

        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            Product fetchedProduct = product.get();
            if(productRequest.getName() != null){
                fetchedProduct.setName(fetchedProduct.getName());
            }
            if(productRequest.getImage() != null){
                fetchedProduct.setImage(productRequest.getImage());
            }
            if(productRequest.getBrand() != null){
                fetchedProduct.setBrand(productRequest.getBrand());
            }
            if(productRequest.getCategory() != null){
                fetchedProduct.setCategory(productRequest.getCategory());
            }
            if(productRequest.getDescription() != null){
                fetchedProduct.setDescription(productRequest.getDescription());
            }
            if(productRequest.getRating() != null){
                fetchedProduct.setRating(productRequest.getRating());
            }
            if(productRequest.getNumReviews() != null){
                fetchedProduct.setNumReviews(productRequest.getNumReviews());
            }
            if(productRequest.getPrice() != null){
                fetchedProduct.setPrice(productRequest.getPrice());
            }
            if(productRequest.getCountInStock() != null){
                fetchedProduct.setCountInStock(productRequest.getCountInStock());
            }
            return productRepository.save(fetchedProduct);
        }
        return null;
    }

    public Product retrieveProduct(String id){
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            Product fetchedProduct = product.get();
            return fetchedProduct;
        }
        return null;
    }

    public List <Product> retrieveAllProducts(){
        return productRepository.findAll();
    }

    public void deleteProduct(String id){
        productRepository.deleteById(id);
    }
}
