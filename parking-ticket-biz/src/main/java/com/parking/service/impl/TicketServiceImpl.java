package com.parking.service.impl;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parking.dao.TicketDAO;
import com.parking.dao.exception.NoSuchParkingSpotFound;
import com.parking.dao.exception.NoSuchTicketException;
import com.parking.dao.exception.NoVehicalOnParkingSpotFound;
import com.parking.models.Ticket;
import com.parking.service.ParkingLotService;
import com.parking.service.ParkingSpotService;
import com.parking.service.TicketService;

/**
 * Service implementation of {@link TicketService}.
 * @author Swapnil Akolkar
 *
 */
@Service("ticketService")
public class TicketServiceImpl implements TicketService{
	
	@Autowired
	private ParkingSpotService parkingSpotService;

	@Autowired
	private ParkingLotService parkingLotService;

	@Autowired
	private TicketDAO ticketDAO;
	
	@Override
	public int createNewTicket(Ticket ticket) throws NoSuchParkingSpotFound, NoVehicalOnParkingSpotFound {
		
		
		int parkingSpotNumber = -1;
		
		// Check if ticket needs reserved parking and get the parking spot
		if (ticket.isReserved()) {
			parkingSpotNumber = parkingSpotService.getAvailableReservedSpot();
		}
		
		// Check if there are any parking spot available (not reserved)
		if (parkingSpotNumber == -1)
		{
			parkingSpotNumber = parkingSpotService.getAvailableNonReservedSpot();
		}
		
		// If no parking spot exist, return an error
		if (parkingSpotNumber == -1) {
			throw new NoSuchParkingSpotFound("No parking spot are available at this time.");
		}
		
		// If parking spot exists, then set it to the ticket
		ticket.setParkingSpotNumber(parkingSpotNumber);

		parkingSpotService.markSpotAsUnAvailable(parkingSpotNumber);
		parkingSpotService.updateLicenseNumber(parkingSpotNumber, ticket.getLicenseNumber());
		
		int ticketNumber = ticketDAO.createNewTicket(ticket);
		
		return ticketNumber;
	}

	@Override
	public Ticket getTicketDetails(int ticketNumber) throws NoSuchTicketException {
		return ticketDAO.getTicketDetails(ticketNumber);
	}

	@Override
	public double releaseTicket(int ticketNumber) throws NoSuchTicketException, NoSuchParkingSpotFound {
		Ticket ticket = getTicketDetails(ticketNumber);
		parkingSpotService.markSpotAsAvailable(ticket.getParkingSpotNumber());
		long beginTime = ticket.getBeginTime();
		long endTime = Calendar.getInstance().getTimeInMillis();
		long hours = getHours(endTime-beginTime);
		hours = hours <= 0 ? 1 : hours;
		double costPerHr = parkingLotService.getParkingLotDetails().getCostPerHr();
		return (costPerHr*hours);
	}

	/**
	 * Converts the given milliseconds to number of hours.
	 * @param timeInMiliSeconds to be converted
	 * @return number of hours
	 */
	private long getHours(long timeInMiliSeconds) {
		return TimeUnit.MILLISECONDS.toHours(timeInMiliSeconds);
	}
	
	@Override
	public void updateTicket(Ticket ticket) throws NoSuchTicketException {
		ticketDAO.updateTicket(ticket);		
	}
}