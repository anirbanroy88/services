package com.anirban.protogen.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import com.anirban.protogen.annotations.Ignore;
import com.anirban.protogen.annotations.ProtoElement;
import com.anirban.protogen.annotations.ProtoEnum;
import com.anirban.protogen.annotations.ProtoMessage;
import com.anirban.protogen.exception.DatatypeNotSupported;
import com.anirban.protogen.model.Property.PropertyType;

public class ReflectionUtil {

	private static List<Class<?>> wrapperTypes = Arrays.asList(new Class<?>[] {
			Character.class, Boolean.class, Double.class, Integer.class,
			Float.class, Short.class, Byte.class, Short.class });
	private static final Logger LOGGER = Logger.getLogger(ReflectionUtil.class);

	public static Class<?> getJavaType(Field field) throws DatatypeNotSupported {
		if (field.getType().isPrimitive()
				|| wrapperTypes.contains(field.getType())) {
			return field.getType();
		}
		if (field.getType() == String.class) {
			return String.class;
		}
		if (field.getType().isArray()) {
			return field.getType().getComponentType();
		}
		if (Collection.class.isAssignableFrom(field.getType())) {
			Type type = field.getGenericType();
			if (type instanceof ParameterizedType) {
				ParameterizedType pt = (ParameterizedType) type;
				for (Type t : pt.getActualTypeArguments()) {
					try {
						return Class.forName(t.toString().split(" ")[1]);
					} catch (ClassNotFoundException e) {
						LOGGER.error("Error while creating java type for the collection field "
								+ field.getName()
								+ " as the parameterised type "
								+ t.toString()
								+ " is not in classpath");
						DatatypeNotSupported.throwMe("Collection named "
								+ field.getName() + " made of " + t.toString()
								+ " cannot be created as the type "
								+ t.toString() + " is not in classpath");
					}
				}
			}

		}

		DatatypeNotSupported.throwMe(field.getType().getCanonicalName()
				+ " for the field " + field.getName()
				+ " is not supported by the library");
		return null;

	}

	public static boolean isFieldPrimitive(Class<?> field) {
		if (field.isPrimitive()) {
			return true;
		} else if (field == String.class) {
			return true;
		} else if (wrapperTypes.contains(field)
				|| Number.class.isAssignableFrom(field)) {
			return true;
		} else if (field.isArray()) {
			return isFieldPrimitive(field.getComponentType());
		}

		return false;
	}

	public static boolean isPrimitive(Field field) throws DatatypeNotSupported {
		if (Collection.class.isAssignableFrom(field.getType())) {
			return isCollectionPrimitive(field);
		} else {
			return isFieldPrimitive(field.getType());
		}
	}

	private static boolean isCollectionPrimitive(Field field)
			throws DatatypeNotSupported {
		Type type = field.getGenericType();
		if (type instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) type;
			for (Type t : pt.getActualTypeArguments()) {
				try {
					return isFieldPrimitive(Class.forName(t.toString().split(
							" ")[1]));
				} catch (ClassNotFoundException e) {
					DatatypeNotSupported.throwMe("Collection named "
							+ field.getName() + " made of " + t.toString()
							+ " cannot be created as the type " + t.toString()
							+ " is not in classpath");
				}

			}
		}
		return false;
	}

	public static PropertyType protoModifier(Field field) {
		if(field.isAnnotationPresent(ProtoElement.class)){
			return field.getAnnotation(ProtoElement.class).modifier();
		}
		if (field.getType().isArray()
				|| Collection.class.isAssignableFrom(field.getType())) {
			return PropertyType.REPEATED;
		} else {
			return PropertyType.REQUIRED;
		}
	}

	public static String getMessageName(Class<?> message) {
		if (message.isAnnotationPresent(ProtoMessage.class)) {
			ProtoMessage annotation = message.getAnnotation(ProtoMessage.class);
			return annotation.messageName();
		} else {
			return message.getSimpleName();
		}
	}
	
	public static String getEnumName(Class<?> enumType) {
		if (enumType.isAnnotationPresent(ProtoEnum.class)) {
			ProtoEnum annotation = enumType.getAnnotation(ProtoEnum.class);
			return annotation.protoName();
		} else {
			return enumType.getSimpleName();
		}
	}

	public static String getPropertyName(Field property) {
		if (property.isAnnotationPresent(ProtoElement.class)) {
			ProtoElement annotation = property
					.getAnnotation(ProtoElement.class);
			return annotation.protoName();
		} else {
			return property.getName();
		}
	}

	public static boolean isIgnored(Field property){
		if(property.isAnnotationPresent(Ignore.class))
			return true;
		return false;
	}

}
