
package edu.rabo.jfx.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dukascopy.api.system.IClient;
import com.dukascopy.api.system.ISystemListener;

import edu.rabo.jfx.account.AccountService;

public class RealSystemListener implements ISystemListener {

	private IClient client = null;
	private AccountService accountService = null;

	private int lightReconnects = 0;
	private static final Logger logger = LoggerFactory.getLogger(RealSystemListener.class);

	public RealSystemListener() {
	}
	
	public RealSystemListener(final IClient client) {
		this.client = client;
	}
	
	public void onStart(final long processId) {
		logger.info("Strategy started: " + processId);
	}

	public void onStop(final long processId) {
		logger.info("Strategy stopped: " + processId);
		if (getClient().getStartedStrategies().size() == 0) {
			System.exit(0);
		}
	}

	public void onConnect() {
		logger.info("Connected");
		lightReconnects = 3;
	}

	@Override
	public void onDisconnect() {
		logger.warn("Disconnected");

		final Runnable runnable = new Runnable() {
			@Override
			public void run() {
				if (lightReconnects > 0) {
					getClient().reconnect();
					--lightReconnects;
				} else {
					do {
						try {
							Thread.sleep(60 * 1000);
						} catch (InterruptedException e) {
						}
						try {
							if (getClient().isConnected()) {
								break;
							}
							getClient().connect(getAccountService().getUrl(), getAccountService().getUser(),
									getAccountService().getPassword());

						} catch (Exception e) {
							logger.error(e.getMessage(), e);
						}
					} while (!getClient().isConnected());
				}
			}
		};
		new Thread(runnable).start();
	}

	public IClient getClient() {
		return client;
	}

	public void setClient(final IClient client) {
		this.client = client;
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(final AccountService accountService) {
		this.accountService = accountService;
	}
}