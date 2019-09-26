package com.buildit.tdd.workshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buildit.tdd.workshop.domain.Address;
import com.buildit.tdd.workshop.exception.AddressNotCreatedException;
import com.buildit.tdd.workshop.exception.AddressNotDeletedException;
import com.buildit.tdd.workshop.exception.AddressNotFoundException;
import com.buildit.tdd.workshop.exception.AddressNotUpdatedException;
import com.buildit.tdd.workshop.service.AddressService;

@RestController
@RequestMapping(value = "/address")
public class AddressController {
	
	
	@Autowired
	private AddressService addressService;
	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Address> getAddress(@PathVariable(value = "id") Long addressId) throws AddressNotFoundException { 
		Address response;
		try {
			response = addressService.getAddress(addressId);
			return ResponseEntity.ok(response);
		} catch(AddressNotFoundException e) {
			return ResponseEntity.notFound().build();

		}
	}
	
	@GetMapping(value = "customer/{id}")
	public ResponseEntity getAddresses(@PathVariable(value="id") Long customerId) throws AddressNotFoundException {
		try {
			List<Address> response  = addressService.getAddresses(customerId);
			if(response.size() == 0) {
				throw new AddressNotFoundException();
			} else {
				return ResponseEntity.ok(response);
			}
		} catch (AddressNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping 
	public ResponseEntity createAddress(@RequestBody Address address) {
		try {
			return ResponseEntity.ok(addressService.createAddress(address));
		} catch (AddressNotCreatedException e) {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity editAddress(@PathVariable(value="id") Long addressId, @RequestBody Address address) {
		try {
			return ResponseEntity.ok(addressService.updateAddress(addressId, address));
		} catch (AddressNotUpdatedException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Address> deleteAddress(@PathVariable(value="id")Long addressId) {
		try {
			addressService.deleteAddress(addressId);
			return new ResponseEntity<Address>(HttpStatus.OK);
		} catch (AddressNotDeletedException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
