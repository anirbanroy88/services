package com.anirban.model;

import io.protostuff.Tag;

public class Portfolio {
	
	@Tag(1)
	private String cusip;
	@Tag(2)
	private double quantity;
	
	public Portfolio(String cusip, double quantity) {
		this.cusip = cusip;
		this.quantity = quantity;
	}

	public String getCusip() {
		return cusip;
	}

	public double getQuantity() {
		return quantity;
	}
	
	
	

}
