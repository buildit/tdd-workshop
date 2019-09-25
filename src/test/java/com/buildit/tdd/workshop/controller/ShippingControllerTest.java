package com.buildit.tdd.workshop.controller;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.buildit.tdd.workshop.domain.ShippingAvailability;
import com.buildit.tdd.workshop.domain.ShippingDetail;
import com.buildit.tdd.workshop.exception.ShippingNotAvailableException;
import com.buildit.tdd.workshop.exception.ShippingServiceNotAvailableException;
import com.buildit.tdd.workshop.service.ShippingService;

@RunWith(MockitoJUnitRunner.class)
public class ShippingControllerTest {

	@InjectMocks
	private ShippingController shippingController;
	
	@Mock
	private ShippingService shippingService;
	
	private ShippingDetail shippingDetail;
	
	private final int SERVICEABLE_PINCODE = 700024;
	private final int NON_SERVICEABLE_PINCODE = 700003;
	
	@Before
	public void setup() throws ShippingNotAvailableException, ShippingServiceNotAvailableException {
		
		shippingDetail = ShippingDetail.builder().cost(40).pin(SERVICEABLE_PINCODE).build();
		when(shippingService.getShippingCostByPincode(SERVICEABLE_PINCODE)).thenReturn(shippingDetail);
	}
	
	@Test
	public void getShippingAvailability_shouldReturnShippingAvailable_forServiceablePincode() throws ShippingNotAvailableException, ShippingServiceNotAvailableException { 
		
		ResponseEntity<ShippingAvailability> response = shippingController.getShippingAvailability(123456);
		
		assertEquals(true, response.getBody().isAvailable());
		
	}
	
	// TODO: test for exception throwing conditions for getShippingAvailability()

	@Test
	public void getShippingAvailability_shouldReturnNotFound_forShippingNotAvailableException() throws ShippingServiceNotAvailableException, ShippingNotAvailableException {
		when(shippingService.getShippingCostByPincode(anyInt())).thenThrow(ShippingNotAvailableException.class);

		ResponseEntity responseEntity = shippingController.getShippingAvailability(123456);
		assertEquals(responseEntity.getStatusCode().value(), 404);
	}

	@Test
	public void getShippingAvailability_shouldReturnNotFound_forShippingServiceNotAvailableException() throws ShippingServiceNotAvailableException, ShippingNotAvailableException {
		when(shippingService.getShippingCostByPincode(anyInt())).thenThrow(ShippingServiceNotAvailableException.class);

		ResponseEntity responseEntity = shippingController.getShippingAvailability(123456);
		assertEquals(responseEntity.getStatusCode().value(), 404);
	}
	
	@Test
	public void getShippingCost_shouldReturnShippingCost_forServiceablePincode() throws ShippingNotAvailableException, ShippingServiceNotAvailableException {
		
		ResponseEntity<ShippingDetail> response = shippingController.getShippingCost(SERVICEABLE_PINCODE);
		
		assertEquals(shippingDetail.getCost(), response.getBody().getCost());
		
	}
	
}
