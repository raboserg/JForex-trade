
package edu.rabo.jfx.config;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dukascopy.api.IStrategy;
import com.dukascopy.api.Instrument;
import com.dukascopy.api.LoadingProgressListener;
import com.dukascopy.api.system.IClient;
import com.dukascopy.api.system.ITesterClient;

import edu.rabo.jfx.PinDialog;
import edu.rabo.jfx.account.AccountService;

/**
 * @author v-srabukha
 */
public class ClientInitializer implements IClientInitializer {

	@Autowired
	private IClient client;

	private Set<Instrument> instruments = null;
	private AccountService accountService = null;
	private Map<String, IStrategy> strategies = null;

	private static final Logger logger = LoggerFactory.getLogger(ClientInitializer.class);

	public ClientInitializer() throws Exception {
	}

	// @PostConstruct
	public void initialize() throws Exception {
		if (connect()) {
			runStrategies();
		}
	}

	@PreDestroy
	public void destroy() throws Exception {
		client.getStartedStrategies().forEach((key, entry) -> {
			logger.info("### destroy() ### - Starting strategy".concat(entry.toString()));
		});
		client.disconnect();
	}

	@Override
	public boolean connect() throws Exception {
		if (Objects.nonNull(client)) {
			logger.info("Connecting...");
			// wait max ten seconds && wait for it to connect
			for (int j = 10; j > 0 && !client.isConnected(); j--) {
				if (accountService.isLiveAccount()) {
					client.connect(accountService.getUrl(), accountService.getUser(), accountService.getPassword(),
							PinDialog.showAndGetPin(client, accountService.getUrl()));
				} else {
					client.connect(accountService.getUrl(), accountService.getUser(), accountService.getPassword());
				}
				Thread.sleep(1000);
			}
			if (!client.isConnected()) {
				logger.error("Failed to connect Dukascopy servers");
			}
		}
		return client.isConnected();
	}

	@Override
	public void runStrategies() throws Exception {
		final Map<String, IStrategy> strategies = getStrategies();
		if (!strategies.isEmpty()) {
			strategies.forEach((key, entry) -> {
				client.startStrategy(entry);
				logger.info("Starting strategy ".concat(key));
				logger.info("key: " + key + " value:" + entry);
			});
		}
	}

	public void setStrategies(final Map<String, IStrategy> strategies) {
		this.strategies = strategies;
	}

	public Map<String, IStrategy> getStrategies() {
		return this.strategies;
	}

	public void setAccountService(final AccountService accountService) {
		this.accountService = accountService;
	}

	public void setInstrumens(final Set<Instrument> instruments) {
		if (!instruments.isEmpty()) {
			client.setSubscribedInstruments(instruments);
			this.instruments = instruments;
		}
	}

	public Set<Instrument> getInstruments() {
		return this.instruments;
	}

	@Override
	public void reconnect() {
		this.client.reconnect();
	}

	@Override
	public IClient getClient() {
		return client;
	}

	@Override
	public boolean isConnected() {
		return client.isConnected();
	}

	@Override
	public Future<?> downloadData(LoadingProgressListener loadingProgressListener)
			throws InterruptedException, ExecutionException {
		Future<?> future = null;
		if (client instanceof ITesterClient) {
			logger.info("Downloading data");
			future = ((ITesterClient) client).downloadData(loadingProgressListener);

		}
		return future;
	}
}
