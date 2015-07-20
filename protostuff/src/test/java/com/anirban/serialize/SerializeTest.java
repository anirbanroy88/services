package com.anirban.serialize;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.anirban.model.CalcRequest;
import com.anirban.model.CalculationRunContext;
import com.anirban.model.Portfolio;
import com.anirban.model.RTMAccount;
import com.anirban.model.RTMAccount.AccountSource;

public class SerializeTest {

	@Test
	public void testSerialize() {
		
		List<CalculationRunContext> runContexts = new ArrayList<CalculationRunContext>();
		runContexts.add(new CalculationRunContext("RTM-ANIRBAN-SUBMIT1", new RTMAccount("THEORAM8", "HPB", AccountSource.RTMHK), 34567L));
		runContexts.add(new CalculationRunContext("RTM-ANIRBAN-SUBMIT2", new RTMAccount("H235812", "PB", AccountSource.RTMNY), 265413L));
		Portfolio portfolio = new Portfolio("IBMPUTACLL", 10.5);
		CalcRequest request = new CalcRequest(runContexts, portfolio);
		StreamSerializer<CalcRequest> serializer = new StreamSerializer<CalcRequest>(CalcRequest.class);
		Deserializer<CalcRequest> deserializer = new Deserializer<CalcRequest>(CalcRequest.class);
		try {
			byte[] bytes = serializer.serialize(request).toString().getBytes();
			CalcRequest retrievedRequest = deserializer.deserialize(new ByteArrayInputStream(bytes));
			assertTrue(retrievedRequest != null);
			assertTrue(retrievedRequest.getRunContexts().get(0).getAccount().getAccountNumber().equals("THEORAM8"));
		} catch (IOException e) {
			e.printStackTrace();
			assertFalse(true);
		}
		
	}

}
