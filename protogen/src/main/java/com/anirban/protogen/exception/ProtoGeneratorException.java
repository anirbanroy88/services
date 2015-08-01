package com.anirban.protogen.exception;

public class ProtoGeneratorException  extends Throwable{

	private static final long serialVersionUID = 6617549413481716246L;
	
	private ProtoGeneratorException(String message){
		super(message);
	}
	
	private ProtoGeneratorException(String message,Throwable t){
		super(message,t);
	}
	
	public static void throwMe(String message) throws ProtoGeneratorException{
		throw new ProtoGeneratorException(message);
	}
	
	public static  void throwMe(String message,Throwable t) throws ProtoGeneratorException{
		throw new ProtoGeneratorException(message,t);
	}
}
