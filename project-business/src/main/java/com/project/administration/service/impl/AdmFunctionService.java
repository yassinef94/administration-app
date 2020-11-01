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

import com.project.administration.model.AdmFunction;
import com.project.administration.model.AdmProfile;
import com.project.administration.model.FunctionProfile;
import com.project.administration.repository.IAdmFunctionRepository;
import com.project.administration.repository.IFunctionProfileRepository;
import com.project.administration.service.IAdmFunctionService;
import com.project.administration.service.IAdmProfileService;
import com.project.tools.ConstanteWs;
import com.project.tools.UtilsWs;
import com.project.tools.model.SendObject;

@Service
public class AdmFunctionService implements IAdmFunctionService {

	private static final Logger logger = LogManager.getLogger(AdmFunctionService.class);

	@Autowired
	private IAdmFunctionRepository admFunctionRepository;

	@Autowired
	private IAdmProfileService admProfileService;

	@Autowired
	private IFunctionProfileRepository functionProfileRepository;

	@Override
	public SendObject getAdmFunctionByIdProfileWs(UUID idAdmProfile) {
		try {
			List<AdmFunction> list = admFunctionRepository.getListAdmFunctionForUserByIdAdmProfile(idAdmProfile);
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray(this.initMenu(list)));
		} catch (Exception e) {
			logger.error("Error AdmFunctionService in method getAdmFunctionByIdProfile :: " + e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONArray());
		}
	}

	public List<AdmFunction> initMenu(List<AdmFunction> list) {
		List<AdmFunction> listP = new ArrayList<AdmFunction>();
		List<AdmFunction> listC = new ArrayList<AdmFunction>();
		List<AdmFunction> listF = new ArrayList<AdmFunction>();
		for (AdmFunction f : list) {
			if (f.getIdParent() == null)
				listP.add(f);
			else
				listC.add(f);
		}

		for (AdmFunction f : listP) {
			AdmFunction app = this.initSubMenu(f, listC);
			if (!app.getListFunction().isEmpty())
				this.initMenu(app, listC);
			listF.add(app);
		}
		return listF;
	}

	private AdmFunction initMenu(AdmFunction f, List<AdmFunction> listAll) {
		for (AdmFunction o : f.getListFunction()) {
			AdmFunction af = this.initSubMenu(o, listAll);
			if (!af.getListFunction().isEmpty())
				return initMenu(af, listAll);
		}
		return f;
	}

	private AdmFunction initSubMenu(AdmFunction f, List<AdmFunction> listC) {
		for (AdmFunction c : listC) {
			if (c.getIdParent() != null && f.getId() == c.getIdParent()) {
				List<AdmFunction> temp = f.getListFunction();
				temp.add(c);
				f.setListFunction(temp);
			}
		}
		return f;
	}

	@Override
	public SendObject saveOrUpdateAdmFunctionWs(AdmFunction obj, Integer decisionSaveOrUpdate) {
		try {
			obj = this.saveOrUpdate(obj, decisionSaveOrUpdate);
			if (obj.getId() == null)
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_SAVE_OR_UPDATE, new JSONObject());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject(obj));
		} catch (Exception e) {
			logger.error("Error AdmFunctionService in method saveOrUpdateAdmFunctionWs " + e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
		}
	}

	@Override
	public SendObject deleteAdmFunctionWs(Long id) {
		try {
			if (id == null)
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());
			return new UtilsWs().resultWs(
					this.delete(id) ? ConstanteWs._CODE_WS_SUCCESS : ConstanteWs._CODE_WS_ERROR_DELETE_ROW,
					new JSONObject());
		} catch (Exception e) {
			logger.error("Error AdmFunctionService in method deleteAdmFunctionWs " + e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
		}

	}

	@Override
	public SendObject getAdmFunctionByIdWs(Long id) {
		return null;
	}

	@Override
	public SendObject getListAdmFunctionWs() {
		try {
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray(this.getListAdmFunction()));
		} catch (Exception e) {
			logger.error("Error AdmFunctionService in method getListPersonnel " + e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
		}
	}

	@Override
	public SendObject getAdmFunctionByIdAdmUserWs(UUID idAdmUser) {
		try {
			List<AdmFunction> list = admFunctionRepository.getListAdmFunctionForUserByIdUser(idAdmUser);
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray(this.initMenu(list)));
		} catch (Exception e) {
			logger.error("Error AdmFunctionService in method getAdmFunctionByIdAdmUserWs :: " + e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONArray());
		}
	}

	@Override
	public AdmFunction saveOrUpdate(AdmFunction obj, Integer decisionSaveOrUpdate) {
		try {
			if (decisionSaveOrUpdate == 0)
				obj.setDateCreation(new Timestamp(new Date().getTime()));
			else
				obj.setDateUpdate(new Timestamp(new Date().getTime()));
			obj = admFunctionRepository.save(obj);
			return obj;
		} catch (Exception e) {
			logger.error("Error AdmFunctionService in method saveOrUpdate " + e.toString());
			return new AdmFunction();
		}
	}

	@Override
	public Boolean delete(Long id) {
		try {
			if (id == null)
				return false;
			admFunctionRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			logger.error("Error AdmFunctionService in method delete " + e.toString());
			return false;
		}
	}

	@Override
	public List<AdmFunction> getListAdmFunction() {
		try {
			return admFunctionRepository.findAll();
		} catch (Exception e) {
			logger.error("Error AdmFunctionService in method getListAdmFunction " + e.toString());
			return new ArrayList<AdmFunction>();
		}
	}

	@Override
	public AdmFunction getAdmFunctionById(Long id) {
		try {
			AdmFunction a = admFunctionRepository.getAdmFunctionById(id);
			return a == null ? new AdmFunction() : a;
		} catch (Exception e) {
			logger.error("Error AdmFunctionService in method getAdmFunctionById " + e.toString());
			return new AdmFunction();
		}
	}

	@Override
	public SendObject assignmentFunctionToProfile(List<AdmFunction> listFunction, UUID idAdmProfile) {
		try {
			if (listFunction.isEmpty())
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());
			AdmProfile ap = this.admProfileService.getAdmProfileById(idAdmProfile);
			if (ap.getId() == null)
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_NOT_EXISTS_ROW_DATA_BASE, new JSONObject());
			functionProfileRepository.deleteFunctionProfiles(idAdmProfile);
			this.assignmentMenuForSave(listFunction, idAdmProfile);
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject());
		} catch (Exception e) {
			logger.error("Error AdmFunctionService in method assignmentFunctionToProfile :: " + e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
		}
	}
	
	private void assignmentMenuForSave(List<AdmFunction> listFunction, UUID idAdmProfile) {
		for(AdmFunction f : listFunction) {
			if(f.getTakeMenu()) {
				FunctionProfile fp = new FunctionProfile();
				fp.setIdAdmFunction(f.getId());
				fp.setIdAdmProfile(idAdmProfile);
				fp = functionProfileRepository.save(fp);
				if(!f.getListFunction().isEmpty())
					assignmentMenuForSave(f.getListFunction(), idAdmProfile);
			}
		}
	}

}
