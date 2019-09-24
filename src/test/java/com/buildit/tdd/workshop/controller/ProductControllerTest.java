package com.buildit.tdd.workshop.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.buildit.tdd.workshop.domain.Product;
import com.buildit.tdd.workshop.exception.ProductNotCreatedException;
import com.buildit.tdd.workshop.exception.ProductNotFoundException;
import com.buildit.tdd.workshop.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {
	
	@InjectMocks
	private ProductController productController;
	
	@Mock
	private ProductService productService;
	
	private Product product;
	private List<Product> productList = new ArrayList<>();
	
	@Before
	public void setup() {
		product = product.builder().name("IPhone").manufacturer("Apple").stockQuantity(10)
				.category("Mobile Phones").isReturnable(false).numDaysTillReturnable(0).build();
		productList.add(product);
	}
	
	@Test
	public void getAllProducts_shouldReturnListofProductsinJson() {
		product = product.builder().name("IPhone").manufacturer("Apple").stockQuantity(10)
				.category("Mobile Phones").isReturnable(false).numDaysTillReturnable(0).build();
		productList.add(product);
		
		when(productService.getProducts()).thenReturn(productList);
		
		ResponseEntity<List<Product>> response = productController.getAllProducts();
		
		assertEquals(200, response.getStatusCode().value());
		assertEquals(productList.size(), response.getBody().size()); 
		
	}
	
	@Test
	public void createProduct_shouldCreateProduct_succesfully() throws ProductNotCreatedException {
		Product productResponse = product;
		productResponse.setProductId(1L);
		
		when(productService.createProduct(product)).thenReturn(productResponse);
		
		ResponseEntity<Product> response  = productController.createProduct(product);
		
		assertEquals(200, response.getStatusCode().value());
		assertEquals(productResponse.getProductId(), response.getBody().getProductId());
	}
	
	@Test
	public void createProduct_shouldNotCreateProduct_forInvalidProductEntry() throws ProductNotCreatedException {
		when(productService.createProduct(product)).thenThrow(ProductNotCreatedException.class);
		
		ResponseEntity responseEntity  = productController.createProduct(product);
		
		assertEquals(404, responseEntity.getStatusCode().value());
	}
	
	
	@Test
	public void getProduct_shouldReturnProduct_forValidProduct() throws ProductNotFoundException {
		product.setProductId(1L);
		when(productService.getProduct(1L)).thenReturn(product);
		
		ResponseEntity<Product> response = productController.getProduct(1L);
		
		assertEquals(200, response.getStatusCode().value());
		assertEquals(1L, response.getBody().getProductId());
	}
	
	@Test
	public void getProduct_shouldNotReturnProduct_forInvalidProductId() throws ProductNotFoundException {
		when(productService.getProduct(1L)).thenThrow(ProductNotFoundException.class);
		
		ResponseEntity<Product> response = productController.getProduct(1L);
		
		assertEquals(404, response.getStatusCode().value());
	}
	
	@Test
	public void getProduct_shouldReturnStockAvailability_forValidProductId() throws ProductNotFoundException {
		product.setProductId(1L);
		when(productService.getProduct(1L)).thenReturn(product);
		
		ResponseEntity<Product> response = productController.getProduct(1L);
		
		assertEquals(10, response.getBody().getStockQuantity());
	
	}

}
