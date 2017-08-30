package com.parking.dao.exception;

/**
 * Exception thrown if the parking spot has no vehicle and if we are updating its license number.
 * @author Swapnil Akolkar
 *
 */
public class NoVehicalOnParkingSpotFound extends Exception {

	private static final long serialVersionUID = 11L;
	
	/**
	 * Constructor.
	 * @param message information about the exception
	 */
	public NoVehicalOnParkingSpotFound(String message) {
		super(message);
	}

}
