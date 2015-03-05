package com.sirma.itt.javacourse.chatcommon.utils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Singleton of the {@link ResourceBundle} of the server.
 * 
 * @author Sinan
 */
public final class LanguageBundleSingleton {

	private static final String SERVER_BUNDLE_FILE_NAME = "ServerBundle";
	private static final String CLIENT_BUNDLE_FILE_NAME = "ClientBundle";
	private static final String CLIENT_LOGIN_BUNDLE_FILE_NAME = "ClientLoginBundle";
	private static final String EN_LANGUAGE = "en";
	private static final String US_COUNTRY = "US";

	private static ResourceBundle serverBundle = ResourceBundle.getBundle(SERVER_BUNDLE_FILE_NAME,
			new Locale(EN_LANGUAGE, US_COUNTRY));
	private static ResourceBundle clientLoginBundle = ResourceBundle.getBundle(
			CLIENT_LOGIN_BUNDLE_FILE_NAME, new Locale(EN_LANGUAGE, US_COUNTRY));
	private static ResourceBundle clientBundle = ResourceBundle.getBundle(CLIENT_BUNDLE_FILE_NAME,
			new Locale(EN_LANGUAGE, US_COUNTRY));

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
		serverBundle = ResourceBundle.getBundle(SERVER_BUNDLE_FILE_NAME, locale);
	}

	/**
	 * Sets the {@link Locale} of the {@link ResourceBundle} for the client.
	 * 
	 * @param locale
	 *            - the locale of the bundle for the client
	 */
	public static void setClientLocale(Locale locale) {
		clientBundle = ResourceBundle.getBundle(CLIENT_BUNDLE_FILE_NAME, locale);
	}

	/**
	 * Sets the {@link Locale} of the {@link ResourceBundle} for the client login form.
	 * 
	 * @param locale
	 *            - the locale of the bundle for the client login form
	 */
	public static void setClientLoginLocale(Locale locale) {
		clientLoginBundle = ResourceBundle.getBundle(CLIENT_LOGIN_BUNDLE_FILE_NAME, locale);
	}

}
