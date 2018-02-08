package com.abc.de.exceptions;

/**
 * This exception is thrown in case of invalid data.
 * 
 * @author Shekhar Suman
 * @version 1.0
 * @since 2017-03-01
 */
public class InvalidDataException extends Exception {

	private static final long serialVersionUID = -3388691834518234285L;

	/**
	 * Default constructor
	 */
	public InvalidDataException() {
		super();
	}

	/**
	 * Constructor with the exception message
	 * 
	 * @param msg
	 *            exception message
	 */
	public InvalidDataException(final String msg) {
		super(msg);
	}
}
