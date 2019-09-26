package com.buildit.tdd.workshop.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {
	
	private long addressId;
	private long customerId;
	private String addressLine;
	private String city;
	private String state;
	private int pincode;

}
