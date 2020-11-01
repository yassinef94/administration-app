package com.project.administration.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.administration.model.AdmUser;

@Repository
public interface IAdmUserRepository extends JpaRepository<AdmUser, UUID> {
	
	@Query("select u from AdmUser u where u.id=:id ")
	public AdmUser findAdmUserById(@Param("id") UUID id);
	
	@Query("select u from AdmUser u where u.login=:login ")
	public AdmUser findAdmUserByLogin(@Param("login") String login);
	
	@Query("select case when count(u) > 0 then true else false end from AdmUser u where u.login=:login ")
	public Boolean uniqueAdmUserByLogin(@Param("login") String login);
	
}
