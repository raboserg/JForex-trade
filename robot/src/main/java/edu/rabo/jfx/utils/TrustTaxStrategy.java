package edu.rabo.jfx.utils;

public class TrustTaxStrategy extends DefaultTaxStrategy<Trust> {
	@Override
	public double computeTax(Trust trust) {
		return trust.isNonProfit() ? 0 : super.computeTax(trust);
	}
}
