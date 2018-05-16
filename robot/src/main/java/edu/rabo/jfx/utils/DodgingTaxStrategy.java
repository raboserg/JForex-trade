package edu.rabo.jfx.utils;

public class DodgingTaxStrategy<P extends TaxPayer> implements TaxStrategy<P> {

	@Override
	public double computeTax(P payer) {
		return 0;
	}
}
