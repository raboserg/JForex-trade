package edu.rabo.jfx.utils;

public class DefaultTaxStrategy<P extends TaxPayer> implements TaxStrategy<P> {
	private static final double RATE = 0.40;

	@Override
	public double computeTax(P payer) {
		return payer.getIncome() * RATE;
	}
}
