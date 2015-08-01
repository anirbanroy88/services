package com.anirban.protogen.exception;

public class DatatypeNotSupported extends Throwable {

private static final long serialVersionUID = 6617549413481716246L;
	
	private DatatypeNotSupported(String message){
		super(message);
	}
	
	private DatatypeNotSupported(String message,Throwable t){
		super(message,t);
	}
	
	public static void throwMe(String message) throws DatatypeNotSupported{
		throw new DatatypeNotSupported(message);
	}
	
	public static void throwMe(String message,Throwable t) throws DatatypeNotSupported{
		throw new DatatypeNotSupported(message,t);
	}
	
	
}
