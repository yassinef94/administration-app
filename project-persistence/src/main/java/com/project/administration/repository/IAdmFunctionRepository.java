package com.project.administration.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.administration.model.AdmFunction;

public interface IAdmFunctionRepository extends JpaRepository<AdmFunction, Long> {

	@Query("select f from AdmFunction f where f.id=:id ")
	public AdmFunction getAdmFunctionById(@Param("id") Long id);

	@Query("select f from AdmFunction f where f.code=:code ")
	public AdmFunction getAdmFunctionByCode(@Param("code") String code);

	@Query("select f from AdmFunction f where f.actifFonc=:actif ")
	public List<AdmFunction> getListAdmFunctionByActif(@Param("actif") Boolean actif);
	
	@Query("select f from AdmFunction f where exists (select 1 from AdmUser u where u.id=:idUser and exists"
			+ " (select 1 from FunctionProfile fp where fp.idAdmFunction=f.id and u.idAdmProfile=fp.idAdmProfile))")
	public List<AdmFunction> getListAdmFunctionForUserByIdUser(@Param("idUser") UUID idUser);
	
	@Query("select f from AdmFunction f where exists"
			+ " (select 1 from FunctionProfile fp where fp.idAdmFunction=f.id and fp.idAdmProfile=:idAdmProfile)")
	public List<AdmFunction> getListAdmFunctionForUserByIdAdmProfile(@Param("idAdmProfile") UUID idAdmProfile);

}
