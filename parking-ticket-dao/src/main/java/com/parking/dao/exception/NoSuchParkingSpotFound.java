package com.parking.dao.exception;

/**
 * Exception thrown if the parking spot number is not found.
 * @author Swapnil Akolkar
 *
 */
public class NoSuchParkingSpotFound extends Exception{

	private static final long serialVersionUID = 10L;

	/**
	 * Constructor.
	 * @param message information about the exception
	 */
	public NoSuchParkingSpotFound(String message) {
		super(message);
	}
}