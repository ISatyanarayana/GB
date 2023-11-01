package com.onlinegasbooking.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "customer_id")
public class Customer extends AbstractUser {
	
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Cylinder cylinder;
	
	//private int bankID;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Bank bank;
	
	private long accountNumber;
	
	private String ifscNo;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "customer")
	private List<GasBooking> bookings;

}
