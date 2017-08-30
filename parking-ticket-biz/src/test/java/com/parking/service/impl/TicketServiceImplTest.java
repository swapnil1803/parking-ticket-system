package com.parking.service.impl;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.parking.dao.TicketDAO;
import com.parking.dao.exception.NoSuchParkingSpotFound;
import com.parking.dao.exception.NoSuchTicketException;
import com.parking.dao.exception.NoVehicalOnParkingSpotFound;
import com.parking.models.ParkingLot;
import com.parking.models.Ticket;
import com.parking.service.ParkingLotService;
import com.parking.service.ParkingSpotService;
import com.parking.test.helper.TestDataHelper;

/**
 * Test suite for {@link TicketServiceImpl}.
 * @author Swapnil Akolkar
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class TicketServiceImplTest {
	
	@InjectMocks
	private TicketServiceImpl ticketService = new TicketServiceImpl();
	
	@Mock
	private TicketDAO ticketDAO;
	
	@Mock
	private ParkingSpotService parkingSpotService;
	
	@Mock
	private ParkingLotService parkingLotService;

	/**
	 * Tests if parkingSpotNumber is does not exists, the method throws {@link NoSuchParkingSpotFound}
	 * exception.
	 */
	@Test(expected=NoSuchParkingSpotFound.class)
	public void createNewTicket_NoSpotsFound() throws NoSuchParkingSpotFound, NoVehicalOnParkingSpotFound {
		Mockito.when(parkingSpotService.getAvailableReservedSpot()).thenReturn(-1);
		Mockito.when(parkingSpotService.getAvailableNonReservedSpot()).thenReturn(-1);
		Ticket ticket = TestDataHelper.getMockTicket();
		ticket.setReserved(true);
		ticketService.createNewTicket(ticket);
	}
	
	/**
	 * Tests if no non-reserved parking spots exists, the method throws {@link NoSuchParkingSpotFound}
	 * exception.
	 */
	@Test(expected=NoSuchParkingSpotFound.class)
	public void createNewTicket_NoNonReservedSpotsFound() throws NoSuchParkingSpotFound, NoVehicalOnParkingSpotFound {
		Mockito.when(parkingSpotService.getAvailableNonReservedSpot()).thenReturn(-1);
		Ticket ticket = TestDataHelper.getMockTicket();
		ticket.setReserved(false);
		ticketService.createNewTicket(ticket);
	}
	
	/**
	 * Tests if updating license fails for invalid parking spot number, the method throws {@link NoSuchParkingSpotFound}
	 */
	@Test(expected=NoSuchParkingSpotFound.class)
	public void createNewTicket_UpdateLicenseSpotNotFound() throws NoSuchParkingSpotFound, NoVehicalOnParkingSpotFound {
		Mockito.when(parkingSpotService.getAvailableReservedSpot()).thenReturn(1);
		Mockito.doThrow(NoSuchParkingSpotFound.class).when(parkingSpotService).updateLicenseNumber(Matchers.anyInt(), Matchers.anyString());
		Ticket ticket = TestDataHelper.getMockTicket();
		ticket.setReserved(true);
		ticketService.createNewTicket(ticket);
	}
	
	/**
	 * Tests if updating license for an available spot, the method throws {@link NoVehicalOnParkingSpotFound}.
	 */
	@Test(expected=NoVehicalOnParkingSpotFound.class)
	public void createNewTicket_UpdateLicenseNoVehicalOnParkingSpotException() throws NoSuchParkingSpotFound, NoVehicalOnParkingSpotFound {
		Mockito.when(parkingSpotService.getAvailableReservedSpot()).thenReturn(1);
		Mockito.doThrow(NoVehicalOnParkingSpotFound.class).when(parkingSpotService).updateLicenseNumber(Matchers.anyInt(), Matchers.anyString());
		Ticket ticket = TestDataHelper.getMockTicket();
		ticket.setReserved(true);
		ticketService.createNewTicket(ticket);
	}
	
	/**
	 * Tests if we try to mark a non-existent spot as unavailable, the method throws {@link NoSuchParkingSpotFound}.
	 */
	@Test(expected=NoSuchParkingSpotFound.class)
	public void createNewTicket_markSpotAsUnAvailableSpotNotFound() throws NoSuchParkingSpotFound, NoVehicalOnParkingSpotFound {
		Mockito.when(parkingSpotService.getAvailableReservedSpot()).thenReturn(1);
		Mockito.doNothing().when(parkingSpotService).updateLicenseNumber(Matchers.anyInt(), Matchers.anyString());
		Mockito.doThrow(NoSuchParkingSpotFound.class).when(parkingSpotService).markSpotAsUnAvailable(1);
		Ticket ticket = TestDataHelper.getMockTicket();
		ticket.setReserved(true);
		ticketService.createNewTicket(ticket);
	}
	
	/**
	 * Tests if we need reserved spot and its available, the ticket is created successfully.
	 */
	@Test
	public void createNewTicket_ReservedAvailSuccess() throws NoSuchParkingSpotFound, NoVehicalOnParkingSpotFound {
		Ticket ticket = TestDataHelper.getMockTicket();
		ticket.setReserved(true);
		Mockito.when(parkingSpotService.getAvailableReservedSpot()).thenReturn(1);
		Mockito.doNothing().when(parkingSpotService).updateLicenseNumber(Matchers.anyInt(), Matchers.anyString());
		Mockito.doNothing().when(parkingSpotService).markSpotAsUnAvailable(1);
		Mockito.when(ticketDAO.createNewTicket(ticket)).thenReturn(1);
		int ticketNumber = ticketService.createNewTicket(ticket);
		assertTrue(ticketNumber > 0);
	}
	
	/**
	 * Tests if we need reserved spot (which is not available) but non-reserved is available, the ticket is created successfully.
	 */
	@Test
	public void createNewTicket_ReservedNotAvailSuccess() throws NoSuchParkingSpotFound, NoVehicalOnParkingSpotFound {
		Ticket ticket = TestDataHelper.getMockTicket();
		ticket.setReserved(true);
		Mockito.when(parkingSpotService.getAvailableReservedSpot()).thenReturn(-1);
		Mockito.when(parkingSpotService.getAvailableNonReservedSpot()).thenReturn(1);
		Mockito.doNothing().when(parkingSpotService).updateLicenseNumber(Matchers.anyInt(), Matchers.anyString());
		Mockito.doNothing().when(parkingSpotService).markSpotAsUnAvailable(1);
		Mockito.when(ticketDAO.createNewTicket(ticket)).thenReturn(1);
		int ticketNumber = ticketService.createNewTicket(ticket);
		assertTrue(ticketNumber > 0);
	}
	
	/**
	 * Tests if we need non-reserved spot and its available, the ticket is created successfully.
	 */
	@Test
	public void createNewTicket_NonReservedAvailSuccess() throws NoSuchParkingSpotFound, NoVehicalOnParkingSpotFound {
		Ticket ticket = TestDataHelper.getMockTicket();
		ticket.setReserved(false);
		Mockito.when(parkingSpotService.getAvailableNonReservedSpot()).thenReturn(1);
		Mockito.doNothing().when(parkingSpotService).updateLicenseNumber(Matchers.anyInt(), Matchers.anyString());
		Mockito.doNothing().when(parkingSpotService).markSpotAsUnAvailable(1);
		Mockito.when(ticketDAO.createNewTicket(ticket)).thenReturn(1);
		int ticketNumber = ticketService.createNewTicket(ticket);
		assertTrue(ticketNumber > 0);
	}
	
	/**
	 * Tests if we try to release a non-existent ticket, the method throws {@link NoSuchTicketException}.
	 */
	@Test(expected=NoSuchTicketException.class)
	public void releaseTicket_NoSuchTicketException() throws NoSuchTicketException, NoSuchParkingSpotFound {
		Mockito.when(ticketDAO.getTicketDetails(1)).thenThrow(new NoSuchTicketException("No ticket found"));
		ticketService.releaseTicket(1);
	}
	
	/**
	 * Tests if we try to release a ticket with non-existent parking spot, the method throws {@link NoSuchParkingSpotFound}.
	 */
	@Test(expected=NoSuchParkingSpotFound.class)
	public void releaseTicket_NoSuchParkingSpotFound() throws NoSuchTicketException, NoSuchParkingSpotFound {
		Ticket ticket = TestDataHelper.getMockTicket();
		Mockito.when(ticketDAO.getTicketDetails(1)).thenReturn(ticket);
		Mockito.doThrow(NoSuchParkingSpotFound.class).when(parkingSpotService).markSpotAsAvailable(Matchers.anyInt());
		ticketService.releaseTicket(1);
	}
	
	/**
	 * Tests if the vehicle is parked for less than 1 hour, the amount is returned for 1 hour. 
	 */
	@Test
	public void releaseTicket_ZeroHours() throws NoSuchTicketException, NoSuchParkingSpotFound {
		ParkingLot parkingLot = new ParkingLot();
		parkingLot.setCostPerHr(2);
		Ticket ticket = TestDataHelper.getMockTicket(); 
		ticket.setBeginTime(Calendar.getInstance().getTimeInMillis());
		Mockito.when(ticketDAO.getTicketDetails(ticket.getTicketNumber())).thenReturn(ticket);
		Mockito.doNothing().when(parkingSpotService).markSpotAsAvailable(Matchers.anyInt());
		Mockito.when(parkingLotService.getParkingLotDetails()).thenReturn(parkingLot);
		double amount = ticketService.releaseTicket(ticket.getParkingSpotNumber());
		assertTrue(2.0 == amount);
	}
	
	/**
	 * Tests if the hour calculation returns negative hours, the amount for 1hour is charged.
	 */
	@Test
	public void releaseTicket_NegativeHours() throws NoSuchTicketException, NoSuchParkingSpotFound {
		ParkingLot parkingLot = new ParkingLot();
		parkingLot.setCostPerHr(2);
		Ticket ticket = TestDataHelper.getMockTicket(); 
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, 2);
		ticket.setBeginTime(calendar.getTimeInMillis());
		Mockito.when(ticketDAO.getTicketDetails(ticket.getTicketNumber())).thenReturn(ticket);
		Mockito.doNothing().when(parkingSpotService).markSpotAsAvailable(Matchers.anyInt());
		Mockito.when(parkingLotService.getParkingLotDetails()).thenReturn(parkingLot);
		double amount = ticketService.releaseTicket(ticket.getParkingSpotNumber());
		assertTrue(2.0 == amount);
	}
	
	/**
	 * Tests if the vehicle is parked for more than 1hour (2hours), the amount for 2hour is charged.
	 */
	@Test
	public void releaseTicket_MoreThanZeroHours() throws NoSuchTicketException, NoSuchParkingSpotFound {
		ParkingLot parkingLot = new ParkingLot();
		parkingLot.setCostPerHr(2);
		Ticket ticket = TestDataHelper.getMockTicket(); 
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, -2);
		ticket.setBeginTime(calendar.getTimeInMillis());
		Mockito.when(ticketDAO.getTicketDetails(ticket.getTicketNumber())).thenReturn(ticket);
		Mockito.doNothing().when(parkingSpotService).markSpotAsAvailable(Matchers.anyInt());
		Mockito.when(parkingLotService.getParkingLotDetails()).thenReturn(parkingLot);
		double amount = ticketService.releaseTicket(ticket.getParkingSpotNumber());
		assertTrue(4.0 == amount);
	}
}