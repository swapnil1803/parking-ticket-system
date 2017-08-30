package com.parking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parking.dao.ParkingSpotDAO;
import com.parking.dao.exception.NoSuchParkingSpotFound;
import com.parking.models.ParkingSpot;
import com.parking.service.ParkingSpotService;

/**
 * Service implementation of {@link ParkingSpotService}.
 * @author Swapnil Akolkar
 *
 */
@Service("parkingSpotService")
public class ParkingSpotServiceImpl implements ParkingSpotService {

	@Autowired
	private ParkingSpotDAO parkingSpotDAO;

	@Override
	public void addParkingSpot(ParkingSpot parkingSpot) {
		parkingSpotDAO.addParkingSpot(parkingSpot);
	}

	@Override
	public int getAvailableReservedSpot() {
		return parkingSpotDAO.getAvailableReservedSpot();
	}

	@Override
	public int getAvailableNonReservedSpot() {
		return parkingSpotDAO.getAvailableNonReservedSpot();
	}

	@Override
	public void markSpotAsAvailable(int parkingSpotNumber) throws NoSuchParkingSpotFound {
		parkingSpotDAO.markSpotAsAvailable(parkingSpotNumber);

	}

	@Override
	public void markSpotAsUnAvailable(int parkingSpotNumber) throws NoSuchParkingSpotFound {
		parkingSpotDAO.markSpotAsUnAvailable(parkingSpotNumber);

	}

	@Override
	public void updateLicenseNumber(int parkingSpotNumber, String licenseNumber)
			throws NoSuchParkingSpotFound, com.parking.dao.exception.NoVehicalOnParkingSpotFound {
		if (parkingSpotDAO.isParkingSpotAvailable(parkingSpotNumber)) {
			throw new com.parking.dao.exception.NoVehicalOnParkingSpotFound("There is no vehical at parking spot#" + parkingSpotNumber);
		}
		parkingSpotDAO.updateLicenseNumber(parkingSpotNumber, licenseNumber);
	}

	@Override
	public ParkingSpot getDetails(int parkingSpotNumber) throws NoSuchParkingSpotFound {
		return parkingSpotDAO.getDetails(parkingSpotNumber);
	}

	@Override
	public List<ParkingSpot> getAllParkingSpots() {
		return parkingSpotDAO.getAllParkingSpots();
	}
}