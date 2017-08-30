package com.parking.test.helper;

import com.parking.models.ParkingSpot;
import com.parking.models.Ticket;

/**
 * Test class to generate mock data for unit testing for Data layer.
 * @author Swapnil Akolkar
 *
 */
public class TestDataHelper {

	/**
	 * Returns a parking spot instance using the given parameters.
	 * @param parkingSpotNumber of the parking spot
	 * @param isReserved flag of parking spot
	 * @param isAvailable flag of parking spot
	 * @return parking spot instance
	 */
	public static ParkingSpot getMockParkingSpot( int parkingSpotNumber, boolean isReserved, boolean isAvailable ) {
		return new ParkingSpot(parkingSpotNumber, isReserved, isAvailable, "");
	}
	
	/**
	 * Returns a mock ticket instance.
	 * @return mock ticket instance
	 */
	public static Ticket getMockTicket() {
		Ticket ticket = new Ticket();
		ticket.setCustomerName("fname lname");
		ticket.setLicenseNumber("234LMN");
		ticket.setPhoneNumber(3214569956l);
		ticket.setReserved(false);
		return ticket;
	}
}
