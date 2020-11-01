package com.project.administration.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.administration.model.AdmProfile;

@Repository
public interface IAdmProfileRepository extends JpaRepository<AdmProfile, UUID> {
	
	@Query("select p from AdmProfile p where p.id=:id ")
	public AdmProfile findAdmProfileById(@Param("id") UUID id);

	@Query("select p from AdmProfile p where p.code=:code ")
	public AdmProfile findAdmProfileByCode(String code);

	@Query("select p from AdmProfile p where p.role=:role ")
	public List<AdmProfile> getListAdmProfileByRole(String role);

}
