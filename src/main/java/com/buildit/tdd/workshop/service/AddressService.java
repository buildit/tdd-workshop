package com.buildit.tdd.workshop.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buildit.tdd.workshop.domain.Address;
import com.buildit.tdd.workshop.domain.Cart;
import com.buildit.tdd.workshop.domain.Product;
import com.buildit.tdd.workshop.entity.AddressEntity;
import com.buildit.tdd.workshop.entity.CartEntity;
import com.buildit.tdd.workshop.entity.CartItemEntity;
import com.buildit.tdd.workshop.entity.ProductEntity;
import com.buildit.tdd.workshop.exception.AddressNotCreatedException;
import com.buildit.tdd.workshop.exception.AddressNotDeletedException;
import com.buildit.tdd.workshop.exception.AddressNotFoundException;
import com.buildit.tdd.workshop.exception.AddressNotUpdatedException;
import com.buildit.tdd.workshop.repository.AddressRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AddressService {
	
	
	
	@Autowired
	private AddressRepository addressRepository;
	
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	public List<Address> getAddresses(Long customerId) throws AddressNotFoundException {
		List<AddressEntity> addressEntityList = addressRepository.findAddressesByCustomerId(customerId);
		return addressEntityList.stream().map(addressEntity -> objectMapper.convertValue(addressEntity, Address.class))
				.collect(Collectors.toList());

	}
	
	
	public Address createAddress(Address address) throws AddressNotCreatedException {
		AddressEntity addressEntity = objectMapper.convertValue(address, AddressEntity.class);
		try {
			AddressEntity responseAddressEntity = addressRepository.save(addressEntity);
			Address response = objectMapper.convertValue(responseAddressEntity, Address.class);
			return response;
		} catch (RuntimeException e) {
			throw new AddressNotCreatedException();
		}

	}
	
	
	public Address getAddress(Long addressId) throws AddressNotFoundException {
		Optional<AddressEntity> response = addressRepository.findById(addressId);
		if(!response.isPresent()) {
			throw new AddressNotFoundException();
		}
		return objectMapper.convertValue(response.get(), Address.class);

	}
	
	public void deleteAddress(Long addressId) throws AddressNotDeletedException {
		addressRepository.deleteById(addressId);

	}
	
	public Address updateAddress(Long addressId, Address address) throws AddressNotUpdatedException {
		Optional<AddressEntity> addr = addressRepository.findById(addressId);
		AddressEntity addressEntity = addr.get();
		addressEntity.setAddressLine(address.getAddressLine());
		addressEntity.setCity(address.getCity());
		addressEntity.setState(address.getState());
		addressEntity.setPincode(address.getPincode());
		addressEntity.setAddressLine(address.getAddressLine());
		try {
			addressRepository.save(addressEntity);
			Optional<AddressEntity> response = addressRepository.findById(addressId);
			return objectMapper.convertValue(response.get(), Address.class);
		} catch (RuntimeException e) {
			throw new AddressNotUpdatedException();
		}
	}

}
