package com.anirban.model;

import java.util.List;

import io.protostuff.Tag;

public class CalcRequest {
	
	@Tag(1)
	private List<CalculationRunContext> runContexts;
	@Tag(2)
	private Portfolio portfolio;
	
	public CalcRequest(List<CalculationRunContext> runContexts,
			Portfolio portfolio) {
		this.runContexts = runContexts;
		this.portfolio = portfolio;
	}

	public List<CalculationRunContext> getRunContexts() {
		return runContexts;
	}

	public Portfolio getQuantity() {
		return portfolio;
	}
	
	
}
