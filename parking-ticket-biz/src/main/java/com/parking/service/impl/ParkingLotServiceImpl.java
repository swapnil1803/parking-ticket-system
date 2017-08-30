package com.parking.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parking.dao.ParkingLotDAO;
import com.parking.models.ParkingLot;
import com.parking.service.ParkingLotService;

/**
 * Service implementation of {@link ParkingLotService}.
 * @author Swapnil Akolkar
 *
 */
@Service("parkingLotService")
public class ParkingLotServiceImpl implements ParkingLotService{

	@Autowired
	private ParkingLotDAO parkingDAO;
	
	@Override
	public void addOrUpdateParkingLot(ParkingLot parkingLot) {
		parkingDAO.addOrUpdateParkingLot(parkingLot);
	}

	@Override
	public ParkingLot getParkingLotDetails() {
		return parkingDAO.getParkingLotDetails();
	}

}