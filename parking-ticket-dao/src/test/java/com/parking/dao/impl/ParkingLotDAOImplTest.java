package com.parking.dao.impl;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.parking.dao.ParkingLotDAO;
import com.parking.models.ParkingLot;

/**
 * Test suite for {@link ParkingLotDAO}.
 * @author Swapnil Akolkar
 *
 */
public class ParkingLotDAOImplTest {

	private ParkingLotDAO parkingLotDAO;
	
	/**
	 * Initializes the required objects.
	 */
	@Before
	public void setup() {
		parkingLotDAO = new ParkingLotDAOImpl();
	}
	
	/**
	 * Cleans up after execution.
	 */
	@After
	public void teardown() {
		parkingLotDAO = null;
	}
	
	/**
	 * Tests if {@link ParkingLotDAO#addOrUpdateParkingLot(ParkingLot)} adds or updates parking lot
	 * information correctly.
	 */
	@Test
	public void testaddOrUpdateAndGetDetails() {
		ParkingLot parkingLot = new ParkingLot();
		parkingLot.setAddress("newAddress");
		parkingLot.setEmail("newemail@someprovider.com");
		parkingLot.setPhoneNumber(1235677890);
		parkingLot.setCostPerHr(3.5);
		
		parkingLotDAO.addOrUpdateParkingLot(parkingLot);
		
		assertEquals(parkingLot, parkingLotDAO.getParkingLotDetails());
	}
}