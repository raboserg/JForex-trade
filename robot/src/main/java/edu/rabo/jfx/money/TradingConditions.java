package edu.rabo.jfx.money;

import com.dukascopy.api.IHistory;
import com.dukascopy.api.Instrument;
import com.dukascopy.api.JFException;

public class TradingConditions extends ATradingConditions {

	private double amount = 1.0;
	private int takeProfitPips = 40;
	private int stopLossPips = takeProfitPips / 2;

	public TradingConditions(final Instrument instrument, final IHistory history) {
		super(instrument, history);
	}

	public TradingConditions(final Instrument instrument, final IHistory history, final double amount, final int takeProfitPips) {
		super(instrument, history);
		this.amount = amount;
		this.takeProfitPips = takeProfitPips / 2;
	}

	@Override
	public Double getLot() throws JFException {
		return amount;
	}

	@Override
	public Double getBuyStopLoss() throws JFException {
		return history.getLastTick(instrument).getAsk() - stopLossPips * getPipValue();
	}

	@Override
	public Double getBuyTakeProfit() throws JFException {
		return history.getLastTick(instrument).getAsk() + takeProfitPips * getPipValue();
	}

	@Override
	public Double getSellStopLoss() throws JFException {
		return history.getLastTick(instrument).getBid() + stopLossPips * getPipValue();

	}

	@Override
	public Double getSellTakeProfit() throws JFException {
		return history.getLastTick(instrument).getBid() - takeProfitPips * getPipValue();
	}

	private double getPipValue() {
		return instrument.getPipValue();
	}
}
