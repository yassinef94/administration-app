package com.project.administration.service;

import java.util.List;
import java.util.UUID;

import com.project.administration.model.AdmFunction;
import com.project.tools.model.SendObject;

public interface IAdmFunctionService {

	public AdmFunction saveOrUpdate(AdmFunction obj, Integer decisionSaveOrUpdate);

	public Boolean delete(Long id);

	public List<AdmFunction> getListAdmFunction();

	public AdmFunction getAdmFunctionById(Long id);
	
	public SendObject saveOrUpdateAdmFunctionWs(AdmFunction obj, Integer decisionSaveOrUpdate);

	public SendObject deleteAdmFunctionWs(Long id);

	public SendObject getAdmFunctionByIdWs(Long id);

	public SendObject getListAdmFunctionWs();

	public SendObject getAdmFunctionByIdAdmUserWs(UUID idAdmUser);

	public SendObject getAdmFunctionByIdProfileWs(UUID idAdmProfile);
	
	public SendObject assignmentFunctionToProfile(List<AdmFunction> listFunction, UUID idAdmProfile);

}
