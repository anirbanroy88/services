package com.anirban.serialize.util;

import java.util.ArrayList;
import java.util.List;

import com.anirban.model.CalcRequest;
import com.anirban.model.CalculationRunContext;
import com.anirban.model.Portfolio;
import com.anirban.model.RTMAccount;
import com.anirban.model.RTMAccount.AccountSource;

public class MockRequest {

	public static CalcRequest mock(){
		List<CalculationRunContext> runContexts = new ArrayList<CalculationRunContext>();
		runContexts.add(new CalculationRunContext("RTM-ANIRBAN-SUBMIT1", new RTMAccount("THEORAM8", "HPB", AccountSource.RTMHK), 34567L));
		runContexts.add(new CalculationRunContext("RTM-ANIRBAN-SUBMIT2", new RTMAccount("H235812", "PB", AccountSource.RTMNY), 265413L));
		Portfolio portfolio = new Portfolio("IBMPUTACLL", 10.5);
		CalcRequest request = new CalcRequest(runContexts, portfolio);
		return request;
	}
}
