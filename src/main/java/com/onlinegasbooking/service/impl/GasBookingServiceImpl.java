 package com.onlinegasbooking.service.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinegasbooking.entity.Customer;
import com.onlinegasbooking.entity.GasBooking;
import com.onlinegasbooking.exceptions.ResourceNotFoundException;
import com.onlinegasbooking.repository.ICustomerRepository;
import com.onlinegasbooking.repository.IGasBookingRepository;
import com.onlinegasbooking.service.IGasBookingService;

@Service
public class GasBookingServiceImpl implements IGasBookingService {

	private IGasBookingRepository bookingRepository;				//fieldInjection
	
	private ICustomerRepository customerRepository;
	
	@Autowired
	public GasBookingServiceImpl(IGasBookingRepository bookingRepository, ICustomerRepository customerRepository) //ConstructorInjection
	{
		this.bookingRepository=bookingRepository;
		this.customerRepository=customerRepository;
	}
	
	@Override
	public GasBooking insertGasBooking(GasBooking gasBooking, long customerId) throws ResourceNotFoundException {
		
		Customer cu= customerRepository.findById(customerId)
		.orElseThrow(() -> new ResourceNotFoundException("Customer not found by id :"+customerId));
		
		gasBooking.setCustomer(cu);
		gasBooking.setBookingDate(LocalDate.now());
		
		return bookingRepository.save(gasBooking);
	}

	
	public GasBooking updateGasBooking(GasBooking gasBooking)throws ResourceNotFoundException {
		
		GasBooking gb = bookingRepository.findById(gasBooking.getGasBookingId())
		.orElseThrow(() ->new ResourceNotFoundException("Customer not found by id:"+gasBooking.getGasBookingId()));
		
		gb.setBill(gasBooking.getBill());
		gb.setGasBookingId(gasBooking.getGasBookingId());
		gb.setStatus(gasBooking.isStatus());
		
		return gb;
	}

	@Override
	public GasBooking deleteGasBooking(long gasBookingId) throws ResourceNotFoundException {

		GasBooking gbd = bookingRepository.findById(gasBookingId)
		.orElseThrow(() ->new ResourceNotFoundException("Customer not found by id:"+gasBookingId));
		
		 bookingRepository.delete(gbd);
		 return gbd;
	}

	@Override
	public float getBill(long customerId) throws ResourceNotFoundException {
		
		Customer cu = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found by id :"+customerId));
		
		float bill=0;
		
			for(GasBooking gb: cu.getBookings())
			{
				bill+=gb.getBill();
			}
			return bill;
	}

	

}
