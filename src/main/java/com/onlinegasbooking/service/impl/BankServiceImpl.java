package com.onlinegasbooking.service.impl;

import java.util.Optional;

import org.hibernate.ResourceClosedException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinegasbooking.entity.Bank;
import com.onlinegasbooking.exceptions.BankAlreadyExistsException;
import com.onlinegasbooking.exceptions.ResourceNotFoundException;
import com.onlinegasbooking.repository.IBankRepository;
import com.onlinegasbooking.service.IBankService;

@Service
public class BankServiceImpl implements IBankService{

	@Autowired
	private IBankRepository bankRepository;
	
	final static Logger logger = org.slf4j.LoggerFactory.getLogger("GasBookingApplication.class");
	
	
	@Override
	public Bank insertBank(Bank bank) throws BankAlreadyExistsException {
		
		Bank b1=null;
	Optional<Bank> b=bankRepository.findBankByName(bank.getBankName());
	
	if(!b.isEmpty())
	{
		if(b.get().getBankName().equalsIgnoreCase(bank.getBankName()))
			logger.info("Bank already exists");
			logger.warn("Insertion failure!!!!!!!!!!!");
			throw new BankAlreadyExistsException("Bank Already exists By Name :"+bank.getBankName());
			
	}
	else {
		logger.info("Bank details added.");
		b1= bankRepository.save(bank);
	}
		
	return b1;
	}

	@Override
	public Bank updateBank(Bank bank) throws ResourceNotFoundException {
		
		Bank b2=bankRepository.findById(bank.getBankId()).
			orElseThrow(() -> new ResourceNotFoundException("Bank doesnt exist with id:"+ bank.getBankId()));
		b2.setBankId(bank.getBankId());
		b2.setBankName(bank.getBankName());
		b2.setAddress(bank.getAddress());
		
		bankRepository.save(b2);
		return b2;
	}

	@Override
	public Bank deleteBank(Bank bank) throws ResourceNotFoundException {
		Bank b3=bankRepository.findById(bank.getBankId()).
				orElseThrow(() -> new ResourceNotFoundException("Bank doesnt exist with id:"+ bank.getBankId()));
		
	bankRepository.delete(b3);
	return b3;
		
	}

}
