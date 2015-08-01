package com.anirban.protogen.model;

import com.anirban.protogen.api.ProtoDataType;
import com.anirban.protogen.exception.DatatypeNotSupported;

public enum PrimitiveProtoTypes implements ProtoDataType {
	DOUBLE("double"), FLOAT("float"), INT32("int32"), INT64("int64"), UNIT32(
			"unit32"), UNIT64("unit64"), SINT32("sint32"), SINT64("sint64"), FIXED32(
			"fixed32"), FIXED64("fixed64"), SFIXED32("sfixed32"), SFIXED64(
			"sfixed64"), BOOL("bool"), STRING("string"), BYTES("bytes");

	private String dataType;

	private PrimitiveProtoTypes(String dataType) {
		this.dataType = dataType;
	}

	public String getName() {
		return this.dataType;
	}

	public boolean isPrimitive() {
		return true;
	}

	public boolean build() {
		return true;
	}

	public static PrimitiveProtoTypes getCoresspondingProtoType(
			Class<?> javaType) throws DatatypeNotSupported {
		if (javaType == double.class || javaType == Double.class)
			return PrimitiveProtoTypes.DOUBLE;
		if (javaType == float.class || javaType == Float.class)
			return PrimitiveProtoTypes.FLOAT;
		if (javaType == int.class || javaType == Integer.class)
			return PrimitiveProtoTypes.INT32;
		if (javaType == long.class || javaType == Long.class)
			return PrimitiveProtoTypes.INT64;
		if (javaType == boolean.class || javaType == Boolean.class)
			return PrimitiveProtoTypes.BOOL;
		if (javaType == String.class)
			return PrimitiveProtoTypes.STRING;

		DatatypeNotSupported.throwMe(javaType.getCanonicalName()
				+ " is not supported by the library");

		return null;

	}
}