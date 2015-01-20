package com.sirma.itt.javacourse.chatcommon.models;

import java.io.Serializable;

/**
 * Represents a query. A query consists of - query type ({@link QueryTypes}) and a message for the
 * query. Implements {@link Serializable}. The server and the client communicates through this
 * structure only.
 * 
 * @author Sinan
 */
public class Query implements Serializable {

	private static final long serialVersionUID = 1533358871950691245L;
	private QueryTypes queryType;
	private String message;

	/**
	 * Creates a new query with given type of the query and message for the query.
	 * 
	 * @param queryType
	 *            - the type of the query
	 * @param message
	 *            - the message in the query
	 */
	public Query(QueryTypes queryType, String message) {
		this.setQueryType(queryType);
		this.setMessage(message);
	}

	/**
	 * Getter method for queryType.
	 * 
	 * @return the queryType
	 */
	public QueryTypes getQueryType() {
		return queryType;
	}

	/**
	 * Setter method for queryType.
	 * 
	 * @param queryType
	 *            the queryType to set
	 */

	public void setQueryType(QueryTypes queryType) {
		this.queryType = queryType;
	}

	/**
	 * Getter method for message.
	 * 
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter method for message.
	 * 
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(queryType.toString());
		result.append(": ");
		result.append(message);

		return result.toString();
	}
}
