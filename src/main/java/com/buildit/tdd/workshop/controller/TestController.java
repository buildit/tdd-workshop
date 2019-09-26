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
@RequestMapping(value = "/test")
public class TestController {

	
	@GetMapping
	public String getAll() {
		
		return "1";
	}
	
	
	
	
}
