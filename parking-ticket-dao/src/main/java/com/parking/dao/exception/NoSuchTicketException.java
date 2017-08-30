package com.parking.dao.exception;

/**
 * Exception thrown if the ticket number is not found.
 * @author Swapnil Akolkar
 *
 */
public class NoSuchTicketException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * @param message information about the exception
	 */
	public NoSuchTicketException(String message) {
		super(message);
	}
}
