package edu.rabo.jfx.utils;

public interface TaxStrategy<P extends TaxPayer> {
	public double computeTax(P payer);
}
