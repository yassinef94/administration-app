package com.project.administration.view.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.administration.view.model.VAdmUser;

@Repository
public interface IVAdmUserRepository extends JpaRepository<VAdmUser, UUID> {
	
	@Query("select v from VAdmUser v where v.id=:id ")
	public VAdmUser findVAdmUserById(@Param("id") UUID id);
	
	@Query("select v from VAdmUser v where v.login=:login ")
	public VAdmUser findVAdmUserByLogin(@Param("login") String login);
	
	@Query("select v from VAdmUser v where v.id=:id or v.login=:id ")
	public VAdmUser findVAdmUserFroAccess(@Param("id") UUID id);

}
