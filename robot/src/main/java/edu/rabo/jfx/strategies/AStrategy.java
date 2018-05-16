package edu.rabo.jfx.strategies;

import com.dukascopy.api.IConsole;
import com.dukascopy.api.IContext;
import com.dukascopy.api.IEngine;
import com.dukascopy.api.IIndicators;
import com.dukascopy.api.IStrategy;
import com.dukascopy.api.Instrument;
import com.dukascopy.api.JFException;

import edu.rabo.jfx.money.TradingConditions;

public abstract class AStrategy implements IStrategy {

	private IContext context;

	protected TradingConditions tradingConditions;
	
	@Override
	public void onStart(IContext context) throws JFException {
		setContext(context);
		setConditions(new TradingConditions(getInstrument(), context.getHistory()));
	}
	
	private void setContext(IContext context) {
		this.context = context;
	}
	
	private IContext getContext() {
		return context;
	}
	
	protected IConsole getConsole() {
		return getContext().getConsole();
	}

	protected IEngine getEngine() {
		return getContext().getEngine();
	}

	protected IIndicators getIndicators() {
		return getContext().getIndicators();
	}

	/*public TradingConditions getConditions() {
		return tradingConditions;
	}*/

	private void setConditions(final TradingConditions tradingConditions) {
		this.tradingConditions = tradingConditions;
	}

	abstract Instrument getInstrument();
	
}
