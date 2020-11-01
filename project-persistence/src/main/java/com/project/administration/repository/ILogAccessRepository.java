package com.project.administration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.administration.model.LogAccess;

@Repository
public interface ILogAccessRepository extends JpaRepository<LogAccess, Long> {

}
