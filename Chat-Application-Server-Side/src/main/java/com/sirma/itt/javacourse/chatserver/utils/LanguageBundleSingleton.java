package com.sirma.itt.javacourse.chatserver.utils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Singleton of the {@link ResourceBundle} of the server.
 * 
 * @author Sinan
 */
public final class LanguageBundleSingleton {

	private static ResourceBundle bundle = ResourceBundle.getBundle("ServerBundle", new Locale(
			"en", "US"));

	/**
	 * Protects from instantiation.
	 */
	private LanguageBundleSingleton() {

	}

	/**
	 * Returns the {@link ResourceBundle} of the server.
	 * 
	 * @return - the resource bundle of the server
	 */
	public static ResourceBundle getBundleInstance() {
		return bundle;
	}

	/**
	 * Sets the {@link Locale} of the {@link ResourceBundle}.
	 * 
	 * @param locale
	 *            - the locale of the bundle
	 */
	public static void setLocale(Locale locale) {
		synchronized (bundle) {
			bundle = ResourceBundle.getBundle("ServerBundle", locale);
		}
	}

}
