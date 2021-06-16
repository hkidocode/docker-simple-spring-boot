package com.example.dockerizespringbootapp.controller;

import com.example.dockerizespringbootapp.exception.ProductNotFoundException;
import com.example.dockerizespringbootapp.model.Product;
import com.example.dockerizespringbootapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable("productId") Long productId) {
         Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
         return ResponseEntity.ok().body(product);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProduct() {
        return ResponseEntity.ok().body(productRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        product.setIdProduct(0L);
        Product addedProduct = productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct);
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(savedProduct);
    }

    @DeleteMapping("{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product with id " + productId + " not found"));
        productRepository.delete(product);
        return ResponseEntity.noContent().build();
    }

}
