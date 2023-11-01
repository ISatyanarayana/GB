package com.onlinegasbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.onlinegasbooking.entity.SurrenderCylinder;
@Repository
public interface ISurrenderCylinderRepository extends JpaRepository<SurrenderCylinder, Long> {

	@Query("select cy from SurrenderCylinder cy where cy.surrenderId=?1")
	public SurrenderCylinder sCylinderfindbyId(long surrenderId);
}
