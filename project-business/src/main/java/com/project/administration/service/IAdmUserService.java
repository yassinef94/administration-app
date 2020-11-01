package com.project.administration.service;

import java.util.UUID;

import com.project.administration.model.AdmUser;
import com.project.tools.model.ResetPassword;
import com.project.tools.model.SendObject;

public interface IAdmUserService {

	public AdmUser getAdmUserById(UUID id);
	
	public AdmUser getAdmUserWithOtherDataByIdWs(UUID id);
	
	public AdmUser findAdmUserByLogin(String login);
	
	public SendObject getListAdmUserWs();
	
	public SendObject findAdmUserByIdWs(UUID id);
	
	public SendObject findAdmUserWithOtherDataByIdWs(UUID id);
	
	public SendObject findAdmUserByUsernameWs(String username);
	
	public SendObject registerWs(AdmUser user, String ipAddress);
	
	public SendObject resetPasswordWs(AdmUser admUser, ResetPassword resetPassword);
	
	public SendObject deleteProfileByAdminWs(UUID idAdmin, UUID idUser);
	
	public SendObject reActivedProfileByAdminWs(UUID idAdmin, UUID idUser);
	
	public SendObject desActivedProfileByAdminWs(UUID idAdmin, UUID idUser);
	
	public SendObject updateAdmUserWs(UUID id, AdmUser admUser);

}
