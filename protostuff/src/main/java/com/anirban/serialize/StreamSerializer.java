package com.anirban.serialize;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

public class StreamSerializer<Type> {

	private Class<Type> type;

	public StreamSerializer(Class<Type> type) {
		this.type = type;
	}

	public OutputStream serialize(Type objectToBeSerialized) throws IOException {
		Schema<Type> schema = RuntimeSchema.getSchema(type);
		LinkedBuffer buffer = LinkedBuffer.allocate(8192);
		byte[] byteArray = ProtostuffIOUtil.toByteArray(objectToBeSerialized,
				schema, buffer);
		buffer.clear();
		ByteArrayOutputStream stream = new ByteArrayOutputStream(byteArray.length);
		stream.write(byteArray);

		return stream;
	}

}
