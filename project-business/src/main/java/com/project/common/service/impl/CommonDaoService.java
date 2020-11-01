package com.project.common.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.common.dao.ICommonDao;
import com.project.common.service.ICommonDaoService;
import com.project.tools.ConstanteWs;
import com.project.tools.UtilsDao;
import com.project.tools.UtilsWs;
import com.project.tools.model.DaoObject;
import com.project.tools.model.SearchObject;
import com.project.tools.model.SendObject;

import net.minidev.json.JSONObject;

@Service
public class CommonDaoService implements ICommonDaoService {

	private static final Logger logger = LogManager.getLogger(CommonDaoService.class);
	
	@Autowired
	private ICommonDao commonDao;

	@Override
	public SendObject getListPaginator(SearchObject obj, Object objClass, String particularSpecifCondi) {
		try {
			obj = new UtilsDao().initSearchObject(obj);
			DaoObject daoObject = commonDao.getListPaginator(obj, objClass, particularSpecifCondi);
			if (daoObject.getOperationSucces() == false)
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
			return new UtilsWs().resultPaginationWs(ConstanteWs._CODE_WS_SUCCESS, daoObject.getObjectReturn(),
					daoObject.getCountTotal());
		} catch (Exception e) {
			logger.error("Error CommonDaoService in method getListPaginator of class "+objClass.getClass().getName()+" :: " + e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
		}
	}

	@Override
	public SendObject getListPaginatorNative(SearchObject obj, Object objClass, String particularSpecifCondi) {
		try {
			obj = new UtilsDao().initSearchObject(obj);
			DaoObject daoObject = commonDao.getListPaginatorNative(obj, objClass, particularSpecifCondi);
			if (daoObject.getOperationSucces() == false)
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
			return new UtilsWs().resultPaginationWs(ConstanteWs._CODE_WS_SUCCESS, daoObject.getObjectReturn(),
					daoObject.getCountTotal());
		} catch (Exception e) {
			logger.error("Error CommonDaoService in method getListPaginator of class "+objClass.getClass().getName()+" :: " + e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
		}
	}

}
