package com.buildit.tdd.workshop.component;

import com.buildit.tdd.workshop.domain.ShippingDetail;
import com.buildit.tdd.workshop.exception.ShippingNotAvailableException;
import com.buildit.tdd.workshop.exception.ShippingServiceNotAvailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ShippingDetailRestClient {

	@Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private ObjectMapper objectMapper;

    public ShippingDetail getShippingDetail(int pin) throws ShippingNotAvailableException, ShippingServiceNotAvailableException {
        ShippingDetail shippingDetail = null;
        String requestUrl = "https://shippingcost-api.herokuapp.com/pin/"+pin;
        try {
            ResponseEntity<String> response  = restTemplate.getForEntity(requestUrl, String.class);
        	if(response.getStatusCode().value() == 200) {
        		shippingDetail = objectMapper.readValue(response.getBody().toString(), ShippingDetail.class);
        	} else if(response.getStatusCode().value() == 404) {
        		throw new ShippingNotAvailableException();
        	}
		} catch (HttpClientErrorException e) {
    		throw new ShippingServiceNotAvailableException();
		} catch (Exception e) {
    		throw new ShippingServiceNotAvailableException();
		} 
        return shippingDetail;
    }

}
