package com.sirma.itt.javacourse.chatcommon.utils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Singleton of the {@link ResourceBundle} of the server.
 * 
 * @author Sinan
 */
public final class LanguageBundleSingleton {

	private static ResourceBundle serverBundle = ResourceBundle.getBundle("ServerBundle",
			new Locale("en", "US"));
	private static ResourceBundle clientLoginBundle = ResourceBundle.getBundle("ClientLoginBundle",
			new Locale("en", "US"));
	private static ResourceBundle clientBundle = ResourceBundle.getBundle("ClientBundle",
			new Locale("en", "US"));

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
	public static ResourceBundle getServerBundleInstance() {
		return serverBundle;
	}

	/**
	 * Returns the {@link ResourceBundle} of the client login form.
	 * 
	 * @return - the resource bundle of the client login form
	 */
	public static ResourceBundle getClientLoginBundleInstance() {
		return clientLoginBundle;
	}

	/**
	 * Returns the {@link ResourceBundle} of the client.
	 * 
	 * @return - the resource bundle of the client
	 */
	public static ResourceBundle getClientBundleInstance() {
		return clientBundle;
	}

	/**
	 * Sets the {@link Locale} of the {@link ResourceBundle}.
	 * 
	 * @param locale
	 *            - the locale of the bundle
	 */
	public static void setServerLocale(Locale locale) {
		synchronized (serverBundle) {
			serverBundle = ResourceBundle.getBundle("ServerBundle", locale);
		}
	}

	/**
	 * Sets the {@link Locale} of the {@link ResourceBundle} for the client.
	 * 
	 * @param locale
	 *            - the locale of the bundle for the client
	 */
	public static void setClientLocale(Locale locale) {
		synchronized (clientBundle) {
			clientBundle = ResourceBundle.getBundle("ClientBundle", locale);
		}
	}

	/**
	 * Sets the {@link Locale} of the {@link ResourceBundle} for the client login form.
	 * 
	 * @param locale
	 *            - the locale of the bundle for the client login form
	 */
	public static void setClientLoginLocale(Locale locale) {
		synchronized (clientLoginBundle) {
			clientLoginBundle = ResourceBundle.getBundle("ClientLoginBundle", locale);
		}
	}

}
