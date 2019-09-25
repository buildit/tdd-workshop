package com.buildit.tdd.workshop.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buildit.tdd.workshop.domain.Order;
import com.buildit.tdd.workshop.entity.OrderEntity;
import com.buildit.tdd.workshop.exception.OrderNotFoundException;
import com.buildit.tdd.workshop.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	private ObjectMapper objectMapper;

	public List<Order> getOrders() {
		
		List<OrderEntity> orderEntityList = orderRepository.findAll();
		
		return orderEntityList.stream().map(orderEntity -> objectMapper.convertValue(orderEntity, Order.class))
				.collect(Collectors.toList());
	}

	public Order getOrder(long orderId) throws OrderNotFoundException {
		Optional<OrderEntity> response = orderRepository.findById(orderId);
		if(!response.isPresent()) {
			throw new OrderNotFoundException();
		}
		return objectMapper.convertValue(response.get(), Order.class);
	}
	
	

	
	
}
