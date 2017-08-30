package com.parking.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.parking.dao.TicketDAO;
import com.parking.dao.exception.NoSuchTicketException;
import com.parking.models.Ticket;

/**
 * Data layer implementation of {@link TicketDAO}.
 * @author Swapnil Akolkar
 *
 */
@Repository("ticketDAO")
public class TicketDAOImpl implements TicketDAO{

	static Map<Integer,Ticket> ticketDetails = new HashMap<>();
	private static int nextId = 1;
	
	@Override
	public int createNewTicket(Ticket ticket) {
		int autogeneratedId = nextId++;
		ticket.setTicketNumber(autogeneratedId);
		ticketDetails.put(autogeneratedId, ticket);
		return autogeneratedId;
	}

	@Override
	public Ticket getTicketDetails(int ticketNumber) throws NoSuchTicketException {
		if (!exists(ticketNumber)) {
			throw new NoSuchTicketException("The ticket with number " + ticketNumber + " is not found.");
		}
		Ticket ticket = ticketDetails.get(ticketNumber);
		return ticket;
	}

	@Override
	public void releaseTicket(int ticketNumber) throws NoSuchTicketException {
		if (!exists(ticketNumber)) {
			throw new NoSuchTicketException("The ticket with number " + ticketNumber + " is not found.");
		}
		/* In the real world, we may not perform hard delete from the database instead we would have
		 * perform a soft delete using an additional column in the table like 'active'.*/
		ticketDetails.remove(ticketNumber);
	}

	@Override
	public void updateTicket(Ticket ticket) throws NoSuchTicketException {
		if (!exists(ticket.getTicketNumber())) {
			throw new NoSuchTicketException("The ticket with number " + ticket.getTicketNumber() + " is not found.");
		}
		ticketDetails.put(ticket.getTicketNumber(), ticket);
	}
	
	boolean exists(int ticketNumber) {
		return ticketDetails.containsKey(ticketNumber);
	}
}