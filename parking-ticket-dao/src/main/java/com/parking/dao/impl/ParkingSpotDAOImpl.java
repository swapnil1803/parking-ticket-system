package com.parking.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.parking.dao.ParkingSpotDAO;
import com.parking.dao.exception.NoSuchParkingSpotFound;
import com.parking.models.ParkingSpot;

/**
 * Data layer implementation of {@link ParkingSpot}.
 * @author Swapnil Akolkar
 *
 */
@Repository("parkingSpotDAO")
public class ParkingSpotDAOImpl implements ParkingSpotDAO{

	static Map<Integer,ParkingSpot> parkingSpots = new HashMap<>();
	
	@Override
	public int getAvailableReservedSpot() {
		int parkingSpot = -1;
		for(ParkingSpot spot: parkingSpots.values()) {
			if (spot.isReserved() && spot.isAvailable()) {
				parkingSpot = spot.getParkingSpotNumber();
				break;
			}
		}
		return parkingSpot;
	}

	@Override
	public int getAvailableNonReservedSpot() {
		int parkingSpot = -1;
		for(ParkingSpot spot: parkingSpots.values()) {
			if (!spot.isReserved() && spot.isAvailable()) {
				parkingSpot = spot.getParkingSpotNumber();
				break;
			}
		}
		return parkingSpot;
	}

	@Override
	public void addParkingSpot(ParkingSpot parkingSpot) {
		parkingSpots.put(parkingSpot.getParkingSpotNumber(), parkingSpot);
	}

	@Override
	public void markSpotAsAvailable(int parkingSpotNumber) throws NoSuchParkingSpotFound {
		if (exists(parkingSpotNumber)) {
			ParkingSpot parkingSpot = parkingSpots.get(parkingSpotNumber);
			parkingSpot.setAvailable(true);
			parkingSpots.put(parkingSpotNumber, parkingSpot);
		}
		
	}
	
	@Override
	public void updateLicenseNumber(int parkingSpotNumber, String licenseNumber) throws NoSuchParkingSpotFound {
		if (exists(parkingSpotNumber)) {
			ParkingSpot parkingSpot = parkingSpots.get(parkingSpotNumber);
			parkingSpot.setLicenseNumber(licenseNumber);
			parkingSpots.put(parkingSpotNumber, parkingSpot);
		}
	}

	@Override
	public ParkingSpot getDetails(int parkingSpotNumber) throws NoSuchParkingSpotFound {
		if (exists(parkingSpotNumber)) {
			ParkingSpot parkingSpot = parkingSpots.get(parkingSpotNumber);
			return parkingSpot;
		}
		return null;
	}

	@Override
	public void markSpotAsUnAvailable(int parkingSpotNumber) throws NoSuchParkingSpotFound {
		if (exists(parkingSpotNumber)) {
			ParkingSpot parkingSpot = parkingSpots.get(parkingSpotNumber);
			parkingSpot.setAvailable(false);
			parkingSpots.put(parkingSpotNumber, parkingSpot);
		}		
	}

	@Override
	public boolean isParkingSpotAvailable(int parkingSpotNumber) throws NoSuchParkingSpotFound {
		return getDetails(parkingSpotNumber).isAvailable();
	}

	@Override
	public List<ParkingSpot> getAllParkingSpots() {
		return new ArrayList<>(parkingSpots.values());
	}
	
	/**
	 * Returns true if the parkingSpotNumber exists in the database; otherwise throws {@link NoSuchParkingSpotFound}.
	 * @param parkingSpotNumber to be looked up
	 * @return true if the parkingSpotNumber is found
	 * @throws NoSuchParkingSpotFound if parkingSpotNumber is not found
	 */
	boolean exists(int parkingSpotNumber) throws NoSuchParkingSpotFound {
		if (parkingSpots.containsKey(parkingSpotNumber)) {
			return true;
		}
		throw new NoSuchParkingSpotFound("Cannot find the parking spot # " + parkingSpotNumber);
	}
}