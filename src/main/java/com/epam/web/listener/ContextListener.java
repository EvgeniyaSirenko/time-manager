package com.epam.web.listener;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
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
		log.debug("Servlet context destruction starts");
		// do nothing
		log.debug("Servlet context destruction finished");
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		log.debug("Servlet context initialization starts");

		ServletContext servletContext = event.getServletContext();
		// initLog4J(servletContext);
		initCommandContainer();
		initI18N(servletContext);

		log.debug("Servlet context initialization finished");
	}
		
	/**
	 * Initializes i18n subsystem.
	 */
	private void initI18N(ServletContext servletContext) {
		log.debug("I18N subsystem initialization started");

		String localesFileName = servletContext.getInitParameter("locales");
		
		// obtain real path on server
		String localesFileRealPath = servletContext.getRealPath(localesFileName);
		
		// locale descriptions
		Properties locales = new Properties();
		try {
			locales.load(new FileInputStream(localesFileRealPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// save descriptions to servlet context
		servletContext.setAttribute("locales", locales);
		locales.list(System.out);
		
		//initLog4J(servletContext);
		initCommandContainer();
		//initI18N(servletContext);
		
		log.debug("Servlet context initialization finished");
		}
		
			

//	private void initLog4J(ServletContext servletContext) {
//		log("Log4J initialization started");
//		try {
//			PropertyConfigurator.configure(servletContext.getRealPath(
//							"WEB-INF/log4j.properties"));
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		
//		log("Log4J initialization finished");
//	}

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
}

//String localesValue = servletContext.getInitParameter("locales");
//if (localesValue == null || localesValue.isEmpty()) {
//log.warn("'locales' init parameter is empty, the default encoding will be used");
//} else {
//List<String> locales = new ArrayList<String>();
//StringTokenizer st = new StringTokenizer(localesValue);
//while (st.hasMoreTokens()) {
//	String localeName = st.nextToken();
//	locales.add(localeName);
//}
//
//log.debug("Application attribute set: locales --> " + locales);
//servletContext.setAttribute("locales", locales);
//}
//
//log.debug("I18N subsystem initialization finished");
//}