package com.project.administration.view.service;

import java.util.UUID;

import com.project.administration.view.model.VAdmUser;
import com.project.tools.model.SearchObject;
import com.project.tools.model.SendObject;

public interface IVAdmUserService {

	public SendObject getVAdmUserByIdWs(UUID id);
	
	public SendObject getVAdmUserByLoginWs(String login);
	
	public VAdmUser getVAdmUserById(UUID id);
	
	public VAdmUser getVAdmUserByLogin(String login);
	
//	public SendObject getListVAdmUserByPagination(SearchObject obj);
	
}
