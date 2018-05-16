package edu.rabo.jfx;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.dukascopy.api.IStrategy;
import com.dukascopy.api.Instrument;
import com.dukascopy.api.Period;

import edu.rabo.jfx.strategies.PortfolioStrategy;

public class StrategyHelper {

		public static Map<String, IStrategy> getStrategies(final Instrument instrument, final Period period) {
			final Map<String, IStrategy> strategies = new HashMap<>();
			// strategies.put(Instrument.EURUSD.name(), new PortfolioStrategy2());
			strategies.put(instrument.name(), new PortfolioStrategy(instrument, period));
			return strategies;
		}

		@SuppressWarnings("deprecation")
		public static Set<Instrument> getInstruments() {
			final Set<Instrument> instruments = new HashSet<>();
			instruments.add(Instrument.EURUSD);
			instruments.add(Instrument.ADSDEEUR);
			return instruments;
		}

}
