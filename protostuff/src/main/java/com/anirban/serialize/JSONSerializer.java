package com.anirban.serialize;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.protostuff.JsonIOUtil;
import io.protostuff.LinkedBuffer;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

public class JSONSerializer<Type> {

	private Class<Type> type;
	private Schema<Type> schema;
	private boolean retainPropertyNames;
	
	public JSONSerializer(Class<Type> type,boolean retainPropertyNames) {
		this.type = type;
		this.retainPropertyNames = retainPropertyNames;
		this.schema = RuntimeSchema.getSchema(this.type);;
	}
	
	public OutputStream serialize(Type objectToBeSerialized) throws IOException {
		LinkedBuffer buffer = LinkedBuffer.allocate(8192);
		byte[] byteArray = JsonIOUtil.toByteArray(objectToBeSerialized,
				schema,this.retainPropertyNames, buffer);
		buffer.clear();
		ByteArrayOutputStream stream = new ByteArrayOutputStream(byteArray.length);
		stream.write(byteArray);

		return stream;
	}
	
	public Type deserialize(InputStream serializedObject) throws IOException{
		LinkedBuffer buffer = LinkedBuffer.allocate(8192);
		Type deserializedObject = schema.newMessage();
		JsonIOUtil.mergeFrom(serializedObject,deserializedObject,schema,this.retainPropertyNames,buffer);
		return deserializedObject;
		
	}
}
