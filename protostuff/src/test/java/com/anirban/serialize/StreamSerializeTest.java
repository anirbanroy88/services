package com.anirban.serialize;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.junit.Test;
import com.anirban.model.CalcRequest;
import com.anirban.serialize.util.MockRequest;

public class StreamSerializeTest {

	@Test
	public void testSerialize() {
		
		
		StreamSerializer<CalcRequest> streamSerializer = new StreamSerializer<CalcRequest>(CalcRequest.class);
		try {
			byte[] bytes = streamSerializer.serialize(MockRequest.mock()).toString().getBytes();
			CalcRequest retrievedRequest = streamSerializer.deserialize(new ByteArrayInputStream(bytes));
			assertTrue(retrievedRequest != null);
			assertTrue(retrievedRequest.getRunContexts().get(0).getAccount().getAccountNumber().equals("THEORAM8"));
		} catch (IOException e) {
			e.printStackTrace();
			assertFalse(true);
		}
		
	}

}
