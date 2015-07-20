package com.anirban.serialize;

import java.io.IOException;
import java.io.InputStream;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

public class Deserializer<Type> {
	
	private Class<Type> type;

	public Deserializer(Class<Type> type) {
		this.type = type;
	}
	
	public Type deserialize(InputStream serializedObject) throws IOException{
		Schema<Type> schema = RuntimeSchema.getSchema(type);
		LinkedBuffer buffer = LinkedBuffer.allocate(8192);
		Type deserializedObject = schema.newMessage();
		ProtostuffIOUtil.mergeFrom(serializedObject,deserializedObject,schema,buffer);
		return deserializedObject;
		
	}
	
	

}
