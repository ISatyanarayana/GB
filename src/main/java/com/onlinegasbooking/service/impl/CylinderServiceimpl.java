package com.onlinegasbooking.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.hibernate.ResourceClosedException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import com.onlinegasbooking.entity.Cylinder;
import com.onlinegasbooking.exceptions.ResourceNotFoundException;
import com.onlinegasbooking.repository.ICylinderRepository;
import com.onlinegasbooking.service.ICylinderService;

@Service
public class CylinderServiceimpl implements ICylinderService {

	@Autowired
	private ICylinderRepository cylinderRepository;
	
	final static Logger logger = org.slf4j.LoggerFactory.getLogger("GasBookingApplication.class");
	
	@Override
	public Cylinder insertCylinder(Cylinder cylinder) {
		
		Cylinder cylinder1 = cylinderRepository.save(cylinder);
		return cylinder1;
	}

	@Override
	public Cylinder updateCylinder(Cylinder cylinder) throws ResourceNotFoundException {
		
		Cylinder cylinder2 = cylinderRepository.findById(cylinder.getCylinderId())
		.orElseThrow(() ->new ResourceNotFoundException("Cylinder doent exist with id:"+cylinder.getCylinderId()));
		
		cylinder2.setCylinderId(cylinder.getCylinderId());
		cylinder2.setPrice(cylinder.getPrice());
		cylinder2.setStrapColor(cylinder.getStrapColor());
		cylinder2.setType(cylinder.getType());
		cylinder2.setWeight(cylinder.getWeight());
		
		cylinderRepository.save(cylinder2);
		logger.info("Updated cylinder details for the cylinder id"+cylinder.getCylinderId());	
		return cylinder2;
	}

	@Override
	public Cylinder deleteCylinder(long cylinderId) throws ResourceNotFoundException {
		Cylinder cylinder3 = cylinderRepository.findById(cylinderId)
				.orElseThrow(() ->new ResourceNotFoundException("Cylinder doent exist with id:"+cylinderId));
		
		cylinderRepository.delete(cylinder3);
		logger.warn("Cylinder got deleted with id: "+cylinderId);
		return cylinder3;
	}

	@Override
	public List<Cylinder> viewCylindersByType(String type) throws ResourceNotFoundException {
		

		List<Cylinder> emplist = cylinderRepository.findAll().stream()
		        .filter(cy -> cy.getType().equalsIgnoreCase(type))
		        .collect(Collectors.toList());
		if (emplist.isEmpty()) {
				logger.warn("No cylinders found for this type :"+type);
				throw new ResourceNotFoundException("No cylinders found for this type :"+type);
		}
		else	
			return emplist;
	}

	
}
