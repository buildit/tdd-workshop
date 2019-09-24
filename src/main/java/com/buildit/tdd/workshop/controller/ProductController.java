package com.buildit.tdd.workshop.controller;

import java.util.List;

import com.buildit.tdd.workshop.domain.Product;
import com.buildit.tdd.workshop.exception.ProductNotCreatedException;
import com.buildit.tdd.workshop.exception.ProductNotFoundException;
import com.buildit.tdd.workshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

	@Autowired
	ProductService productService;
	
	@GetMapping
	public ResponseEntity getAllProducts() {
		List<Product> response  = productService.getProducts();
		return ResponseEntity.ok(response);
	}
	
	@PostMapping
	public ResponseEntity createProduct(@RequestBody Product product) {
		try {
			return ResponseEntity.ok(productService.createProduct(product));
		} catch (ProductNotCreatedException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity getProduct(@PathVariable(value = "id") Long productId) {
		Product response;
		try {
			response = productService.getProduct(productId);
			return ResponseEntity.ok(response);
		} catch (ProductNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	
}
