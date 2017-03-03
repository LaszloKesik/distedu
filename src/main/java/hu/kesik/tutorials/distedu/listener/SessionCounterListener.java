package hu.kesik.tutorials.distedu.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SessionCounterListener implements HttpSessionListener {
	private static final Logger LOGGER = LogManager.getLogger();
	private static int sessionCounter = 0;

	@Override
	public void sessionCreated(HttpSessionEvent hse) {
		synchronized (this) {
			LOGGER.debug("Session created: " + hse.getSession().getId());
			sessionCounter++;
		}
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent hse) {
		synchronized (this) {
			LOGGER.debug("Session destroyed: " + hse.getSession().getId());
			sessionCounter--;
		}
	}

	public static int getSessionNumber() {
		return sessionCounter;
	}

}
