package com.anirban.protogen.api;

public interface ProtoDataType {
	
	String getName();
	
	boolean isPrimitive();
	
	boolean build() throws Exception;
}
