package com.buildit.tdd.workshop.component;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.buildit.tdd.workshop.domain.ShippingDetail;
import com.buildit.tdd.workshop.exception.ShippingNotAvailableException;
import com.buildit.tdd.workshop.exception.ShippingServiceNotAvailableException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class ShippingDetailRestClientTest {

	@InjectMocks
	private ShippingDetailRestClient shippingDetailRestClient;
	
	@Mock
    private RestTemplate restTemplate;
    
	@Mock
    private ObjectMapper objectMapper;
	
	@Mock
	private ResponseEntity<String> responseEntity;
	
    private String requestUrl = "https://shippingcost-api.herokuapp.com/pin/"+123456;
	
	@Test
	public void getShippingDetail_shouldReturnShippingDetail_forServiceablePinCode() throws ShippingNotAvailableException, ShippingServiceNotAvailableException, IOException {
		
		ShippingDetail shippingDetail = ShippingDetail.builder().cost(40).pin(123456).build();
		
		when(restTemplate.getForEntity(requestUrl, String.class)).thenReturn(responseEntity);
		when(responseEntity.getBody()).thenReturn("");
		when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
		when(objectMapper.readValue(responseEntity.getBody().toString(), ShippingDetail.class)).thenReturn(shippingDetail);
		
		ShippingDetail response = shippingDetailRestClient.getShippingDetail(123456);
		
		assertEquals(40, response.getCost());
		assertEquals(123456, response.getPin());
		
	}
	
	
	@Test(expected = ShippingServiceNotAvailableException.class)
	public void getShippingDetail_shouldReturnThrowShippingServiceNotAvailableException_forUnServiceablePinCode() throws ShippingNotAvailableException, ShippingServiceNotAvailableException, JsonParseException, JsonMappingException, IOException {
				
		when(restTemplate.getForEntity(requestUrl, String.class)).thenReturn(responseEntity);
		when(responseEntity.getStatusCode()).thenReturn(HttpStatus.NOT_FOUND);
		
		shippingDetailRestClient.getShippingDetail(123456);
		
	}
	
	
	@Test(expected = ShippingServiceNotAvailableException.class)
	public void getShippingDetail_shouldReturnShippingServiceNotAvailableException_whenServiceIsDown() throws ShippingNotAvailableException, ShippingServiceNotAvailableException, JsonParseException, JsonMappingException, IOException {
				
		when(restTemplate.getForEntity(requestUrl, String.class)).thenThrow(RuntimeException.class);
		
		shippingDetailRestClient.getShippingDetail(123456);
		
	}

}
