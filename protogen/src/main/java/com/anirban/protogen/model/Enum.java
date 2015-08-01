package com.anirban.protogen.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.anirban.protogen.api.Printable;
import com.anirban.protogen.api.ProtoDataType;
import com.anirban.protogen.api.Writable;
import com.anirban.protogen.exception.CannotCreateConstruct;
import com.anirban.protogen.util.ReflectionUtil;

public class Enum implements ProtoDataType,Printable,Writable {
	
	private static final Logger LOGGER = Logger.getLogger(Enum.class);
	public static final String DATATYPE = "enum";
	
	private List<String> constants;
	private String constructName;
	private boolean isBuilt;
	
	private Enum(Class<?> enumName) throws CannotCreateConstruct{
		constants = new ArrayList<String>();
		constructName = ReflectionUtil.getEnumName(enumName);
		isBuilt = false;
		for(Object enumConstant:enumName.getEnumConstants()){
			try{
			java.lang.Enum<?> e = (java.lang.Enum<?>) enumConstant;
			constants.add(e.name());
			}catch(Exception e){
				LOGGER.error("Cannot construct enumeration "+this.constructName+" as \n"+e.getMessage());
				CannotCreateConstruct.throwMe("Cannot construct enumeration "+this.constructName, e);
			}
		}
	}
	
	public static Enum constructEnum(Class<?> enumName,Proto proto) throws CannotCreateConstruct{
		String constructName = enumName.getSimpleName();
		if (enumName.getName().contains("$")) {
			Enum newEnum = new Enum(enumName);
			String[] splitClassName = enumName.getCanonicalName().split("\\.");
			String parentType = splitClassName[splitClassName.length -2];
			Message parent = proto.getExistingMessage(parentType);
			parent.addChildEnum(newEnum);
			return newEnum;
		}else{
			if(proto.isCreated(constructName)){
				return proto.getExistingEnum(constructName);
			}else{
				Enum newEnum = new Enum(enumName);
				proto.addEnum(constructName, newEnum);
				return newEnum;
			}

		}
	}
	public String getName() {
		return this.constructName;
	}

	public boolean isPrimitive() {
		return false;
	}

	public boolean build() {
		if(isBuilt){
			return true;
		}
		if(this.constants != null && this.constants.size() > 0){
			isBuilt = true;
			return true;
		}
		return false;
	}

	public void write(StringBuffer writer, int value,int indentation) {
		int counter = 1;
		String prefix = "";
		for(int i = 1;i <= indentation;i++){
			prefix = prefix + "\t";
		}
		writer.append(prefix+DATATYPE+" "+this.constructName+" {"+"\n");
		for(String constant:constants){
			writer.append(prefix+"\t"+constant+" = "+counter+";\n");
			counter++;
		}
		writer.append(prefix+"}"+"\n");
		
	}

	public void print(int value,int indentation) {
		int counter = 1;
		String prefix = "";
		for(int i = 1;i <= indentation;i++){
			prefix = prefix + "\t";
		}
		System.out.print(prefix+DATATYPE+" "+this.constructName+" {"+"\n");
		for(String constant:constants){
			System.out.println(prefix+"\t"+constant+" = "+counter+";");
			counter++;
		}
		System.out.println("\n"+prefix+"}");
	}

}
