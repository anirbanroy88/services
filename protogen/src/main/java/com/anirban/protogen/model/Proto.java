package com.anirban.protogen.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.anirban.protogen.api.Printable;
import com.anirban.protogen.api.Writable;
import com.anirban.protogen.exception.CannotCreateConstruct;

public class Proto implements Printable,Writable {
	
	private static final Logger LOGGER = Logger.getLogger(Proto.class);
	private Map<String,Message> messages;
	
	private Map<String,Enum> enums;
	
	private Map<String,Boolean> nonPrimitiveDataTypes;
	
	
	private Proto(){
		messages = new HashMap<String, Message>();
		enums = new HashMap<String, Enum>();
		nonPrimitiveDataTypes = new HashMap<String, Boolean>();
	}
	
	
	public static Proto build(Class<?> rootClass) throws CannotCreateConstruct{
		Proto proto = new Proto();
		boolean isBuilt = true;
		Message.constructMessage(rootClass,proto);
		for(Message message:proto.messages.values()){
			if(!message.build()){
				LOGGER.error("Cannot create message from class "+rootClass.getSimpleName());
				isBuilt =  false;
			}
		}
		for(Enum enumTypes:proto.enums.values()){
			if(!enumTypes.build()){
				LOGGER.error("Cannot create enumeration from type "+rootClass.getSimpleName());
				isBuilt =  false;
			}
		}
		if(isBuilt)
			return proto;
		else{
			return null;
		}
	}
	
	
	public boolean isCreated(String dataType){
		return nonPrimitiveDataTypes.containsKey(dataType);
	}
	
	public void addMessage(String protoTypeName,Message message){
		messages.put(protoTypeName,message);
		nonPrimitiveDataTypes.put(protoTypeName, true);
	}
	
	public void addEnum(String protoTypeName,Enum enumType){
		enums.put(protoTypeName,enumType);
		nonPrimitiveDataTypes.put(protoTypeName, true);
	}
	
	public Message getExistingMessage(String constructName){
		return messages.get(constructName);
	}
	
	public Enum getExistingEnum(String constructName){
		return enums.get(constructName);
	}


	public void write(StringBuffer writer, int value, int indentation) {
		for(Message message:messages.values()){
			message.write(writer, value, indentation);
			writer.append("\n");
		}
		for(Enum enumType:enums.values()){
			enumType.write(writer, value, indentation);
			writer.append("\n");
		}
	}


	public void print(int value, int indentation) {
		for(Message message:messages.values()){
			message.print(value, indentation);
		}
		for(Enum enumType:enums.values()){
			enumType.print(value, indentation);
		}
		
	}
	

}
