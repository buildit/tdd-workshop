package com.buildit.tdd.workshop.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.buildit.tdd.workshop.domain.Customer;
import com.buildit.tdd.workshop.exception.CustomerNotFoundException;
import com.buildit.tdd.workshop.service.CustomerService;


@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {
	
	@InjectMocks
	private CustomerController customerController;
		
	@Mock
	private CustomerService customerService;

	private Customer customer;
	
	
	@Before
	public void setup() {
		customer = customer.builder().name("simar").phone("9876543210").customerId(1L).build();
	}
	
	
	@Test
	public void getCustomer_shouldReturnCustomerDetails_whenCustomerIdExists() throws CustomerNotFoundException {
		when(customerService.getCustomer(1L)).thenReturn(customer);
		
		ResponseEntity<Customer> response = customerController.getCustomer(1L);

		assertEquals(200, response.getStatusCode().value());
		assertEquals("simar", response.getBody().getName());
		assertEquals("9876543210", response.getBody().getPhone());
		assertEquals(1L, response.getBody().getCustomerId());
	}
	
	
	@Test
	public void getCustomer_shouldReturnNothing_whenCustomerIdDoesNotExists() throws CustomerNotFoundException {
		when(customerService.getCustomer(1L)).thenThrow(CustomerNotFoundException.class);
		
		ResponseEntity<Customer> response = customerController.getCustomer(1L);

		assertEquals(404, response.getStatusCode().value());
	}
	
	

}
