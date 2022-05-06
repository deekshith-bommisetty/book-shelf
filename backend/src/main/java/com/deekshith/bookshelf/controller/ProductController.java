package com.deekshith.bookshelf.controller;

import com.deekshith.bookshelf.config.service.UserDetailsImpl;
import com.deekshith.bookshelf.model.Product;
import com.deekshith.bookshelf.model.ProductReview;
import com.deekshith.bookshelf.model.builder.ProductBuilder;
import com.deekshith.bookshelf.payload.request.ProductRequest;
import com.deekshith.bookshelf.payload.response.MessageResponse;
import com.deekshith.bookshelf.payload.response.Response;
import com.deekshith.bookshelf.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ProductController {

    @Autowired
    ProductServiceImpl productService;

    // @desc    Create new review
    // @route   POST /api/products/:id/reviews
    // @access  Private
    @RequestMapping(value = "/api/products/{id}/review", method = RequestMethod.POST)
    public ResponseEntity<?> createProductReview(@PathVariable("id") String id, @RequestBody ProductReview productReview) {
        Product product = productService.getProduct(id);
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
            return ResponseEntity.ok(productService.saveProduct(product));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Unable to review product"));
        }
    }

    // @desc    Update a product
    // @route   PUT /api/products/:id
    // @access  Private/Admin
    @RequestMapping(value = "/api/products/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

    // @desc    Delete a product
    // @route   DELETE /api/products/:id
    // @access  Private/Admin
    @RequestMapping(value = "/api/products/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") String id) {

        Product product = productService.getProduct(id);
        if(product != null){
            productService.deleteProduct(id);
            return ResponseEntity.ok(new MessageResponse("Product has benn deleted"));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Unable to delete product"));
        }
    }

    // @desc    Fetch all products
    // @route   GET /api/products
    // @access  Public
    @RequestMapping(value = "/api/products", method = RequestMethod.GET)
    public ResponseEntity<?> getAllProducts() {
        // Check for admin role later
        List<Product> productList = productService.getProducts();
        Response data = new Response(productList);
        if(!productList.isEmpty()){
            return ResponseEntity.ok(data);
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Products not found"));
        }
    }

    // @desc    Create a product
    // @route   POST /api/products
    // @access  Private/Admin
    @RequestMapping(value = "/api/products", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        ProductBuilder productBuilder = new ProductBuilder();
        Product product = productBuilder.setName(productRequest.getName()).setImage(productRequest.getImage()).setBrand(productRequest.getBrand()).setCategory(productRequest.getCategory()).setDescription(productRequest.getDescription()).setRating(productRequest.getRating()).setNumReviews(productRequest.getNumReviews()).setPrice(productRequest.getPrice()).setCountInStock(productRequest.getCountInStock()).getProducct();
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        product.setUser(userDetails.getId().toString());
        return ResponseEntity.ok(productService.saveProduct(product));
    }

    // @desc    Fetch single product
    // @route   GET /api/products/:id
    // @access  Public
    @RequestMapping(value = "/api/products/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getProductById(@PathVariable("id") String id) {

        Product product = productService.getProduct(id);
        if(product != null){
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Product not found"));
        }
    }
}
