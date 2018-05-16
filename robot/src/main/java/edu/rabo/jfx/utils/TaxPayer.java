package edu.rabo.jfx.utils;

/**
 * @author Siarhei_Rabukha
 *
 */
public abstract class TaxPayer {
	public long income;

	public TaxPayer(final long income) {
		this.income = income;
	}

	public long getIncome() {
		return income;
	}

}
