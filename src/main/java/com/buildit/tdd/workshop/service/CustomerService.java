package com.buildit.tdd.workshop.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buildit.tdd.workshop.domain.Customer;
import com.buildit.tdd.workshop.entity.CustomerEntity;
import com.buildit.tdd.workshop.exception.CustomerNotFoundException;
import com.buildit.tdd.workshop.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	public  Customer getCustomer(Long customerId) throws CustomerNotFoundException {
		Optional<CustomerEntity> response = customerRepository.findById(customerId);
		
		if(!response.isPresent()) {
			throw new CustomerNotFoundException();
		}
		
		return objectMapper.convertValue(response.get(), Customer.class);
		
	}

}
