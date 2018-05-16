package edu.rabo.jfx.strategies;

import com.dukascopy.api.Configurable;
import com.dukascopy.api.Filter;
import com.dukascopy.api.IAccount;
import com.dukascopy.api.IBar;
import com.dukascopy.api.IEngine;
import com.dukascopy.api.IIndicators;
import com.dukascopy.api.IMessage;
import com.dukascopy.api.IOrder;
import com.dukascopy.api.ITick;
import com.dukascopy.api.Instrument;
import com.dukascopy.api.JFException;
import com.dukascopy.api.OfferSide;
import com.dukascopy.api.Period;

public class PortfolioStrategy extends AStrategy {

	private IBar previousBar;
	private Instrument instrument;

	private static final int PREV = 1;
	private static final int SECOND_TO_LAST = 0;

	@Configurable("Indicator time period")
	public int indicatorTimePeriod = 24; //?????
	@Configurable(value = "Period value")
	private Period period = Period.TEN_SECS;
	@Configurable(value = "Offer Side value", obligatory = true)
	public OfferSide myOfferSide = OfferSide.ASK;

	public PortfolioStrategy(final Instrument instrument, final Period period) {
		this.period = period;
		this.instrument = instrument;
	}

	@Override
	public void onTick(Instrument instrument, ITick tick) throws JFException {
	}
		
	@Override
	public void onBar(Instrument instrument, Period period, IBar askBar, IBar bidBar) throws JFException {
		if (!instrument.equals(this.instrument) || !period.equals(this.period)) {
			return; // quit
		}
		
		IEngine.OrderCommand myCommand = null;
		final int candlesBefore = 2, candlesAfter = 0;
		// get SMA values of 2nd-to last and last (two last completed) bars
		previousBar = myOfferSide == OfferSide.ASK ? askBar : bidBar;
		final long currBarTime = previousBar.getTime();
		double sma[] = getIndicators().linearReg(instrument, period, myOfferSide, IIndicators.AppliedPrice.CLOSE,
				indicatorTimePeriod, Filter.ALL_FLATS, candlesBefore, currBarTime, candlesAfter);
		
		getConsole().getOut().println(String.format("Bar Indicator Values: Second-to-last = %.5f; Last Completed = %.5f",
				sma[SECOND_TO_LAST], sma[PREV]));

		//Double sl, tp;
		double sl, tp, lot = tradingConditions.getLot();
		if (sma[PREV] > sma[SECOND_TO_LAST]) {
			getConsole().getOut().println("Up-trend"); // indicator goes up
			myCommand = IEngine.OrderCommand.BUY;
			sl = tradingConditions.getBuyStopLoss();
			tp = tradingConditions.getBuyTakeProfit();
		} else if (sma[PREV] < sma[SECOND_TO_LAST]) {
			getConsole().getOut().println("Down-trend"); // indicator goes down
			sl = tradingConditions.getSellStopLoss();
			tp = tradingConditions.getSellTakeProfit();
			myCommand = IEngine.OrderCommand.SELL;
		} else {
			return;
		}
		
		final IOrder order = getEngine().getOrder("MyStrategyOrder");
		if (order != null && getEngine().getOrders().contains(order) && order.getOrderCommand() != myCommand) {
			order.close();
			order.waitForUpdate(IOrder.State.CLOSED); // wait till the order is closed
			getConsole().getOut().println("Order " + order.getLabel() + " is closed");
		}
		if (order == null || !getEngine().getOrders().contains(order)) {
			getEngine().submitOrder("MyStrategyOrder", instrument, myCommand, lot, 0, 0, sl, tp);
		}
	}

	@Override
	public void onMessage(IMessage message) throws JFException {
		getConsole().getOut().println("Message " + message.toString());
	}

	@Override
	public void onAccount(IAccount account) throws JFException {
		StringBuilder accountInfo = new StringBuilder();
		accountInfo.append("Equity: " + String.valueOf(account.getEquity()) + "; ");
		accountInfo.append("Balance: " + String.valueOf(account.getBalance()));
		getConsole().getOut().println(accountInfo.toString());
	}

	@Override
	public void onStop() throws JFException {

	}

	@Override
	public Instrument getInstrument() {
		return this.instrument;
	}
}
