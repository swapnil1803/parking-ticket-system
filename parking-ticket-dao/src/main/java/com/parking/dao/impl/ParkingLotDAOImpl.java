package com.parking.dao.impl;

import org.springframework.stereotype.Repository;

import com.parking.dao.ParkingLotDAO;
import com.parking.models.ParkingLot;

/**
 * Data layer implementation of {@link ParkingLotDAO}.
 * @author Swapnil Akolkar
 *
 */
@Repository("parkingLotDAO")
public class ParkingLotDAOImpl implements ParkingLotDAO{

	/*
	 * Represents a single record in database.
	 */
	static ParkingLot parkingLot = new ParkingLot();
	
	@Override
	public void addOrUpdateParkingLot(ParkingLot newParkingLot) {
		parkingLot.setAddress(newParkingLot.getAddress());
		parkingLot.setEmail(newParkingLot.getEmail());
		parkingLot.setPhoneNumber(newParkingLot.getPhoneNumber());
		parkingLot.setCostPerHr(newParkingLot.getCostPerHr());		
	}

	@Override
	public ParkingLot getParkingLotDetails() {
		return parkingLot;
	}
}