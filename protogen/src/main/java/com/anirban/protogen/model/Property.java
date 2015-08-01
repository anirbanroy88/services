package com.anirban.protogen.model;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;

import com.anirban.protogen.annotations.ProtoElement;
import com.anirban.protogen.api.Printable;
import com.anirban.protogen.api.ProtoDataType;
import com.anirban.protogen.api.Writable;
import com.anirban.protogen.exception.CannotCreateConstruct;
import com.anirban.protogen.exception.DatatypeNotSupported;
import com.anirban.protogen.util.ReflectionUtil;

public class Property implements Printable, Writable {
	private static final Logger LOGGER = Logger.getLogger(Message.class);

	public enum PropertyType {
		REQUIRED("required"), OPTIONAL("optional"), REPEATED("repeated");

		private String propertyModifier;

		private PropertyType(String propertyModifier) {
			this.propertyModifier = propertyModifier;
		}

		public String getModiferName() {
			return this.propertyModifier;
		}
	}

	private String name;
	private Field field;
	private PropertyType modifier;
	private ProtoDataType type;
	private Proto proto;
	private boolean ignored;

	public Property(Field field, Proto proto) {
		this.field = field;
		this.proto = proto;
	}

	public boolean build() {
		try {
			this.ignored = ReflectionUtil.isIgnored(field);
			if (!ignored) {
				this.name = ReflectionUtil.getPropertyName(this.field);
				this.modifier = ReflectionUtil.protoModifier(field);
				this.type = getProtoType(field);
				return this.type.build();
			} else
				return true;
		} catch (DatatypeNotSupported e) {
			LOGGER.error("Datatype not supported while building property: "
					+ this.name + " as \n" + e.getMessage());
			return false;
		} catch (Exception e) {
			LOGGER.error("Error while building property: " + this.name
					+ " as \n" + e.getMessage());
			return false;
		}

	}

	public String getName() {
		return name;
	}

	public PropertyType getModifier() {
		return modifier;
	}

	public ProtoDataType getType() {
		return type;
	}

	private ProtoDataType getProtoType(Field field)
			throws DatatypeNotSupported, CannotCreateConstruct {
		if (field.isAnnotationPresent(ProtoElement.class)) {
			return field.getAnnotation(ProtoElement.class).protoType();
		}
		if (ReflectionUtil.isPrimitive(field)) {
			return PrimitiveProtoTypes.getCoresspondingProtoType(ReflectionUtil
					.getJavaType(field));
		} else {
			if (field.getType().isEnum()) {
				return com.anirban.protogen.model.Enum.constructEnum(
						field.getType(), this.proto);
			}
			return Message.constructMessage(field.getType(), this.proto);
		}
	}

	public void print(int value, int indentation) {
		if (!this.ignored) {
			System.out.println("\n");
			for (int i = 1; i <= indentation; i++) {
				System.out.print("\t");
			}
			System.out.print(this.modifier.getModiferName() + " "
					+ this.type.getName() + " " + this.getName() + " = "
					+ value + ";");
		}
	}

	public void write(StringBuffer writer, int value, int indentation) {
		if (!this.ignored) {
			String str = "";
			for (int i = 1; i <= indentation; i++) {
				str = str + "\t";
			}
			str = str + this.modifier.getModiferName() + " "
					+ this.type.getName() + " " + this.getName() + " = "
					+ value + ";";
			writer.append(str);
		}
	}

}
