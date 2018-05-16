package edu.rabo.jfx.strategies;

import com.dukascopy.api.IAccount;
import com.dukascopy.api.IBar;
import com.dukascopy.api.IContext;
import com.dukascopy.api.IMessage;
import com.dukascopy.api.IStrategy;
import com.dukascopy.api.ITick;
import com.dukascopy.api.Instrument;
import com.dukascopy.api.JFException;
import com.dukascopy.api.Period;

public class PortfolioStrategy2 implements IStrategy {

	private IContext context;
	
	@Override
	public void onStart(IContext context) throws JFException {
		this.context = context;
	}

	@Override
	public void onTick(Instrument instrument, ITick tick) throws JFException {
		context.getConsole().getOut().println("################################");
	}

	@Override
	public void onBar(Instrument instrument, Period period, IBar askBar, IBar bidBar) throws JFException {
		context.getConsole().getOut().println("FEEEGERTFERERERERERERERERE");
	}

	@Override
	public void onMessage(IMessage message) throws JFException {
		context.getConsole().getOut().println("public void onMessage(IMessage message) throws JFException {" + message);		
	}

	@Override
	public void onAccount(IAccount account) throws JFException {
		StringBuilder accountInfo = new StringBuilder();
		accountInfo.append("Equity: " + String.valueOf(account.getEquity()) + "; ");
		accountInfo.append("Balance: " + String.valueOf(account.getBalance()));
		context.getConsole().getOut().println(accountInfo.toString());
		
	}

	@Override
	public void onStop() throws JFException {
		context.getConsole().getOut().println("################################");
	}

}
