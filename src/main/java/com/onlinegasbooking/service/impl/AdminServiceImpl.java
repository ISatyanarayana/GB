package com.onlinegasbooking.service.impl;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinegasbooking.entity.Admin;
import com.onlinegasbooking.entity.Customer;
import com.onlinegasbooking.entity.GasBooking;
import com.onlinegasbooking.exceptions.AdminAlreadyExistsException;
import com.onlinegasbooking.exceptions.ResourceNotFoundException;
import com.onlinegasbooking.repository.IAdminRepository;
import com.onlinegasbooking.repository.ICustomerRepository;
import com.onlinegasbooking.service.IAdminService;

@Service
public  class AdminServiceImpl implements IAdminService {

	@Autowired
	private IAdminRepository adminRepository;
	
	private ICustomerRepository customerRepository;
	
	@Override
	public Admin insertAdmin(Admin admin) throws AdminAlreadyExistsException {

		Admin save=null;
		Optional<?> a1=adminRepository.findByName(admin.getUserName());
		if(a1.isPresent())
		{
			throw new AdminAlreadyExistsException("LOL");
		}
		else
			 return save = adminRepository.save(admin);
	}

	@Override
	public Admin updateAdmin(Admin admin) throws ResourceNotFoundException {
		Admin a2 = adminRepository.findById(admin.getUserId())
		.orElseThrow(() -> new ResourceNotFoundException("Admin not found with user id:"+admin.getUserId()));
		
		a2.setAddress(admin.getAddress());
		a2.setAdminName(admin.getAdminName());
		a2.setEmail(admin.getEmail());
		a2.setMobileNumber(admin.getMobileNumber());
		a2.setPassword(admin.getPassword());
		a2.setUserId(admin.getUserId());
		a2.setUserName(admin.getAdminName());
		
		adminRepository.save(a2);
		return  a2;
	}	

	@Override
	public Admin deleteAdmin(long adminId) throws ResourceNotFoundException {
		Admin a3 = adminRepository.findById(adminId)
				.orElseThrow(() -> new ResourceNotFoundException("Admin not found with user id:"+adminId));
		adminRepository.delete(a3);
		
		return a3;
	}

	@Override
	public List<GasBooking> getAllBookings(long customerId) throws ResourceNotFoundException {
		
		Customer c = customerRepository.findById(customerId)
		.orElseThrow(() -> new ResourceNotFoundException("GasBooking not found for the id :"+customerId));
		
		return c.getBookings();
	}

	@Override
	public List<GasBooking> getAllBookingsForDays(long customerId, LocalDateTime fromDate, LocalDateTime toDate)
			throws ResourceNotFoundException, ParseException {
		
		Customer cust = customerRepository.findById(customerId)
			.orElseThrow(() -> new ResourceNotFoundException("Customer not found by id:"+customerId));
			
		List<GasBooking> bookings = cust.getBookings();
		List<GasBooking> list = new ArrayList<GasBooking>();
		
		for(GasBooking g : bookings)
		{
			SimpleDateFormat sd=new SimpleDateFormat("MM/dd/yyyy");
			
			Date fd=sd.parse(fromDate.toString());
			Date td=sd.parse(toDate.toString());
			
			Date rdate=sd.parse(g.getBookingDate().toString());
			
			if(rdate.compareTo(fd)>=0 && rdate.compareTo(td)<=0)
			{
				list.add(g);
			}
		}
		return list;
		
	}

	

}
