package com.anirban.protogen.api;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.anirban.protogen.exception.CannotCreateConstruct;
import com.anirban.protogen.exception.ProtoGeneratorException;
import com.anirban.protogen.model.Proto;

public class ProtoGenrator {

	private static final String PACKAGE = "package";
	
	public static void generate(Class<?> rootClass, File protoFile,String packageName)
			throws ProtoGeneratorException {
		StringBuffer protoString = new StringBuffer();
		Proto proto;
		try {
			StringBuffer protoStringWithoutPackage = new StringBuffer();
			proto = Proto.build(rootClass);
			proto.write(protoStringWithoutPackage, 0, 0);
			protoString.append(PACKAGE + " "+packageName+";\n");
			protoString.append(protoStringWithoutPackage.toString());
		} catch (CannotCreateConstruct e1) {
			ProtoGeneratorException.throwMe("Error while creating proto put of class files. \n"+e1.getMessage(), e1);
		}
		
		if (!protoFile.exists()) {
			try {
				if(!protoFile.createNewFile())
				ProtoGeneratorException
						.throwMe("The input proto file does not exist. And it could not be created");
			} catch (IOException e) {
				ProtoGeneratorException
				.throwMe("The input proto file does not exist and could not be created",e);
			}
		}
		try (BufferedWriter bwr = new BufferedWriter(new FileWriter(protoFile))){
			bwr.write(protoString.toString());
			bwr.flush();
			bwr.close();
		} catch (IOException e) {
			ProtoGeneratorException.throwMe("Error while writing proto to file",e);
		} finally{
			
		}
		
	}

}
