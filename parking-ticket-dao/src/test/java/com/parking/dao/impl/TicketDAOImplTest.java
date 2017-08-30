package com.parking.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.parking.dao.TicketDAO;
import com.parking.dao.exception.NoSuchParkingSpotFound;
import com.parking.dao.exception.NoSuchTicketException;
import com.parking.models.Ticket;
import com.parking.test.helper.TestDataHelper;

/**
 * Test suite for {@link TicketDAOImpl}.
 * @author Swapnil Akolkar
 *
 */
public class TicketDAOImplTest {

	private TicketDAO ticketDAO;
	private Ticket ticket;

	/**
	 * Initializes required objects.
	 */
	@Before
	public void setup() {
		ticketDAO = new TicketDAOImpl();
		TicketDAOImpl.ticketDetails = new HashMap<Integer, Ticket>();
	}

	/**
	 * Cleans up after execution.
	 */
	@After
	public void teardown() {
		ticketDAO = null;
	}

	/**
	 * Tests if the method successfully creates a new ticket.
	 */
	@Test
	public void testCreateNewTicket() throws NoSuchParkingSpotFound {
		int ticketNumber = ticketDAO.createNewTicket(TestDataHelper.getMockTicket());
		assertTrue(ticketNumber > 0);
	}

	/**
	 * Tests if the ticket number is invalid, the method throws {@link NoSuchTicketException}.
	 */
	@Test(expected = NoSuchTicketException.class)
	public void testGetTicketDetails_NoSuchTicketException() throws NoSuchTicketException {
		ticketDAO.getTicketDetails(99);
	}

	/**
	 * Tests if the ticket number is exists, the method returns the ticket details.
	 */
	@Test
	public void testGetTicketDetails_FoundTicket() throws NoSuchTicketException, NoSuchParkingSpotFound {
		int ticketNumber = ticketDAO.createNewTicket(TestDataHelper.getMockTicket());
		ticket = ticketDAO.getTicketDetails(ticketNumber);
		assertEquals(ticketNumber, ticket.getTicketNumber());
	}

	/**
	 * Tests if the ticket number is invalid, the method throws {@link NoSuchTicketException}.
	 */
	@Test(expected = NoSuchTicketException.class)
	public void testReleaseTicket_NoSuchTicketException() throws NoSuchTicketException {
		ticketDAO.releaseTicket(99);
	}
	
	/**
	 * Tests if we try to release a valid ticket, the method executes successfully.
	 */
	@Test
	public void testReleaseTicket_ReleaseTicket() throws NoSuchTicketException, NoSuchParkingSpotFound {
		int ticketNumber = ticketDAO.createNewTicket(TestDataHelper.getMockTicket());
		ticketDAO.releaseTicket(ticketNumber);
		try {
			ticketDAO.getTicketDetails(ticketNumber);
			fail();
		} catch (NoSuchTicketException exception) {
			assertTrue(true);
		}
	}

	/**
	 * Tests if we try to update a non-existent ticket, the method throws {@link NoSuchTicketException}.
	 */
	@Test(expected = NoSuchTicketException.class)
	public void testUpdateTicket_NoSuchTicketException() throws NoSuchTicketException {
		Ticket ticket = TestDataHelper.getMockTicket();
		ticket.setTicketNumber(99);
		ticketDAO.updateTicket(ticket);
	}

	/**
	 * Tests if we try to update a valid ticket, the method executes successfully.
	 */
	@Test
	public void testUpdateTicket_UpdateTicket() throws NoSuchTicketException, NoSuchParkingSpotFound {
		Ticket ticket = TestDataHelper.getMockTicket();
		int ticketNumber = ticketDAO.createNewTicket(ticket);
		ticket.setCustomerName("updatedname");
		ticketDAO.updateTicket(ticket);
		Ticket updatedTicket = ticketDAO.getTicketDetails(ticketNumber);
		assertEquals(ticket, updatedTicket);
	}

	/**
	 * Tests if the ticket does not exist, the method returns false.
	 */
	@Test
	public void testExist_False() throws NoSuchTicketException {
		assertFalse(((TicketDAOImpl) ticketDAO).exists(99));
	}

	/**
	 * Tests if the ticket exists, the method returns true.
	 */
	@Test
	public void testExists_True() throws NoSuchTicketException, NoSuchParkingSpotFound {
		Ticket ticket = TestDataHelper.getMockTicket();
		int ticketNumber = ticketDAO.createNewTicket(ticket);
		assertTrue(((TicketDAOImpl) ticketDAO).exists(ticketNumber));
	}
}