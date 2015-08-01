package com.anirban.protogen.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.anirban.protogen.model.PrimitiveProtoTypes;
import com.anirban.protogen.model.Property;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ProtoElement {
	
	String protoName();
	Property.PropertyType modifier();
	PrimitiveProtoTypes protoType();

}
