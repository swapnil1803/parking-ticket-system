package com.parking.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.parking.dao.ParkingSpotDAO;
import com.parking.dao.exception.NoSuchParkingSpotFound;
import com.parking.dao.exception.NoVehicalOnParkingSpotFound;
import com.parking.service.ParkingSpotService;

/**
 * Test suite for {@link ParkingSpotService} class.
 * @author Swapnil Akolkar
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ParkingSpotServiceImplTest {

	@InjectMocks
	private ParkingSpotService parkingSpotService = new ParkingSpotServiceImpl();
	
	@Mock
	private ParkingSpotDAO parkingSpotDAO;

	/**
	 * Tests if DAO layer throws {@link NoSuchParkingSpotFound} exception 
	 * the method rethrows the same.
	 */
	@Test(expected=NoSuchParkingSpotFound.class)
	public void updateLicenseNumber_NoSuchParkingSpotFound() throws NoSuchParkingSpotFound, NoVehicalOnParkingSpotFound {
		Mockito.when(parkingSpotDAO.isParkingSpotAvailable(99)).thenReturn(false);
		Mockito.doThrow(NoSuchParkingSpotFound.class).when(parkingSpotDAO).updateLicenseNumber(99, "ABC123");
		parkingSpotService.updateLicenseNumber(99, "ABC123");
	}	
	
	/**
	 * Tests if DAO layer throws {@link NoVehicalOnParkingSpotFound} exception 
	 * the method rethrows the same.
	 */
	@Test(expected=NoVehicalOnParkingSpotFound.class)
	public void updateLicenseNumber_NoVehicalOnParkingSpotException() throws NoSuchParkingSpotFound, NoVehicalOnParkingSpotFound {
		Mockito.when(parkingSpotDAO.isParkingSpotAvailable(99)).thenReturn(true);
		Mockito.doNothing().when(parkingSpotDAO).updateLicenseNumber(99, "ABC123");
		parkingSpotService.updateLicenseNumber(99, "ABC123");
	}	
	
	/**
	 * Tests for valid input the method executes successfully.
	 */
	@Test
	public void updateLicenseNumber_Success() throws NoSuchParkingSpotFound, NoVehicalOnParkingSpotFound {
		Mockito.when(parkingSpotDAO.isParkingSpotAvailable(99)).thenReturn(false);
		Mockito.doNothing().when(parkingSpotDAO).updateLicenseNumber(99, "ABC123");
		parkingSpotService.updateLicenseNumber(99, "ABC123");
	}	
}