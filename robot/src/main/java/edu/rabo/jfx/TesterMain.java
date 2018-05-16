package edu.rabo.jfx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.AbstractEnvironment;

import com.dukascopy.api.Instrument;
import com.dukascopy.api.OfferSide;
import com.dukascopy.api.Period;
import com.dukascopy.api.system.ITesterClient;
import com.dukascopy.api.system.ITesterClient.InterpolationMethod;
import com.dukascopy.api.util.DateUtils;

import edu.rabo.jfx.config.BaseConfig;
import edu.rabo.jfx.config.ClientInitializer;

/**
 * This small program demonstrates how to initialize Dukascopy tester and start
 * a strategy
 */
public class TesterMain {
	private static final Period period = Period.DAILY;
	private static final OfferSide offerSide = OfferSide.ASK;
	private static final InterpolationMethod interpolationMethod = InterpolationMethod.CLOSE_TICK;
	private static final String fromDateInterval = "2018-01-01 00:00:00 000";
	private static final String toDateInterval = "2018-04-27 00:00:00 000";

	private static final Logger logger = LoggerFactory.getLogger(TesterMain.class);

	@SuppressWarnings({ "resource", "deprecation" })
	public static void main(String[] args) throws Exception {

		System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "test");

		final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BaseConfig.class);

		final ClientInitializer clientInitializer = context.getBean(ClientInitializer.class);
		final ITesterClient client = (ITesterClient) clientInitializer.getClient();

		final long from = DateUtils.parseAsSeconds(fromDateInterval);
		final long to = DateUtils.parseAsSeconds(toDateInterval);
		client.setDataInterval(period, offerSide, interpolationMethod, from, to);
		
		logger.info("setting initial deposit");
		client.setInitialDeposit(Instrument.EURUSD.getSecondaryJFCurrency(), 50000);

		// TODO
		clientInitializer.setInstrumens(StrategyHelper.getInstruments());
		clientInitializer.setStrategies(StrategyHelper.getStrategies(Instrument.ADSDEEUR, period));

		clientInitializer.connect();
		clientInitializer.downloadData(null).get();
		clientInitializer.runStrategies();
	}
}