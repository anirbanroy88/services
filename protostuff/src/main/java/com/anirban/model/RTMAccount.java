package com.anirban.model;

import io.protostuff.Tag;

public class RTMAccount {
	
	public enum AccountSource{
		RTMNY,
		RTMLN,
		RTMHK;
	}
	
	@Tag(1)
	private String accountNumber;
	@Tag(2)
	private String busGroup;
	@Tag(3)
	private AccountSource source;
	
	public RTMAccount(String accountNumber, String busGroup,
			AccountSource source) {
		this.accountNumber = accountNumber;
		this.busGroup = busGroup;
		this.source = source;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public String getBusGroup() {
		return busGroup;
	}
	public AccountSource getSource() {
		return source;
	}
	
}
