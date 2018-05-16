package edu.rabo.jfx.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dukascopy.api.LoadingProgressListener;

public class TestLoadingProgressListener implements LoadingProgressListener {

	private static final Logger logger = LoggerFactory.getLogger(TestLoadingProgressListener.class);
	
	public TestLoadingProgressListener() {
	}

	@Override
	public void dataLoaded(long start, long end, long currentPosition, String information) {
		logger.info(information);
	}

	@Override
	public void loadingFinished(boolean allDataLoaded, long start, long end, long currentPosition) {
	}

	@Override
	public boolean stopJob() {
		return false;
	}

}
