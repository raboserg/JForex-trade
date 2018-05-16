package edu.rabo.jfx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dukascopy.api.system.IClient;

import edu.rabo.jfx.account.AccountService;
import edu.rabo.jfx.system.RealSystemListener;

public class Connector implements Runnable {

	private final IClient client;
	private int lightReconnects = 0;
	private final AccountService accoutnService;
	private static final Logger logger = LoggerFactory.getLogger(RealSystemListener.class);

	public Connector(final IClient client, final AccountService accoutnService) {
		this.client = client;
		this.accoutnService = accoutnService;
	}

	@Override
	public void run() {
		if (lightReconnects > 0) {
			client.reconnect();
			--lightReconnects;
		} else {
			do {
				try {
					Thread.sleep(60 * 1000);
				} catch (InterruptedException e) {
				}
				try {
					if (client.isConnected()) {
						break;
					}
					client.connect(accoutnService.getUrl(), accoutnService.getUser(), accoutnService.getPassword());

				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			} while (!client.isConnected());
		}
	}

}
