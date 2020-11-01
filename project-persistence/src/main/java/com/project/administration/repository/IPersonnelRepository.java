package com.project.administration.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.project.administration.model.Personnel;

@Repository
@Transactional
public interface IPersonnelRepository extends JpaRepository<Personnel, UUID> {

	@Query("select p from Personnel p where p.id=:id ")
	public Personnel findPersonnelById(@Param("id") UUID id);

	@Modifying
	@Query("DELETE FROM Personnel p WHERE p.id=:id ")
	public void deletePersonnelById(@Param("id") UUID id);

}
