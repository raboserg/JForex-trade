package edu.rabo.jfx.strategies;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.dukascopy.api.IAccount;
import com.dukascopy.api.IBar;
import com.dukascopy.api.IConsole;
import com.dukascopy.api.IContext;
import com.dukascopy.api.IHistory;
import com.dukascopy.api.IMessage;
import com.dukascopy.api.IStrategy;
import com.dukascopy.api.ITick;
import com.dukascopy.api.Instrument;
import com.dukascopy.api.JFException;
import com.dukascopy.api.OfferSide;
import com.dukascopy.api.Period;

public class WriteCurrencyHistory implements IStrategy {

	private Period period = null;
	private IHistory history = null;
	private IConsole console = null;
	private Instrument instrument = null;

	public WriteCurrencyHistory(final Period period, final Instrument instrument) {
		this.period = period;
		this.instrument = instrument;
	}
	
	@Override
	public void onStart(IContext context) throws JFException {
		this.history = context.getHistory();
		this.console = context.getConsole();
	}

	@Override
	public void onTick(Instrument instrument, ITick tick) throws JFException {
		// TODO Auto-generated method stub
	}

	@Override
	public void onBar(Instrument instrument, Period period, IBar askBar, IBar bidBar) throws JFException {
		if (!instrument.equals(this.instrument) || !period.equals(this.period)) {
	        return; //quit
	    }
		final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date dateFrom, dateTo;
		try {
			dateFrom = dateFormat.parse("25/04/2017 00:00:00");
			dateTo = dateFormat.parse("25/04/2018 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}
		final List<IBar> bars = history.getBars(Instrument.EURUSD, Period.DAILY, OfferSide.ASK, dateFrom.getTime(),
				dateTo.getTime());
		console.getOut().format("bar FROM=%s; bar TO=%s", bars.get(0), bars.get(bars.size() - 1)).println();
	}

	@Override
	public void onMessage(IMessage message) throws JFException {
		// TODO Auto-generated method stub
	}

	@Override
	public void onAccount(IAccount account) throws JFException {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStop() throws JFException {
		// TODO Auto-generated method stub
	}
}
