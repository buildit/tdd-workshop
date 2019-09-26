package com.buildit.tdd.workshop.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.buildit.tdd.workshop.domain.Address;
import com.buildit.tdd.workshop.domain.Product;
import com.buildit.tdd.workshop.exception.AddressNotCreatedException;
import com.buildit.tdd.workshop.exception.AddressNotDeletedException;
import com.buildit.tdd.workshop.exception.AddressNotFoundException;
import com.buildit.tdd.workshop.exception.AddressNotUpdatedException;
import com.buildit.tdd.workshop.service.AddressService;

@RunWith(MockitoJUnitRunner.class)
public class AddressControllerTest {
	
	@InjectMocks
	private AddressController addressController;
	
	@Mock
	private AddressService addressService;
	
	
	private Address address;
	private List<Address> addressList = new ArrayList<>();
	
	@Before
	public void setup() {
		address = address.builder().addressLine("71 wipro limited")
				.city("Bengaluru")
				.state("Karnataka")
				.pincode(560100)
				.build();
		
		addressList.add(address);
		
		
	}
	
	@Test
	public void getAddress_shouldReturnAddress_whenAddressIdExists() throws AddressNotFoundException {
		when(addressService.getAddress(1L)).thenReturn(address);
		
		ResponseEntity<Address> response = addressController.getAddress(1L);
		
		assertEquals(200, response.getStatusCode().value());
		assertEquals("Bengaluru", response.getBody().getCity());
		assertEquals("Karnataka", response.getBody().getState());
		assertEquals("71 wipro limited", response.getBody().getAddressLine());
		assertEquals(560100, response.getBody().getPincode());
		
	}
	
	@Test
	public void getAddress_shouldThrowException_whenAddressIdDoesNotExist() throws AddressNotFoundException {
		when(addressService.getAddress(1L)).thenThrow(AddressNotFoundException.class);
		
		ResponseEntity<Address> response = addressController.getAddress(1L);
		
		assertEquals(404, response.getStatusCode().value());

		
	}
	
	
	@Test
	public void getAddresses_shouldReturnAddresses_whenCustomerIdExists() throws AddressNotFoundException {
		when(addressService.getAddresses(1L)).thenReturn(addressList);
		
		ResponseEntity<List<Address>> response = addressController.getAddresses(1L);
		
		assertEquals(200, response.getStatusCode().value());
		assertEquals(addressList.size(), response.getBody().size()); 

		
	}
	
	@Test
	public void getAddresses_shouldThrowException_whenCustomerIdDoesNotExist() throws AddressNotFoundException {
		when(addressService.getAddresses(1L)).thenThrow(AddressNotFoundException.class);
		
		ResponseEntity<Address> response = addressController.getAddresses(1L);
		
		assertEquals(404, response.getStatusCode().value());
		
	}
	
	@Test
	public void createAddress_shouldReturnAddress_whenAddressisSaved() throws AddressNotCreatedException {
		Address addressResponse = address;
		addressResponse.setAddressId(1L);
		
		when(addressService.createAddress(address)).thenReturn(addressResponse);
		
		ResponseEntity<Address> response  = addressController.createAddress(address);
		
		assertEquals(200, response.getStatusCode().value());
		assertEquals(addressResponse.getAddressId(), response.getBody().getAddressId());
	}
	
	@Test
	public void createAddress_shouldThrowException_whenAddressisNotSaved() throws AddressNotCreatedException {
		Address addressResponse = address;
		addressResponse.setAddressId(1L);
		
		when(addressService.createAddress(address)).thenThrow(AddressNotCreatedException.class);
		
		ResponseEntity<Address> response  = addressController.createAddress(address);
		
		assertEquals(404, response.getStatusCode().value());
	}
	
	@Test
	public void editAddress_shouldReturnAddress_whenAddressisUpdated() throws AddressNotUpdatedException {
		Address addressResponse = address;
		addressResponse.setAddressId(1L);
		when(addressService.updateAddress(1L,address)).thenReturn(addressResponse);
		
		ResponseEntity<Address> response  = addressController.editAddress(1L, address);
		
		assertEquals(200, response.getStatusCode().value());
		assertEquals(addressResponse.getAddressId(), response.getBody().getAddressId());

	}
	
	@Test
	public void editAddress_shouldThrowException_whenAddressisNotUpdated() throws AddressNotUpdatedException {
		Address addressResponse = address;
		addressResponse.setAddressId(1L);
		when(addressService.updateAddress(1L,address)).thenThrow(AddressNotUpdatedException.class);
		
		ResponseEntity<Address> response  = addressController.editAddress(1L, address);
		
		assertEquals(404, response.getStatusCode().value());

	}
	
	@Test
	public void deleteAddress_shouldReturnSuccessStatus_whenAddressisDeleted() throws AddressNotDeletedException {
		
//		when(addressService.deleteAddress(1L)).thenRe(AddressNotUpdatedException.class);
		
		ResponseEntity<Address> response  = addressController.deleteAddress(1L);
		
		assertEquals(200, response.getStatusCode().value());

	}

}
