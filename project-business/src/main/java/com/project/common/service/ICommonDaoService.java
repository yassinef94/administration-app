package com.project.common.service;

import com.project.tools.model.SearchObject;
import com.project.tools.model.SendObject;

public interface ICommonDaoService {

	public SendObject getListPaginator(SearchObject obj, Object objClass, String particularSpecifCondi);
	
	public SendObject getListPaginatorNative(SearchObject obj, Object objClass, String particularSpecifCondi);
}
