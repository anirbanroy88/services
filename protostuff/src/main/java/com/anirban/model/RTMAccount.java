package com.anirban.model;

import io.protostuff.Tag;

public class RTMAccount {
	
	public enum AccountSource{
		RTMNY("RTMNY"),
		RTMLN("RTMLN"),
		RTMHK("RTMHK");
		private String accountSource;
		
		private AccountSource(String source){
			this.accountSource = source;
		}
		
		public String getAccountSource(){
			return this.accountSource;
		}
	}
	
	@Tag(value = 1,alias="Account Number")
	private String accountNumber;
	@Tag(value = 2,alias="Business Group")
	private String busGroup;
	@Tag(value = 3,alias = "Account Region")
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
