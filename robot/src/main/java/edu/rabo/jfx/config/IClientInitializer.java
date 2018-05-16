
package edu.rabo.jfx.config;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.dukascopy.api.LoadingProgressListener;
import com.dukascopy.api.system.IClient;

public interface IClientInitializer {
	IClient getClient();

	boolean isConnected();

	boolean connect() throws Exception;

	void runStrategies() throws Exception;

	void reconnect();

	void initialize() throws Exception;
	
	Future<?> downloadData(LoadingProgressListener loadingProgressListener) throws InterruptedException, ExecutionException;
}
