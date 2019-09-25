package com.buildit.tdd.workshop.domain;

import java.sql.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long orderId;
	private long customerId;
	private Date orderDate;
	private long cartId;
	private double totalAmount;
}
