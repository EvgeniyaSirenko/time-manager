package com.epam.web.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ContextListener implements ServletContextListener {

	private static final Logger log = LogManager.getLogger(ContextListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		log("Servlet context destruction starts");
		// do nothing
		log("Servlet context destruction finished");
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		log("Servlet context initialization starts");

		ServletContext servletContext = event.getServletContext();
		// initLog4J(servletContext);
		initCommandContainer();
		initI18N(servletContext);

		log("Servlet context initialization finished");
	}

	/**
	 * Initializes i18n subsystem.
	 */
	private void initI18N(ServletContext servletContext) {
		log.debug("I18N subsystem initialization started");

		String localesValue = servletContext.getInitParameter("locales");
		if (localesValue == null || localesValue.isEmpty()) {
			log.warn("'locales' init parameter is empty, the default encoding will be used");
		} else {
			List<String> locales = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(localesValue);
			while (st.hasMoreTokens()) {
				String localeName = st.nextToken();
				locales.add(localeName);
			}

			log.debug("Application attribute set: locales --> " + locales);
			servletContext.setAttribute("locales", locales);
		}

		log.debug("I18N subsystem initialization finished");
	}

	/**
	 * Initializes CommandContainer.
	 * 
	 * @param servletContext
	 */
	private void initCommandContainer() {
		log.debug("Command container initialization started");

		// initialize commands container
		// just load class to JVM
		try {
			Class.forName("com.epam.web.command.CommandContainer");
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}

		log.debug("Command container initialization finished");
	}

	private void log(String msg) {
		System.out.println("[ContextListener] " + msg);
	}
}
