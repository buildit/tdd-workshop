package com.buildit.tdd.workshop.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "address")
public class AddressEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long addressId;
	private long customerId;
	private String addressLine;
	private String city;
	private String state;
	private int pincode;

}



