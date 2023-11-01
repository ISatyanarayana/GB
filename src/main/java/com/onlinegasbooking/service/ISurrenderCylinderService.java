package com.onlinegasbooking.service;

import com.onlinegasbooking.entity.Customer;
import com.onlinegasbooking.entity.SurrenderCylinder;
import com.onlinegasbooking.exceptions.ResourceNotFoundException;

public interface ISurrenderCylinderService {

	public SurrenderCylinder insertSurrenderCylinder(SurrenderCylinder sc,long customerId,long cylinderId) throws ResourceNotFoundException;
	
	public void updateSurrenderCylinder(SurrenderCylinder sc) throws ResourceNotFoundException;
	
	public void deleteSurrenderCylinder(SurrenderCylinder sc) throws ResourceNotFoundException;
	
	public long countSurrendredCylinders();
	
}
