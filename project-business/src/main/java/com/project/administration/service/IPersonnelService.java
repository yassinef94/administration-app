package com.project.administration.service;

import java.util.List;
import java.util.UUID;

import com.project.administration.model.Personnel;
import com.project.tools.model.SendObject;

public interface IPersonnelService {

	public SendObject getListPersonnelWs();

	public SendObject getPersonnelByIdWs(UUID id);

	public SendObject saveOrUpdatePersonnelWs(Personnel obj, Integer decisionSaveOrUpdate);

	public SendObject deletePersonnelByIdWs(UUID id);
	
	public List<Personnel> getListPersonnel();
	
	public Personnel getPersonnelById(UUID id);
	
	public Personnel saveOrUpdate(Personnel obj, Integer decisionSaveOrUpdate);
	
	public Boolean delete(UUID id);
	
}
