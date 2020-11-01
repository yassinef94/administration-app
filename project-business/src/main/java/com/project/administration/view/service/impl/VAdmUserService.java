package com.project.administration.view.service.impl;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.administration.view.model.VAdmUser;
import com.project.administration.view.repository.IVAdmUserRepository;
import com.project.administration.view.service.IVAdmUserService;
import com.project.tools.ConstanteWs;
import com.project.tools.UtilsWs;
import com.project.tools.model.SendObject;


@Service
public class VAdmUserService implements IVAdmUserService {

	private static final Logger logger = LogManager.getLogger(VAdmUserService.class);
	
	@Autowired
	private IVAdmUserRepository vAdmUserRepository;
	
	@Override
	public SendObject getVAdmUserByIdWs(UUID id) {
		try {
			if(id == null)
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());
			VAdmUser admUser = this.getVAdmUserById(id);
			if(admUser.getId() == null)
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject(admUser));
		} catch (Exception e) {
			logger.error("Error VAdmUserService in method getVAdmUserByIdWs :: " + e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
		}
	}

	@Override
	public VAdmUser getVAdmUserById(UUID id) {
		try {
			if(id == null)
				return new VAdmUser();
			VAdmUser user = vAdmUserRepository.findVAdmUserById(id);
			return user == null ? new VAdmUser() : user;
		} catch (Exception e) {
			logger.error("Error VAdmUserService in method getVAdmUserById :: " + e.toString());
			return new VAdmUser();
		}
	}

	@Override
	public SendObject getVAdmUserByLoginWs(String login) {
		try {
			if(login == null)
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());
			VAdmUser admUser = this.getVAdmUserByLogin(login);
			if(admUser.getId() == null)
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_SUCCESS, admUser);
		} catch (Exception e) {
			logger.error("Error VAdmUserService in method getVAdmUserByLoginWs :: " + e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
		}
	}

	@Override
	public VAdmUser getVAdmUserByLogin(String login) {
		try {
			if(login == null)
				return new VAdmUser();
			VAdmUser user = vAdmUserRepository.findVAdmUserByLogin(login);
			return user == null ? new VAdmUser() : user;
		} catch (Exception e) {
			logger.error("Error VAdmUserService in method getVAdmUserByLogin :: " + e.toString());
			return new VAdmUser();
		}
	}

}
