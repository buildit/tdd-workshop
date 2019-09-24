package com.buildit.tdd.workshop.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.buildit.tdd.workshop.domain.Product;
import com.buildit.tdd.workshop.entity.ProductEntity;
import com.buildit.tdd.workshop.exception.ProductNotCreatedException;
import com.buildit.tdd.workshop.exception.ProductNotFoundException;
import com.buildit.tdd.workshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ObjectMapper objectMapper;

	public List<Product> getProducts() {
		List<ProductEntity> productEntityList = productRepository.findAll();
		return productEntityList.stream().map(productEntity -> objectMapper.convertValue(productEntity, Product.class))
				.collect(Collectors.toList());
	}
	
	public Product createProduct(Product product) throws ProductNotCreatedException {
		ProductEntity productEntity = objectMapper.convertValue(product, ProductEntity.class);
		try {
			ProductEntity responseProductEntity = productRepository.save(productEntity);
			Product response = objectMapper.convertValue(responseProductEntity, Product.class);
			return response;
		} catch (RuntimeException e) {
			throw new ProductNotCreatedException();
		}
	}
	
	public Product getProduct(Long productId) throws ProductNotFoundException {
		Optional<ProductEntity> response = productRepository.findById(productId);
		if(!response.isPresent()) {
			throw new ProductNotFoundException();
		}
		return objectMapper.convertValue(response.get(), Product.class);
	}

}
