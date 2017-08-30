package com.parking.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.parking.dao.ParkingSpotDAO;
import com.parking.dao.exception.NoSuchParkingSpotFound;
import com.parking.dao.exception.NoVehicalOnParkingSpotFound;
import com.parking.models.ParkingSpot;
import com.parking.test.helper.TestDataHelper;

/**
 * Test suite for {@link ParkingSpotDAO}.
 * @author Swapnil Akolkar
 *
 */
public class ParkingSpotDAOImplTest {

	private ParkingSpotDAO parkingSpotDAO;
	
	/**
	 * Initializes all the required objects.
	 */
	@Before
	public void setup() {
		parkingSpotDAO = new ParkingSpotDAOImpl();
		ParkingSpotDAOImpl.parkingSpots = new HashMap<>();
	}
	
	/**
	 * Cleans up after execution.
	 */
	@After
	public void teardown() {
		parkingSpotDAO = null;
	}
	
	/**
	 * Tests if parking spot is added successfully for the valid input.
	 */
	@Test
	public void addParkingSpot() throws NoSuchParkingSpotFound {
		ParkingSpot parkingSpot = TestDataHelper.getMockParkingSpot(1, true, true);
		parkingSpotDAO.addParkingSpot(parkingSpot);
		assertEquals(parkingSpot, parkingSpotDAO.getDetails(parkingSpot.getParkingSpotNumber()));
	}
	
	/**
	 * Tests if parking spot is invalid, the method throws {@link NoSuchParkingSpotFound}.
	 */
	@Test(expected=NoSuchParkingSpotFound.class)
	public void updateLicenseNumber_NoSuchParkingException() throws NoSuchParkingSpotFound, NoVehicalOnParkingSpotFound {
		parkingSpotDAO.updateLicenseNumber(99, "ALD456");
	}
	
	/**
	 * Tests if parking spot is valid and parking spot is unavailable, the method throws {@link NoSuchParkingSpotFound}.
	 */
	@Test
	public void updateLicenseNumber_Success() throws NoSuchParkingSpotFound, NoVehicalOnParkingSpotFound {
		ParkingSpot parkingSpot = TestDataHelper.getMockParkingSpot(1, true, true);
		parkingSpotDAO.addParkingSpot(parkingSpot);
		parkingSpotDAO.updateLicenseNumber(parkingSpot.getParkingSpotNumber(), "ALD456");
		assertEquals("ALD456", parkingSpot.getLicenseNumber());
	}
	
	/**
	 * Tests if parking spot is not reserved and not available, the method returns -1.
	 */
	@Test
	public void getAvailableReservedSpotBothFalse() {
		ParkingSpot parkingSpot = TestDataHelper.getMockParkingSpot(1, false, false);
		parkingSpotDAO.addParkingSpot(parkingSpot);
		int parkingSpotNumber = parkingSpotDAO.getAvailableReservedSpot();
		assertEquals(-1, parkingSpotNumber);
	}
	
	/**
	 * Tests if parking spot is reserved and not available, the method returns -1.
	 */
	@Test
	public void getAvailableReservedSpotAvailTrueReservedFalse() {
		ParkingSpot parkingSpot = TestDataHelper.getMockParkingSpot(1, true, false);
		parkingSpotDAO.addParkingSpot(parkingSpot);
		int parkingSpotNumber = parkingSpotDAO.getAvailableReservedSpot();
		assertEquals(-1, parkingSpotNumber);
	}
	
	/**
	 * Tests if parking spot is reserved and available, the method returns parking spot number.
	 */
	@Test
	public void getAvailableReservedSpotBothTrue() {
		ParkingSpot parkingSpot = TestDataHelper.getMockParkingSpot(1, true, true);
		parkingSpotDAO.addParkingSpot(parkingSpot);
		int parkingSpotNumber = parkingSpotDAO.getAvailableReservedSpot();
		assertEquals(1, parkingSpotNumber);
	}
	
	/**
	 * Tests if parking spot is not reserved and available, the method returns -1.
	 */
	@Test
	public void getAvailableReservedSpotAvailFalseReservedTrue() {
		ParkingSpot parkingSpot = TestDataHelper.getMockParkingSpot(1, false, true);
		parkingSpotDAO.addParkingSpot(parkingSpot);
		int parkingSpotNumber = parkingSpotDAO.getAvailableReservedSpot();
		assertEquals(-1, parkingSpotNumber);
	}
	
	/**
	 * Tests if parking spot is not reserved and not available, the method returns -1.
	 */
	@Test
	public void getAvailableNonReservedSpotBothFalse() {
		ParkingSpot parkingSpot = TestDataHelper.getMockParkingSpot(1, false, false);
		parkingSpotDAO.addParkingSpot(parkingSpot);
		int parkingSpotNumber = parkingSpotDAO.getAvailableNonReservedSpot();
		assertEquals(-1, parkingSpotNumber);
	}
	
	/**
	 * Tests if parking spot is not reserved and available, the method returns -1.
	 */
	@Test
	public void getAvailableNonReservedSpotAvailTrueReservedFalse() {
		ParkingSpot parkingSpot = TestDataHelper.getMockParkingSpot(1, true, false);
		parkingSpotDAO.addParkingSpot(parkingSpot);
		int parkingSpotNumber = parkingSpotDAO.getAvailableNonReservedSpot();
		assertEquals(-1, parkingSpotNumber);
	}
	
	/**
	 * Tests if parking spot is reserved and available, the method returns -1.
	 */
	@Test
	public void getAvailableNonReservedSpotBothTrue() {
		ParkingSpot parkingSpot = TestDataHelper.getMockParkingSpot(1, true, true);
		parkingSpotDAO.addParkingSpot(parkingSpot);
		int parkingSpotNumber = parkingSpotDAO.getAvailableNonReservedSpot();
		assertEquals(-1, parkingSpotNumber);
	}
		
	/**
	 * Tests if parking spot is not reserved and available, the method returns parking spot number.
	 */
	@Test
	public void getAvailableNonReservedSpotAvailFalseReservedTrue() {
		ParkingSpot parkingSpot = TestDataHelper.getMockParkingSpot(1, false, true);
		parkingSpotDAO.addParkingSpot(parkingSpot);
		int parkingSpotNumber = parkingSpotDAO.getAvailableNonReservedSpot();
		assertEquals(1, parkingSpotNumber);
	}
	
	/**
	 * Tests if we try to mark non-existent parking spot as available, the method throws
	 * {@link NoSuchParkingSpotFound}.
	 */
	@Test(expected=NoSuchParkingSpotFound.class)
	public void markSpotAsAvailable_NoSuchParkingSpotFound() throws NoSuchParkingSpotFound {
		parkingSpotDAO.markSpotAsAvailable(99);
	}
	
	/**
	 * Tests if we try to mark existing parking spot as available, the method available attribute is set to true.
	 */
	@Test
	public void markSpotAsAvailable_Success() throws NoSuchParkingSpotFound {
		ParkingSpot parkingSpot = TestDataHelper.getMockParkingSpot(1, false, false);
		parkingSpotDAO.addParkingSpot(parkingSpot);
		parkingSpotDAO.markSpotAsAvailable(1);
		assertTrue(parkingSpot.isAvailable());
	}
	
	/**
	 * Tests if we try to mark non-existent parking spot as available, the method throws
	 * {@link NoSuchParkingSpotFound}.
	 */
	@Test(expected=NoSuchParkingSpotFound.class)
	public void markSpotAsUnAvailable_NoSuchParkingSpotFound() throws NoSuchParkingSpotFound {
		parkingSpotDAO.markSpotAsUnAvailable(99);
	}
	
	/**
	 * Tests if we try to mark existing parking spot as un-available, the method available attribute is set to false.
	 */
	@Test
	public void markSpotAsUnAvailable_Success() throws NoSuchParkingSpotFound {
		ParkingSpot parkingSpot = TestDataHelper.getMockParkingSpot(1, false, true);
		parkingSpotDAO.addParkingSpot(parkingSpot);
		parkingSpotDAO.markSpotAsUnAvailable(1);
		assertFalse(parkingSpot.isAvailable());
	}
	
	/**
	 * Tests if we try to get a non-existent parking spot, the method throws
	 * {@link NoSuchParkingSpotFound}.
	 */
	@Test(expected=NoSuchParkingSpotFound.class)
	public void getDetails_NoSuchParkingSpotFound() throws NoSuchParkingSpotFound {
		parkingSpotDAO.getDetails(99);
	}
	
	
	/**
	 * Tests if we try to get an existing parking spot, the method returns the data successfully.
	 */
	@Test
	public void getDetails_Success() throws NoSuchParkingSpotFound {
		ParkingSpot parkingSpot = TestDataHelper.getMockParkingSpot(1, false, true);
		parkingSpotDAO.addParkingSpot(parkingSpot);
		assertEquals(parkingSpot, parkingSpotDAO.getDetails(1));
	}
	
	/**
	 * Tests if we try to check availability of a non-existent parking spot, the method throws
	 * {@link NoSuchParkingSpotFound}.
	 */
	@Test(expected=NoSuchParkingSpotFound.class)
	public void isParkingSpotAvailable_NoSuchParkingSpotFound() throws NoSuchParkingSpotFound {
		parkingSpotDAO.isParkingSpotAvailable(99);
	}
	
	/**
	 * Tests if we try to check availability of an available parking spot, the method returns true.
	 */
	@Test
	public void isParkingSpotAvailable_True() throws NoSuchParkingSpotFound {
		ParkingSpot parkingSpot = TestDataHelper.getMockParkingSpot(1, false, true);
		parkingSpotDAO.addParkingSpot(parkingSpot);
		assertTrue(parkingSpotDAO.isParkingSpotAvailable(parkingSpot.getParkingSpotNumber()));
	}
	
	/**
	 * Tests if we try to check availability of an un-available parking spot, the method returns false.
	 */
	@Test
	public void isParkingSpotAvailable_False() throws NoSuchParkingSpotFound {
		ParkingSpot parkingSpot = TestDataHelper.getMockParkingSpot(1, false, false);
		parkingSpotDAO.addParkingSpot(parkingSpot);
		assertFalse(parkingSpotDAO.isParkingSpotAvailable(parkingSpot.getParkingSpotNumber()));
	}
	
	/**
	 * Tests if we try to check existence of a non-existent parking spot, the method throws
	 * {@link NoSuchParkingSpotFound}.
	 */
	@Test(expected=NoSuchParkingSpotFound.class)
	public void exists_NoSuchParkingSpotFound() throws NoSuchParkingSpotFound {
		assertFalse(((ParkingSpotDAOImpl)parkingSpotDAO).exists(99));
	}
	
	
	/**
	 * Tests if we try to check existence of an existing parking spot, the method returns true.
	 */
	@Test
	public void exists_True() throws NoSuchParkingSpotFound {
		ParkingSpot parkingSpot = TestDataHelper.getMockParkingSpot(1, false, false);
		parkingSpotDAO.addParkingSpot(parkingSpot);
		assertTrue(((ParkingSpotDAOImpl)parkingSpotDAO).exists(1));
	}
	
	/**
	 * Tests if no parking spots exists, the method returns empty list.
	 */
	@Test
	public void getAllParkingSpots_EmptyList() throws NoSuchParkingSpotFound {
		assertTrue(parkingSpotDAO.getAllParkingSpots().isEmpty());
	}
		
	/**
	 * Tests if no parking spots exists, the method returns non-empty list.
	 */
	@Test
	public void getAllParkingSpots_NonEmptyList() throws NoSuchParkingSpotFound {
		ParkingSpot parkingSpot = TestDataHelper.getMockParkingSpot(1, false, true);
		parkingSpotDAO.addParkingSpot(parkingSpot);
		assertFalse(parkingSpotDAO.getAllParkingSpots().isEmpty());
	}
}