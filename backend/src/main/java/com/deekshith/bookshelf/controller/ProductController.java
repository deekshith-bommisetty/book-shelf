package com.deekshith.bookshelf.controller;

import com.deekshith.bookshelf.config.service.UserDetailsImpl;
import com.deekshith.bookshelf.model.Product;
import com.deekshith.bookshelf.model.ProductReview;
import com.deekshith.bookshelf.payload.request.ProductRequest;
import com.deekshith.bookshelf.payload.response.MessageResponse;
import com.deekshith.bookshelf.repository.ProductRepository;
import com.deekshith.bookshelf.service.ProductService;
import com.deekshith.bookshelf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @RequestMapping(value = "/api/products/{id}/review", method = RequestMethod.POST)
    public ResponseEntity<?> createProductReview(@PathVariable("id") String id, @RequestBody ProductReview productReview) {
        Product product = productService.retrieveProduct(id);
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(product != null){
            if(product.getReviews() != null){
                for(ProductReview review : product.getReviews()){
                    if(review.getId().toString() == userDetails.getId().toString()){
                        return ResponseEntity
                                .badRequest()
                                .body(new MessageResponse("You have already reviewed this product"));
                    }
                }
            }
            ProductReview review = new ProductReview(userDetails.getUsername(), productReview.getRating(),productReview.getComment(), userDetails.getId());
            product.setReviews(review);
            product.setNumReviews(product.getNumReviews() + 1);
            Double accumulator = 0.0;
            for(ProductReview currentReview : product.getReviews()){
                 accumulator = accumulator + currentReview.getRating();
            }
            product.setRating((Double) accumulator/(Integer) product.getReviews().size());
            return ResponseEntity.ok(productRepository.save(product));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Unable to review product"));
        }
    }

    @RequestMapping(value = "/api/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProduct(@PathVariable("id") String id, @RequestBody ProductRequest productRequest) {
        Product product = productService.updateProduct(id, productRequest);
        if(product != null){
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Unable to update product"));
        }
    }

    @RequestMapping(value = "/api/products/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProduct(@PathVariable("id") String id) {

        Product product = productService.retrieveProduct(id);
        if(product != null){
            productService.deleteProduct(id);
            return ResponseEntity.ok(new MessageResponse("Product has benn deleted"));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Unable to delete product"));
        }
    }

    @RequestMapping(value = "/api/products", method = RequestMethod.GET)
    public ResponseEntity<?> getAllProducts() {
        // Check for admin role later
        List<Product> productList = productService.retrieveAllProducts();
        if(!productList.isEmpty()){
            return ResponseEntity.ok(productList);
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Products not found"));
        }
    }

    // has role admin
    // take care of adding userReviews
    @RequestMapping(value = "/api/products", method = RequestMethod.POST)
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        Product product = new Product(productRequest.getName(), productRequest.getImage(), productRequest.getBrand(), productRequest.getCategory(), productRequest.getDescription(), productRequest.getRating(), productRequest.getNumReviews(), productRequest.getPrice(), productRequest.getCountInStock());
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        product.setUser(userDetails.getId().toString());
        return ResponseEntity.ok(productRepository.save(product));
    }

    @RequestMapping(value = "/api/products/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getProductByID(@PathVariable("id") String id) {

        Product product = productService.retrieveProduct(id);
        if(product != null){
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Product not found"));
        }
    }
}
