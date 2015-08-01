package com.anirban.protogen.exception;

public class CannotCreateConstruct extends Exception{

	private static final long serialVersionUID = 6617549413481716246L;
	
	private CannotCreateConstruct(String message){
		super(message);
	}
	
	private CannotCreateConstruct(String message,Throwable t){
		super(message,t);
	}
	
	public static void throwMe(String message) throws CannotCreateConstruct{
		throw new CannotCreateConstruct(message);
	}
	
	public static  void throwMe(String message,Throwable t) throws CannotCreateConstruct{
		throw new CannotCreateConstruct(message,t);
	}

}
