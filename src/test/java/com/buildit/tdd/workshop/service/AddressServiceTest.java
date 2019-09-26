package com.buildit.tdd.workshop.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.buildit.tdd.workshop.domain.Address;
import com.buildit.tdd.workshop.domain.Product;
import com.buildit.tdd.workshop.entity.AddressEntity;
import com.buildit.tdd.workshop.entity.ProductEntity;
import com.buildit.tdd.workshop.exception.AddressNotCreatedException;
import com.buildit.tdd.workshop.exception.AddressNotDeletedException;
import com.buildit.tdd.workshop.exception.AddressNotFoundException;
import com.buildit.tdd.workshop.exception.AddressNotUpdatedException;
import com.buildit.tdd.workshop.repository.AddressRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class AddressServiceTest {
	
	@InjectMocks
	private AddressService addressService;
	
	@Mock
	private AddressRepository addressRepository;
	
	@Mock
	private ObjectMapper objectMapper;
	
	private List<AddressEntity> addressEntityList = new ArrayList<>();
	private List<Address> addressList = new ArrayList<>();
	private AddressEntity addressEntity;
	private Address address;
	
	
	
	@Before
	public void setup() {
		addressEntity = addressEntity.builder().addressLine("72 wipro limited").city("bengaluru").pincode(560100).build();
		address = address.builder().addressLine("72 wipro limited").city("bengaluru").pincode(560100).build();

		addressEntityList.add(addressEntity);
		addressList.add(address);
	}
	
	@Test
	public void getAddress_shouldReturnAddress_whenAddressIdExists() throws AddressNotFoundException {
		addressEntity.setAddressId(1L);
		address.setAddressId(1L);
		Optional addressEntityOptional = Optional.ofNullable(addressEntity);
		
		when(addressRepository.findById(1L)).thenReturn(addressEntityOptional);
		when(objectMapper.convertValue(addressEntityOptional.get(), Address.class)).thenReturn(address);
		
		Address response = addressService.getAddress(1L);
		
		assertEquals(1L, response.getAddressId());
		assertEquals("72 wipro limited", response.getAddressLine());
	}
	
	@Test(expected = AddressNotFoundException.class)
	public void getAddress_shouldThrowException_whenAddressIdDoesNotExists() throws AddressNotFoundException {
		when(addressRepository.findById(1L)).thenReturn(Optional.empty());
		addressService.getAddress(1L);
	}
	
	
	@Test
	public void createAddress_shouldCreateAddress_whendataisPassed() throws AddressNotCreatedException{
		AddressEntity addressEntityResponse = addressEntity;
		addressEntityResponse.setAddressId(1L);

		Address addressResponse = address;
		addressResponse.setAddressId(1L);

		when(objectMapper.convertValue(address, AddressEntity.class)).thenReturn(addressEntity);
		when(addressRepository.save(addressEntity)).thenReturn(addressEntityResponse);
		when(objectMapper.convertValue(addressEntityResponse, Address.class)).thenReturn(addressResponse);

		Address response = addressService.createAddress(address);

		assertEquals("72 wipro limited", response.getAddressLine());
		assertEquals(1L, response.getAddressId());
	}
	
	@Test(expected = AddressNotCreatedException.class)
	public void createAddress_shouldThrowException_whenAddressCreationFails() throws AddressNotCreatedException{
		when(objectMapper.convertValue(address, AddressEntity.class)).thenReturn(addressEntity);
		when(addressRepository.save(addressEntity)).thenThrow(RuntimeException.class);
		
		addressService.createAddress(address);
	}
	
	@Test
	public void getAddresses_shouldReturnAddressForCustomer_whenCustomerIdHadAddresses() throws AddressNotFoundException {
		when(addressRepository.findAddressesByCustomerId(1L)).thenReturn(addressEntityList);
		when(objectMapper.convertValue(addressEntity, Address.class)).thenReturn(address);
		List<Address> response = addressService.getAddresses(1L);
		assertEquals(addressList.size(), response.size());
	}
	
	@Test
	public void deleteAddress_shouldCallRepoToDeleteAddress_whenAddressIdisShared() throws AddressNotDeletedException {
		addressService.deleteAddress(1L);
		verify(addressRepository, times(1)).deleteById(1L);
		
	}

}
