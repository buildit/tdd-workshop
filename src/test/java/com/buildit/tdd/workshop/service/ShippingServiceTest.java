package com.buildit.tdd.workshop.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.buildit.tdd.workshop.component.ShippingDetailRestClient;
import com.buildit.tdd.workshop.domain.ShippingDetail;
import com.buildit.tdd.workshop.exception.ShippingNotAvailableException;
import com.buildit.tdd.workshop.exception.ShippingServiceNotAvailableException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RunWith(MockitoJUnitRunner.class)
public class ShippingServiceTest {

	@InjectMocks
	private ShippingService shippingService;

	private ShippingDetail shippingDetail;

	private final int SERVICEABLE_PINCODE = 700024;
	private final int NON_SERVICEABLE_PINCODE = 700003;

	@Mock
	private ShippingDetailRestClient shippingDetailRestClient;

	@Test
	public void getShippingCostByPincode_shouldReturnCost_forServiceablePincode()
			throws ShippingNotAvailableException, ShippingServiceNotAvailableException {

		shippingDetail = ShippingDetail.builder().pin(SERVICEABLE_PINCODE).cost(40).build();
		when(shippingDetailRestClient.getShippingDetail(SERVICEABLE_PINCODE)).thenReturn(shippingDetail);

		ShippingDetail response = shippingService.getShippingCostByPincode(SERVICEABLE_PINCODE);
		assertEquals(shippingDetail.getCost(), response.getCost());
	}

	@Test(expected = ShippingServiceNotAvailableException.class)
	public void getShippingCostByPincode_shouldThrowShippingServiceNotAvailableException_forNonServiceablePincode()
			throws ShippingNotAvailableException, ShippingServiceNotAvailableException {

		when(shippingDetailRestClient.getShippingDetail(NON_SERVICEABLE_PINCODE))
				.thenThrow(ShippingServiceNotAvailableException.class);

		shippingService.getShippingCostByPincode(NON_SERVICEABLE_PINCODE);
	}

}