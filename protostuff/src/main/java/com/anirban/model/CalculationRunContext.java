package com.anirban.model;

import io.protostuff.Tag;

public class CalculationRunContext {
	
	@Tag(1)
	private String collerationToken;
	@Tag(2)
	private RTMAccount account;
	@Tag(3)
	private Long policyNumber;
	
	public CalculationRunContext(String collerationToken, RTMAccount account,
			Long policyNumber) {
		this.collerationToken = collerationToken;
		this.account = account;
		this.policyNumber = policyNumber;
	}
	public String getCollerationToken() {
		return collerationToken;
	}
	public RTMAccount getAccount() {
		return account;
	}
	public Long getPolicyNumber() {
		return policyNumber;
	}

}
