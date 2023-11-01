package com.onlinegasbooking.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinegasbooking.entity.Customer;
import com.onlinegasbooking.entity.Cylinder;
import com.onlinegasbooking.entity.GasBooking;
import com.onlinegasbooking.entity.SurrenderCylinder;
import com.onlinegasbooking.exceptions.ResourceNotFoundException;
import com.onlinegasbooking.repository.ICustomerRepository;
import com.onlinegasbooking.repository.ICylinderRepository;
import com.onlinegasbooking.repository.ISurrenderCylinderRepository;
import com.onlinegasbooking.repository.ISurrenderCylinderRepository;
import com.onlinegasbooking.service.IGasBookingService;
import com.onlinegasbooking.service.ISurrenderCylinderService;

@Service
public class SurrenderCylinderServiceImpl implements  ISurrenderCylinderService{

		@Autowired
		private ISurrenderCylinderRepository scylinderRepository;
		
		@Autowired
		private ICustomerRepository customerRepository;
		
		@Autowired
		private ICylinderRepository cylinderRepository;
		
		final static Logger logger = org.slf4j.LoggerFactory.getLogger("GasBookingApplication.class");

		@Override
		public SurrenderCylinder insertSurrenderCylinder(SurrenderCylinder sc,long customerId,long cylinderId) throws ResourceNotFoundException {
			
			Customer customer = customerRepository.findById(customerId)
			.orElseThrow(() ->new ResourceNotFoundException("Cylinder not found by id :"+sc.getSurrenderId()));
			
			
			  Cylinder cylinder = cylinderRepository.findById(cylinderId)
					.orElseThrow(() ->new ResourceNotFoundException("Cylinder not found by id :"+sc.getSurrenderId()));
			  
			
			sc.setCustomer(customer);
			sc.setCylinder(cylinder);
			sc.setSurrenderId(sc.getSurrenderId());
			sc.setSurrenderDate(LocalDate.now());
			
			return scylinderRepository.save(sc);
		}

		@Override
		public void updateSurrenderCylinder(SurrenderCylinder sc) throws ResourceNotFoundException {
			SurrenderCylinder su1 = scylinderRepository.findById(sc.getSurrenderId())
					.orElseThrow(() ->new ResourceNotFoundException("Cylinder not found by id :"+sc.getSurrenderId()));
			su1.setCustomer(sc.getCustomer());
			su1.setCylinder(sc.getCylinder());
			su1.setSurrenderId(sc.getSurrenderId());
			su1.setSurrenderDate(sc.getSurrenderDate());
			scylinderRepository.save(su1);
			logger.info("SurrenderCylinder details successfully updated with surrendercylinderID:"+sc.getSurrenderId());
		}

		@Override
		public void deleteSurrenderCylinder(SurrenderCylinder sc) throws ResourceNotFoundException {
			SurrenderCylinder su2 = scylinderRepository.findById(sc.getSurrenderId())
					.orElseThrow(() ->new ResourceNotFoundException("Cylinder not found by id :"+sc.getSurrenderId()));
			scylinderRepository.delete(su2);
			logger.info("SurrenderCylinder details successfully deleted with surrendercylinderID:"+sc.getSurrenderId());
			
		}

		@Override
		public long countSurrendredCylinders() {
//			List<Long> distinctSurrenderCylinders = scylinderRepository.findAll().stream().map(t -> t.getSurrenderId())
//			.distinct().collect(Collectors.toList());
			
			logger.info("Counted no of SurrendredCylinders");
			return  scylinderRepository.findAll().size();
		} 
	
}
