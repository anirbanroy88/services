package com.anirban.serialize;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

public class StreamSerializer<Type> {

	private Class<Type> type;
	private Schema<Type> schema;
	
	public StreamSerializer(Class<Type> type) {
		this.type = type;
		this.schema = RuntimeSchema.getSchema(this.type);;
	}

	public OutputStream serialize(Type objectToBeSerialized) throws IOException {
		LinkedBuffer buffer = LinkedBuffer.allocate(8192);
		byte[] byteArray = ProtostuffIOUtil.toByteArray(objectToBeSerialized,
				schema, buffer);
		buffer.clear();
		ByteArrayOutputStream stream = new ByteArrayOutputStream(byteArray.length);
		stream.write(byteArray);

		return stream;
	}
	
	public Type deserialize(InputStream serializedObject) throws IOException{
		LinkedBuffer buffer = LinkedBuffer.allocate(8192);
		Type deserializedObject = schema.newMessage();
		ProtostuffIOUtil.mergeFrom(serializedObject,deserializedObject,schema,buffer);
		return deserializedObject;
		
	}

}
