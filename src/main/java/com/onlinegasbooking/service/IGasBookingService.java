package com.onlinegasbooking.service;

import com.onlinegasbooking.entity.Customer;
import com.onlinegasbooking.entity.GasBooking;
import com.onlinegasbooking.exceptions.ResourceNotFoundException;

public interface IGasBookingService {
	
	public GasBooking insertGasBooking(GasBooking gasBooking, long customerId) throws ResourceNotFoundException;
	
	public GasBooking updateGasBooking(GasBooking gasBooking) throws ResourceNotFoundException;
	
	public GasBooking deleteGasBooking(long gasBookingId) throws ResourceNotFoundException;
	
	public float getBill(long customerId) throws ResourceNotFoundException;

}
