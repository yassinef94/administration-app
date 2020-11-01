package com.project.administration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.administration.model.LogData;

@Repository
public interface ILogDataRepository extends JpaRepository<LogData, Long> {

}
