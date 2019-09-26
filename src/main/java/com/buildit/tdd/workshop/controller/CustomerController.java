package com.buildit.tdd.workshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buildit.tdd.workshop.domain.Customer;
import com.buildit.tdd.workshop.exception.CustomerNotFoundException;
import com.buildit.tdd.workshop.service.CustomerService;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Customer> getCustomer(@PathVariable(value = "id") Long customerId) {
		Customer response;
		try {
			response = customerService.getCustomer(customerId);
			return ResponseEntity.ok(response);
		} catch(CustomerNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
		
	}

}
