package com.anirban.protogen.model;

import java.util.List;

import com.anirban.protogen.annotations.Ignore;

public class Position {

	private Instrument instrument;

	private Double quantity[];
	@Ignore
	private double marketValue;

	private List<String> countries;

	private Exposure exposure;

	static class Exposure {
		private double deltaExposure;
		private double betaExposure;

		public Exposure(double deltaExposure, double betaExposure) {
			this.deltaExposure = deltaExposure;
			this.betaExposure = betaExposure;
		}

		public double getDeltaExposure() {
			return deltaExposure;
		}

		public double getBetaExposure() {
			return betaExposure;
		}
	}

	public Position(Instrument instrument, Double[] quantity,
			double marketValue, List<String> countries, Exposure exposure) {
		this.instrument = instrument;
		this.quantity = quantity;
		this.marketValue = marketValue;
		this.countries = countries;
		this.exposure = exposure;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public Double[] getQuantity() {
		return quantity;
	}

	public double getMarketValue() {
		return marketValue;
	}

	public List<String> getCountries() {
		return countries;
	}

	public Exposure getExposure() {
		return exposure;
	}
	
	

}
