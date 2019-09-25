package com.buildit.tdd.workshop.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.buildit.tdd.workshop.domain.Customer;
import com.buildit.tdd.workshop.entity.CustomerEntity;
import com.buildit.tdd.workshop.exception.CustomerNotFoundException;
import com.buildit.tdd.workshop.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {
	
	@InjectMocks
	private  CustomerService customerService;
	
	@Mock
	private ObjectMapper objectMapper;
	
	@Mock
	private CustomerRepository customerRepository;
	
	private CustomerEntity customerEntity;
	private Customer customer;
	
	
	
	@Before
	public void setup() {
		
		customer = customer.builder().name("Simar").phone("9876543210").build();
		
	}
	
	
	
	@Test
	public void getCustomer_shouldReturnCustomerDetails_whenCustomerExists() {
		
	}
	
	@Test(expected = CustomerNotFoundException.class)
	public void getCustomer_shouldRaiseDoesNotExistsException_whenCustomerIdNotFound() throws CustomerNotFoundException {
//		when(customerRepository.findById((1L)).then
		
	}

}
