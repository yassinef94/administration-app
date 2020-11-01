package com.project.administration.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.project.administration.model.FunctionProfile;

@Repository
public interface IFunctionProfileRepository extends JpaRepository<FunctionProfile, Long>{

	@Modifying
	@Transactional
	@Query("delete from FunctionProfile f where f.idAdmProfile=:idAdmProfile")
	public void deleteFunctionProfiles(@Param("idAdmProfile") UUID idAdmProfile);
	
}
