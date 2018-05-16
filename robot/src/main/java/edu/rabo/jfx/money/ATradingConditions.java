package edu.rabo.jfx.money;

import com.dukascopy.api.IHistory;
import com.dukascopy.api.Instrument;

public abstract class ATradingConditions implements ITradingConditions {
	
	protected IHistory history;
	protected Instrument instrument;
	
	public ATradingConditions(final Instrument instrument, final IHistory history) {
		this.history = history;
		this.instrument = instrument;
	}
}
