package com.anirban.protogen.test.generate;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import com.anirban.protogen.api.ProtoGenrator;
import com.anirban.protogen.exception.ProtoGeneratorException;
import com.anirban.protogen.model.Position;

public class TestGenerate {

	@Test
	public void test() {
		try {
			ProtoGenrator.generate(Position.class, new File("."), "com.anirban.generated.api");
		} catch (ProtoGeneratorException e) {
			e.printStackTrace();
			assertFalse(true);
		}
		assertTrue(true);
	}

}
