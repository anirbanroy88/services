package com.anirban.protogen.model;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.anirban.protogen.api.Printable;
import com.anirban.protogen.api.ProtoDataType;
import com.anirban.protogen.api.Writable;
import com.anirban.protogen.exception.CannotCreateConstruct;
import com.anirban.protogen.util.ReflectionUtil;

public class Message implements ProtoDataType,Printable,Writable {

	private static final Logger LOGGER = Logger.getLogger(Message.class);
	public static final String DATATYPE = "message";
	private Set<Property> properties;
	private Set<Enum> nestedEnums;
	private Set<Message> nestedMessages;
	private String constructName;
	private boolean isBuilt;

	private Message(Class<?> className,Proto proto) throws CannotCreateConstruct {
		this.constructName = ReflectionUtil.getMessageName(className);
		this.properties = new HashSet<Property>();
		this.nestedEnums = new HashSet<Enum>();
		this.nestedMessages = new HashSet<Message>();
		this.isBuilt = false;
		for (Field field : className.getDeclaredFields()) {
			try {
				properties.add(new Property(field,proto));
			} catch (Exception e) {
				LOGGER.error("Cannot create message " + this.constructName
						+ " due to the reason \n" + e.getMessage());
				CannotCreateConstruct.throwMe("Cannot create property "
						+ this.constructName, e);
			}
		}
	}

	public static Message constructMessage(Class<?> className,Proto proto)
			throws CannotCreateConstruct {
		String constructName = className.getSimpleName();
		if (className.getName().contains("$")) {
			Message newMessage = new Message(className,proto);
			String[] splitClassName = className.getCanonicalName().split("\\.");
			String parentType = splitClassName[splitClassName.length -2];
			Message parent = proto.getExistingMessage(parentType);
			parent.addChildMessage(newMessage);
			return newMessage;
		} else {
			if (proto.isCreated(constructName)) {
				return proto.getExistingMessage(constructName);
			} else {
				Message newMessage = new Message(className,proto);
				proto.addMessage(constructName, newMessage);
				return newMessage;
			}
		}
	}

	
	public boolean build() throws CannotCreateConstruct {
		if(isBuilt)
			return true;
		for (Property property : properties) {
			if (property.build() != true) {
				LOGGER.error("Property " + property.getName()
						+ " could not be built.");
				return false;
			}
		}
		for (Enum enumType : nestedEnums) {
			if (enumType.build() != true) {
				LOGGER.error("Nested enumeration " + enumType.getName()
						+ " could not be built.");
				return false;
			}
		}
		for (Message message : nestedMessages) {
			if (message.build() != true) {
				LOGGER.error("Nested message " + message.getName()
						+ " could not be built.");
				return false;
			}
		}
		isBuilt = true;
		return true;
	}

	public String getName() {
		return this.constructName;
	}

	public boolean isPrimitive() {
		return false;
	}

	public void addChildMessage(Message child) {
		this.nestedMessages.add(child);
	}

	public void addChildEnum(Enum child) {
		this.nestedEnums.add(child);
	}

	public void write(StringBuffer writer, int value ,int indentation) {
		String prefix = "";
		int counter = 1;
		for(int i = 1;i <= indentation;i++){
			prefix = prefix + "\t";
		}
		writer.append(prefix+DATATYPE+" "+getName()+"{ \n");
		for(Message message:this.nestedMessages){
			message.write(writer, value, indentation+1);
			writer.append("\n");
		}
		for(Enum enumType:nestedEnums){
			enumType.write(writer, value, indentation+1);
			writer.append("\n");
		}
		for(Property property: properties){
			property.write(writer, counter, indentation+1);
			writer.append("\n");
			counter++;
		}
		writer.append(prefix+"}");
	}

	public void print(int value,int indentation) {
		String prefix = "";
		int counter = 1;
		for(int i = 1;i <= indentation;i++){
			prefix = prefix + "\t";
		}
		System.out.print(prefix+DATATYPE+" "+this.constructName+"{");
		for(Message message:this.nestedMessages){
			System.out.println();
			message.print(value, indentation+1);
		}
		for(Enum enumType:nestedEnums){
			System.out.println();
			enumType.print(value, indentation+1);
		}
		for(Property property: properties){
			property.print(counter, indentation+1);
			counter++;
		}
		System.out.println("\n"+prefix+"}");
	}

}
