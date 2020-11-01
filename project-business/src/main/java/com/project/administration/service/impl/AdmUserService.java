package com.project.administration.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.administration.model.AdmProfile;
import com.project.administration.model.AdmUser;
import com.project.administration.model.Personnel;
import com.project.administration.repository.IAdmProfileRepository;
import com.project.administration.repository.IAdmUserRepository;
import com.project.administration.repository.IPersonnelRepository;
import com.project.administration.service.IAdmUserService;
import com.project.tools.ConstanteWs;
import com.project.tools.UtilsWs;
import com.project.tools.model.ResetPassword;
import com.project.tools.model.SendObject;

@Service
public class AdmUserService implements IAdmUserService {
	
	private static final Logger logger = LogManager.getLogger(AdmUserService.class);

	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	private IAdmUserRepository admUserRepository;
	
	@Autowired
	private IPersonnelRepository personnelRepository;
	
	@Autowired
	private IAdmProfileRepository admProfileRepository;
	
	@Override
	public AdmUser getAdmUserById(UUID id) {
		try {
			if(id == null)
				return new AdmUser();
			AdmUser admUser = admUserRepository.findAdmUserById(id);
			return admUser == null ? new AdmUser() : admUser;
		} catch (Exception e) {
			logger.error("Error AdmUserService in method getAdmUserById " + e.toString());
			return new AdmUser();
		}
	}
	
	@Override
	public AdmUser getAdmUserWithOtherDataByIdWs(UUID id) {
		try {
			if(id == null)
				return new AdmUser();
			AdmUser admUser = this.getAdmUserById(id);
			return this.getAdmUserAllInformation(admUser);
		} catch (Exception e) {
			logger.error("Error AdmUserService in method getAdmUserWithOtherDataByIdWs " + e.toString());
			return new AdmUser();
		}
	}
	
	public AdmUser getAdmUserAllInformation(AdmUser admUser) {
		try {
			Personnel personnel = personnelRepository.findPersonnelById(admUser.getIdPersonnel());
			AdmProfile admProfile = admProfileRepository.findAdmProfileById(admUser.getIdAdmProfile());
//			admUser.setFlgDelete(false);
			admUser.setAdmProfile(admProfile);
			admUser.setPersonnel(personnel);
			return admUser;
		} catch (Exception e) {
			logger.error("Error AdmUserService in method getAdmUserWithOtherDataByIdWs " + e.toString());
			return new AdmUser();
		}
	}

	@Override
	public AdmUser findAdmUserByLogin(String login) {
		try {
			return admUserRepository.findAdmUserByLogin(login);
		} catch (Exception e) {
			logger.error("Error AdmUserService in method findAdmUserByLogin " + e.toString());
			return new AdmUser();
		}
	}

	@Override
	public SendObject getListAdmUserWs() {
		try {
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_SUCCESS, admUserRepository.findAll());
		} catch (Exception e) {
			logger.error("Error AdmUserService in method getListAdmUserWs " + e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONArray());
		}
	}

	@Override
	public SendObject findAdmUserByUsernameWs(String username) {
		try {
			AdmUser user = this.findAdmUserByLogin(username);
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject(user));
		} catch (Exception e) {
			logger.error("Error AdmUserService in method findAdmUserByUsernameWs " + e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR, new JSONObject());
		}
	}

	@Override
	public SendObject registerWs(AdmUser user, String ipAddress) {
		try {
			if(user.getIdAdmProfile() == null || user.getPersonnel() == null)
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());

			if(!user.getPassword().equals(user.getConfirmPassword()))
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_PRB_IN_CONFIRM_PASSWORD, new JSONObject());

			if(admUserRepository.uniqueAdmUserByLogin(user.getLogin()) == true)
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_LOGIN_EXISTS, new JSONObject());

			if(user.getPersonnel() != null) {
				if(user.getPersonnel().getId() == null) {
					user.getPersonnel().setDateCreation(new Timestamp(new Date().getTime()));
					Personnel personnel = personnelRepository.save(user.getPersonnel());
					user.setPersonnel(personnel);
					user.setIdPersonnel(personnel.getId());
				} else {
					user.setIdPersonnel(user.getPersonnel().getId());
				}
			}

			user.setPassword(bcryptEncoder.encode(user.getPassword()));
			user.setFlgActif(1L);
			user.setDateCreation(new Timestamp(new Date().getTime()));
			user.setDateUpdate(null);
			user.setIpAddress(ipAddress);
			user = admUserRepository.save(user);
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_SUCCESS, user);
		} catch(Exception argEx) {
			logger.error("Error AdmUserService in method registerWs :: " + argEx.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject()); 
		}
	}

	@Override
	public SendObject resetPasswordWs(AdmUser admUser, ResetPassword resetPassword) {
		try {
			if(!resetPassword.getNewPassword().equals(resetPassword.getConfrimPassword()))
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_PRB_IN_CONFIRM_PASSWORD, new JSONObject());
			admUser.setPassword(bcryptEncoder.encode(resetPassword.getNewPassword()));
			admUser.setDateUpdate(new Timestamp(new Date().getTime()));
			admUser = admUserRepository.save(admUser);
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject());
		} catch (Exception e) {
			logger.error("Error AdmUserService in method registerWs :: " + e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject()); 
		}
	}
	
	@Override
	public SendObject findAdmUserByIdWs(UUID id) {
		try {
			if(id == null)
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());
			AdmUser admUser = this.getAdmUserById(id);
			if(admUser.getId() == null)
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_NOT_EXISTS_ROW_DATA_BASE, new JSONObject());			
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_SUCCESS, admUser); 
		} catch (NullPointerException e) {
			logger.error("Error AdmUserService in method findAdmUserByIdWs NullPointerException :: " + e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject()); 
		} catch (Exception e) {
			logger.error("Error AdmUserService in method findAdmUserByIdWs Exception :: " + e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject()); 
		}
	}

	@Override
	public SendObject findAdmUserWithOtherDataByIdWs(UUID id) {
		try {
			if(id == null)
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());
			AdmUser admUser = this.getAdmUserById(id);
			if(admUser.getId() == null)
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_NOT_EXISTS_ROW_DATA_BASE, new JSONObject());
			if(admUser.getIdAdmProfile() == null || admUser.getIdPersonnel() == null)
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());
			admUser = this.getAdmUserAllInformation(admUser);
			if(admUser.getId() == null)
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject(admUser)); 
		} catch (NullPointerException e) {
			logger.error("Error AdmUserService in method findAdmUserWithOtherDataByIdWs NullPointerException :: " + e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject()); 
		} catch (Exception e) {
			logger.error("Error AdmUserService in method findAdmUserWithOtherDataByIdWs Exception :: " + e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject()); 
		}
	}

	@Override
	public SendObject updateAdmUserWs(UUID id, AdmUser admUser) {
		try {
			AdmUser admUserTemp = this.getAdmUserById(id);
			if(!admUserTemp.getLogin().equals(admUser.getLogin()))
				if(admUserRepository.uniqueAdmUserByLogin(admUser.getLogin()))
					return new UtilsWs().resultWs(ConstanteWs._CODE_WS_LOGIN_EXISTS, new JSONObject());
			
			
			Timestamp dtNow = new Timestamp(new Date().getTime());
			
			admUser.setDateCreation(admUserTemp.getDateCreation());
			admUser.setDateUpdate(dtNow);
			
			Personnel p = admUser.getPersonnel();
			p.setDateUpdate(dtNow);
			p = personnelRepository.save(p);
			
			admUser.setIdPersonnel(p.getId());
			admUser.setIdAdmProfile(admUser.getIdAdmProfile()); //getAdmProfile().getId()

			admUser = admUserRepository.save(admUser);
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject(admUser)); 
		} catch (NullPointerException e) {
			logger.error("Error AdmUserService in method updateAdmUserWs NullPointerException :: " + e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject()); 
		} catch (Exception e) {
			logger.error("Error AdmUserService in method updateAdmUserWs Exception :: " + e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject()); 
		}
	}

	@Override
	public SendObject deleteProfileByAdminWs(UUID idAdmin, UUID idUser) {
		try {
			if(idUser == null)
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());
			AdmUser admUser = this.getAdmUserById(idUser);
			if(admUser.getId() == null)
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_NOT_EXISTS_ROW_DATA_BASE, new JSONObject()); 
			admUserRepository.deleteById(admUser.getId());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject()); 
		} catch (NullPointerException e) {
			logger.error("Error AdmUserService in method deleteAdmUserWs NullPointerException :: " + e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject()); 
		} catch (Exception e) {
			logger.error("Error AdmUserService in method deleteAdmUserWs Exception :: " + e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_DELETE_ROW, new JSONObject()); 
		}
		
		
	}

	@Override
	public SendObject reActivedProfileByAdminWs(UUID idAdmin, UUID idUser) {
		try {
			if(idUser == null)
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());
			AdmUser admUser = this.getAdmUserById(idUser);
			if(admUser.getId() == null)
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_NOT_EXISTS_ROW_DATA_BASE, new JSONObject());

			admUser.setFlgDelete(false);
			admUser = admUserRepository.save(admUser);

			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject()); 
		} catch (NullPointerException e) {
			logger.error("Error AdmUserService in method deleteAdmUserWs NullPointerException :: " + e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject()); 
		} catch (Exception e) {
			logger.error("Error AdmUserService in method deleteAdmUserWs Exception :: " + e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject()); 
		}
	}

	@Override
	public SendObject desActivedProfileByAdminWs(UUID idAdm, UUID idUser) {
		try {
			if(idUser == null)
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());
			AdmUser admUser = this.getAdmUserById(idUser);
			if(admUser.getId() == null)
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_NOT_EXISTS_ROW_DATA_BASE, new JSONObject());
			
			admUser.setFlgDelete(true);
			admUser = admUserRepository.save(admUser);

			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject()); 
		} catch (NullPointerException e) {
			logger.error("Error AdmUserService in method deleteAdmUserWs NullPointerException :: " + e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject()); 
		} catch (Exception e) {
			logger.error("Error AdmUserService in method deleteAdmUserWs Exception :: " + e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject()); 
		}
	}

}
