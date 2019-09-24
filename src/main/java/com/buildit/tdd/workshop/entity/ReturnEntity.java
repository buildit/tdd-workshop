package com.buildit.tdd.workshop.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturnEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long returnId;
	@Column(unique = true)
	private long orderId;
	private Date returnDate;

}
