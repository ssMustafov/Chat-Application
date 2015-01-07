package com.sirma.itt.javacourse.chatcommon.utils;

import java.text.SimpleDateFormat;

/**
 * Holds method for getting the date.
 * 
 * @author Sinan
 */
public final class Date {

	public static final String SIMPLE_TIME_DATE_FORMAT = "kk:mm:ss";
	public static final String FULL_DATE_FORMAT = "dd-EEEE-Y";

	/**
	 * Protects from instantiation.
	 */
	private Date() {
	}

	/**
	 * Returns the current date in given format. The date format can be taken from this class in the
	 * static constants.
	 * 
	 * @param dateFormat
	 *            - the format of the date
	 * @return - the formatted current date
	 */
	public static String getCurrentDate(String dateFormat) {
		java.util.Date date = new java.util.Date();
		String formattedDate = new SimpleDateFormat(dateFormat).format(date);
		return formattedDate;
	}

}
