package com.project.common.dao;

import com.project.tools.model.DaoObject;
import com.project.tools.model.SearchObject;

public interface ICommonDao {

	public DaoObject getListPaginator(SearchObject obj, Object objClass, String particularSpecifCondi);
	
	public DaoObject getListPaginatorNative(SearchObject obj, Object objClass, String particularSpecifCondi);
	
}
