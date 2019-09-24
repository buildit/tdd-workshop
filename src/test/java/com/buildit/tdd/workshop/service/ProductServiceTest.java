package com.buildit.tdd.workshop.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.buildit.tdd.workshop.entity.ProductEntity;
import com.buildit.tdd.workshop.domain.Product;
import com.buildit.tdd.workshop.exception.ProductNotCreatedException;
import com.buildit.tdd.workshop.exception.ProductNotFoundException;
import com.buildit.tdd.workshop.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

	@InjectMocks
	private ProductService productService;

	@Mock
	private ObjectMapper objectMapper;

	@Mock
	private ProductRepository productRepository;

	private List<ProductEntity> productEntityList = new ArrayList<>();
	private List<Product> productList = new ArrayList<>();
	private ProductEntity productEntity;
	private Product product;

	@Before
	public void setup() {
		productEntity = productEntity.builder().name("IPhone").manufacturer("Apple").category("Mobile Phones")
				.isReturnable(false).numDaysTillReturnable(0).build();
		product = product.builder().name("IPhone").manufacturer("Apple").category("Mobile Phones").isReturnable(false)
				.numDaysTillReturnable(0).build();

		productEntityList.add(productEntity);
		productList.add(product);
	}

	@Test
	public void getProducts_shouldReturnProductList_whenProductsExists() {
		when(productRepository.findAll()).thenReturn(productEntityList);
		when(objectMapper.convertValue(productEntity, Product.class)).thenReturn(product);
		List<Product> response = productService.getProducts();
		assertEquals(productList.size(), response.size());
	}

	@Test
	public void createProduct_shouldCreateProduct_succesfully() throws ProductNotCreatedException {
		ProductEntity productEntityResponse = productEntity;
		productEntityResponse.setProductId(1L);

		Product productResponse = product;
		productResponse.setProductId(1L);

		when(objectMapper.convertValue(product, ProductEntity.class)).thenReturn(productEntity);
		when(productRepository.save(productEntity)).thenReturn(productEntityResponse);
		when(objectMapper.convertValue(productEntityResponse, Product.class)).thenReturn(productResponse);

		Product response = productService.createProduct(product);

		assertEquals("Apple", response.getManufacturer());
		assertEquals(1L, response.getProductId());
	}
	
	@Test(expected = ProductNotCreatedException.class)
	public void createProduct_shouldThrowProductNotCreatedException_ifProductNotCreated() throws ProductNotCreatedException {
		when(objectMapper.convertValue(product, ProductEntity.class)).thenReturn(productEntity);
		when(productRepository.save(productEntity)).thenThrow(RuntimeException.class);
		
		productService.createProduct(product);
	}
	
	@Test
	public void getProduct_shouldReturnProduct_forValidProductId() throws ProductNotFoundException {
		productEntity.setProductId(1L);
		product.setProductId(1L);
		Optional productEntityOptional = Optional.ofNullable(productEntity);
		
		when(productRepository.findById(1L)).thenReturn(productEntityOptional);
		when(objectMapper.convertValue(productEntityOptional.get(), Product.class)).thenReturn(product);
		
		Product response = productService.getProduct(1L);
		
		assertEquals(1L, response.getProductId());
		assertEquals("IPhone", response.getName());
	}
	
	@Test(expected = ProductNotFoundException.class)
	public void getProduct_shouldThrowProductNotFoundException_forInvalidProductId() throws ProductNotFoundException {
		when(productRepository.findById(1L)).thenReturn(Optional.empty());
		productService.getProduct(1L);
	}

}
