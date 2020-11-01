package com.project.administration.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.administration.model.Personnel;
import com.project.administration.repository.IPersonnelRepository;
import com.project.administration.service.IPersonnelService;
import com.project.tools.ConstanteWs;
import com.project.tools.UtilsWs;
import com.project.tools.model.SendObject;

@Service
public class PersonnelService implements IPersonnelService {

	private static final Logger logger = LogManager.getLogger(PersonnelService.class);

	@Autowired
	private IPersonnelRepository personnelRepository;

	@Override
	public SendObject getListPersonnelWs() {
		try {
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray(this.getListPersonnel()));
		} catch (Exception e) {
			logger.error("Error PersonnelService in method getListPersonnelWs " + e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
		}
	}

	@Override
	public SendObject getPersonnelByIdWs(UUID id) {
		try {
			if (id == null)
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());
			Personnel p = this.getPersonnelById(id);
			if (p.getId() == null)
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_NOT_EXISTS_ROW_DATA_BASE, new JSONObject());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_NOT_EXISTS_ROW_DATA_BASE, new JSONObject(p));
		} catch (Exception e) {
			logger.error("Error PersonnelService in method getPersonnelByIdWs " + e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
		}
	}

	@Override
	public SendObject saveOrUpdatePersonnelWs(Personnel obj, Integer decisionSaveOrUpdate) {
		try {
			obj = this.saveOrUpdate(obj, decisionSaveOrUpdate);
			if (obj.getId() == null)
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_SAVE_OR_UPDATE, new JSONObject());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject(obj));
		} catch (Exception e) {
			logger.error("Error PersonnelService in method saveOrUpdatePersonnelWs " + e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
		}
	}

	@Override
	public SendObject deletePersonnelByIdWs(UUID id) {
		try {
			if (id == null)
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());
			return new UtilsWs().resultWs(
					this.delete(id) ? ConstanteWs._CODE_WS_SUCCESS : ConstanteWs._CODE_WS_ERROR_DELETE_ROW,
					new JSONObject());
		} catch (Exception e) {
			logger.error("Error PersonnelService in method deletePersonnelByIdWs " + e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
		}
	}

	@Override
	public List<Personnel> getListPersonnel() {
		try {
			return personnelRepository.findAll();
		} catch (Exception e) {
			logger.error("Error PersonnelService in method getListPersonnel " + e.toString());
			return new ArrayList<Personnel>();
		}
	}

	@Override
	public Personnel getPersonnelById(UUID id) {
		try {
			Personnel p = personnelRepository.findPersonnelById(id);
			return p == null ? new Personnel() : p;
		} catch (Exception e) {
			logger.error("Error PersonnelService in method getPersonnelById " + e.toString());
			return new Personnel();
		}
	}

	@Override
	public Personnel saveOrUpdate(Personnel obj, Integer decisionSaveOrUpdate) {
		try {
			if (decisionSaveOrUpdate == 0)
				obj.setDateCreation(new Timestamp(new Date().getTime()));
			else
				obj.setDateUpdate(new Timestamp(new Date().getTime()));
			obj = personnelRepository.save(obj);
			return obj;
		} catch (Exception e) {
			logger.error("Error PersonnelService in method saveOrUpdate " + e.toString());
			return new Personnel();
		}
	}

	@Override
	public Boolean delete(UUID id) {
		try {
			if (id == null)
				return false;
			personnelRepository.deletePersonnelById(id);
			return true;
		} catch (Exception e) {
			logger.error("Error PersonnelService in method delete " + e.toString());
			return false;
		}
	}

}
