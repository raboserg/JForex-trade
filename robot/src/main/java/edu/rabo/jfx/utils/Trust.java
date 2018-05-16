package edu.rabo.jfx.utils;

public class Trust extends TaxPayer {
	private boolean nonProfit;

	public Trust(final long income, final boolean nonProfit) {
		super(income);
		this.nonProfit = nonProfit;
	}

	public boolean isNonProfit() {
		return this.nonProfit;
	}

}
