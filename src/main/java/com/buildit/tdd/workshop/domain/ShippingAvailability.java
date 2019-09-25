package com.buildit.tdd.workshop.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShippingAvailability {

	private boolean available;
}
