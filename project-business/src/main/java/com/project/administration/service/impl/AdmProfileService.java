package com.project.administration.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.administration.model.AdmProfile;
import com.project.administration.repository.IAdmProfileRepository;
import com.project.administration.service.IAdmProfileService;
import com.project.tools.ConstanteWs;
import com.project.tools.UtilsWs;
import com.project.tools.model.SendObject;

@Service
public class AdmProfileService implements IAdmProfileService {
	
	private static final Logger logger = LogManager.getLogger(AdmProfileService.class);
	
	@Autowired
	private IAdmProfileRepository admProfileRepository;

	@Override
	public SendObject getListAdmProfileWs() {
		try {
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray(this.getListAdmProfile()));
		} catch (Exception e) {
			logger.error("Error AdmProfileService in method getListAdmProfileWs "+e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
		}
	}

	@Override
	public SendObject getAdmProfileByIdWs(UUID id) {
		try {
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject(this.getAdmProfileById(id)));
		} catch (Exception e) {
			logger.error("Error AdmProfileService in method getAdmProfileByIdWs "+e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
		}
	}

	@Override
	public SendObject getAdmProfileByCodeWs(String code) {
		try {
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject(this.getAdmProfileByCode(code)));
		} catch (Exception e) {
			logger.error("Error AdmProfileService in method getAdmProfileByCodeWs "+e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
		}
	}

	@Override
	public SendObject saveOrUpdateAdmProfileWs(AdmProfile obj, Integer decisionSaveOrUpdate) {
		try {
			AdmProfile objCodeUnique = admProfileRepository.findAdmProfileByCode(obj.getCode());
			if(obj.getId() != null) {
				AdmProfile objTemp = admProfileRepository.getOne(obj.getId());
				if(!obj.getCode().equals(objTemp.getCode())) {
					if(objCodeUnique != null)
						return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_UNIQUE_CODE, new JSONObject());
				}
			} else if (obj.getId() == null && objCodeUnique != null)
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_UNIQUE_CODE, new JSONObject());

			if(decisionSaveOrUpdate == 0)
				obj.setDateCreation(new Timestamp(new Date().getTime()));
			else
				obj.setDateUpdate(new Timestamp(new Date().getTime()));

			obj = admProfileRepository.save(obj);
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject(obj));
		} catch (Exception e) {
			logger.error("Error AdmProfileService in method getAdmProfileByCodeWs "+e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
		}
	}

	@Override
	public SendObject deleteAdmProfileByIdWs(UUID id) {
		try {
			admProfileRepository.deleteById(id);
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject());
		} catch (Exception e) {
			logger.error("Error AdmProfileService in method getAdmProfileByCodeWs "+e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_PROBLEM_DELETE, new JSONObject());
		}
	}

	@Override
	public List<AdmProfile> getListAdmProfile() {
		return admProfileRepository.findAll();
	}

	@Override
	public AdmProfile getAdmProfileById(UUID id) {
		AdmProfile a = admProfileRepository.findAdmProfileById(id);
		if(a == null)
			return new AdmProfile();
		return a;
	}

	@Override
	public AdmProfile getAdmProfileByCode(String code) {
		AdmProfile a = admProfileRepository.findAdmProfileByCode(code);
		if(a == null)
			return new AdmProfile();
		return a;
	}

}
