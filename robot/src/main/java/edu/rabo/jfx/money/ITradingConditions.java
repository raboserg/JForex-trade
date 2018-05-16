package edu.rabo.jfx.money;

import com.dukascopy.api.JFException;

public interface ITradingConditions {
	public Double getLot() throws JFException;

	public Double getBuyStopLoss() throws JFException;

	public Double getBuyTakeProfit() throws JFException;

	public Double getSellStopLoss() throws JFException;

	public Double getSellTakeProfit() throws JFException;
}
