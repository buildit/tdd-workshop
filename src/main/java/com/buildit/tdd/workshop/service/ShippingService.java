package com.buildit.tdd.workshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buildit.tdd.workshop.domain.ShippingDetail;
import com.buildit.tdd.workshop.exception.ShippingNotAvailableException;
import com.buildit.tdd.workshop.exception.ShippingServiceNotAvailableException;
import com.buildit.tdd.workshop.component.ShippingDetailRestClient;

@Service
public class ShippingService {
	
	@Autowired
	private ShippingDetailRestClient shippingDetailRestClient;
	
	public ShippingDetail getShippingCostByPincode(int pincode) throws ShippingNotAvailableException, ShippingServiceNotAvailableException {
		
		return shippingDetailRestClient.getShippingDetail(pincode);
	}

}
