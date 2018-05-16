
package edu.rabo.jfx.system;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dukascopy.api.IOrder;
import com.dukascopy.api.system.IClient;
import com.dukascopy.api.system.ISystemListener;
import com.dukascopy.api.system.ITesterClient;
import com.dukascopy.api.util.DateUtils;

public class TestSystemListener implements ISystemListener {

	private ITesterClient client = null;

	private static String pathToFile = "D:\\\\My\\\\JForex\\\\trade\\\\robot\\\\test\\\\orders.txt";
	private static String pathToReport = "D:\\My\\JForex\\trade\\robot\\test\\report.html";

	private static final Logger logger = LoggerFactory.getLogger(TestSystemListener.class);
	
	public TestSystemListener() {
		
	}
	
	public TestSystemListener(final ITesterClient client) {
		this.client = client;
	}
	
	public void onStart(final long processId) {
		logger.info("Strategy started: " + processId);
	}

	public void onStop(final long processId) {
		logger.info("Strategy stopped: " + processId);
		final File reportFile = new File(pathToReport);
		try {
			final String comma = ";";
			final File file = new File(pathToFile);
			final StringBuilder builder = new StringBuilder();
			//final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			final List<IOrder> closedOrders = client.getReportData(processId).getClosedOrders();
			
			closedOrders.forEach((order)->{
				builder.append(DateUtils.formatToSeconds(order.getCloseTime()));
				//builder.append(dateFormat.format(order.getCloseTime()));
				builder.append(comma);
				builder.append(Double.toString(order.getClosePrice()));
				builder.append(comma);
				builder.append(Double.toString(order.getProfitLossInUSD()));
				builder.append('\r');
				try {
					FileUtils.writeStringToFile(file, builder.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
				});
			client.createReport(processId, reportFile);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (client.getStartedStrategies().size() == 0) {
			System.exit(0);
		}

	}

	public void onConnect() {
		logger.info("Connected");
	}

	@Override
	public void onDisconnect() {
		logger.warn("Disconnected");
	}

	public IClient getClient() {
		return client;
	}

	public void setClient(final ITesterClient client) {
		this.client = client;
	}
}