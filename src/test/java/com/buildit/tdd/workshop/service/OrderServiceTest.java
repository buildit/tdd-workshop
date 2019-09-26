package com.buildit.tdd.workshop.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.buildit.tdd.workshop.domain.Order;
import com.buildit.tdd.workshop.entity.OrderEntity;
import com.buildit.tdd.workshop.exception.OrderNotFoundException;
import com.buildit.tdd.workshop.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

	@InjectMocks
	private OrderService orderService;
	
	@Mock
	private OrderRepository orderRepository;
	
	@Mock
	private ObjectMapper objectMapper;
	
	private Order order;
	private OrderEntity orderEntity;
	private OrderEntity orderEntityRequest;
	private List<Order> orderList = new ArrayList<>();
	private List<OrderEntity> orderEntityList = new ArrayList<>();
	
	private final long VALID_ORDER_ID = 1L;
	private final long INVALID_ORDER_ID = 2L;
	
	@Before
	public void setup() {
		Date date = Date.valueOf("2019-01-01");
		order = Order.builder().cartId(1L).customerId(1L).orderDate(date).orderId(1L).totalAmount(100.0).build();
		orderEntity = OrderEntity.builder().cartId(1L).customerId(1L).orderDate(date).orderId(1L).totalAmount(100.0).build();
		orderEntityRequest = OrderEntity.builder().cartId(1L).customerId(1L).orderDate(date).totalAmount(100.0).build();;
		orderEntityList.add(orderEntity);
		orderList.add(order);
	}
	
	@Test
	public void getOrders_shouldReturnOrders_whenOrdersExists() {
		
		when(orderRepository.findAll()).thenReturn(orderEntityList);
		when(objectMapper.convertValue(orderEntity, Order.class)).thenReturn(order);
		
		List<Order> response = orderService.getOrders();
		
		assertEquals(orderList.size(), response.size());
		
	}
	
	@Test
	public void getOrder_shouldReturnOrders_whenOrderExists() throws OrderNotFoundException {
		Optional orderEntityOptional = Optional.ofNullable(orderEntity);
		
		when(orderRepository.findById(VALID_ORDER_ID)).thenReturn(orderEntityOptional);
		when(objectMapper.convertValue(orderEntity, Order.class)).thenReturn(order);
		
		Order response = orderService.getOrder(VALID_ORDER_ID);
		
		assertEquals(order.getCartId(), response.getCartId());
	}
	
	@Test(expected = OrderNotFoundException.class)
	public void getOrder_shouldThrowOrderNotFoundException_whenOrderExists() throws OrderNotFoundException {
		
		when(orderRepository.findById(INVALID_ORDER_ID)).thenReturn(Optional.empty());
		
		orderService.getOrder(INVALID_ORDER_ID);
	}
	
	@Test
	public void createOrder_shouldReturnOrder_forValidRequest() {
		// TODO: write tests
	}
}
