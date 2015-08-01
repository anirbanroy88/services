package com.anirban.protogen.model;

import com.anirban.protogen.annotations.ProtoElement;
import com.anirban.protogen.annotations.ProtoEnum;
import com.anirban.protogen.annotations.ProtoMessage;
import com.anirban.protogen.model.PrimitiveProtoTypes;
import com.anirban.protogen.model.Property;

@ProtoMessage(messageName="Product")
public class Instrument {
	
	
	private String cusip;
	
	private double marginPrice;
	
	private Double scaleFactor;
	
	private int conversionRatio;
	
	@ProtoElement(protoName="isVanilla",modifier=Property.PropertyType.OPTIONAL,protoType=PrimitiveProtoTypes.BOOL)
	private boolean hasUnderlier;
	
	private InstrumentType type;
	
	@ProtoEnum(protoName="ProductType")
	enum InstrumentType{
		EQTY,
		PUT,
		CALL,
		BOND,
		IRFUT,
		CCALL
	}

	public Instrument(String cusip, double marginPrice, Double scaleFactor,
			int conversionRatio, boolean hasUnderlier, InstrumentType type) {
		this.cusip = cusip;
		this.marginPrice = marginPrice;
		this.scaleFactor = scaleFactor;
		this.conversionRatio = conversionRatio;
		this.hasUnderlier = hasUnderlier;
		this.type = type;
	}

	public String getCusip() {
		return cusip;
	}

	public double getMarginPrice() {
		return marginPrice;
	}

	public Double getScaleFactor() {
		return scaleFactor;
	}

	public int getConversionRatio() {
		return conversionRatio;
	}

	public boolean isHasUnderlier() {
		return hasUnderlier;
	}

	public InstrumentType getType() {
		return type;
	}
	
	

}
