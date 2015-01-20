package com.sirma.itt.javacourse.chatcommon.models;

import java.io.Serializable;

/**
 * @author Sinan
 */
public class Query implements Serializable {

	private static final long serialVersionUID = 1533358871950691245L;
	private QueryTypes queryType;
	private String message;

	public Query(QueryTypes queryType, String message) {
		this.setQueryType(queryType);
		this.setMessage(message);
	}

	public QueryTypes getQueryType() {
		return queryType;
	}

	public void setQueryType(QueryTypes queryType) {
		this.queryType = queryType;
	}

	public String getMessage() {
		return message;
	}

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
