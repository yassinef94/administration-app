package com.project.common.dao.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Table;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.common.dao.ICommonDao;
import com.project.tools.ConstanteDao;
import com.project.tools.UtilsDao;
import com.project.tools.model.DaoObject;
import com.project.tools.model.SearchObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Repository
public class CommonDao implements ICommonDao {

	private static final Logger logger = LogManager.getLogger(CommonDao.class);

	private EntityManager entityManager;
	
	private List<String> listColNative;

	@Autowired
	public CommonDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	private String getScriptHeadCol(String fieldNameSql, String typeField, String sqlCol) {
		if (typeField.equals(ConstanteDao._CODE_DAO_UUID))
			return sqlCol.concat(" CAST(v." + fieldNameSql).concat(" as varchar(255)) as " + fieldNameSql + ", ");
		else
			return sqlCol.concat("v.").concat(fieldNameSql).concat(", ");
	}
	
	private String initSqlColumnName(Object objClass, List<String> listCol, Boolean nativeNameCol) {
		String result = "";
		this.listColNative = new ArrayList<String>();
		for (int index = 0; index < objClass.getClass().getDeclaredFields().length; index++) {
			Field field = objClass.getClass().getDeclaredFields()[index];
			field.setAccessible(true);
			String fieldNameSql = field.getAnnotation(Column.class).name();
			this.listColNative.add(field.getName());
			if(listCol.isEmpty()) {
				if(nativeNameCol == false)
					return "v";
				else
					result = this.getScriptHeadCol(fieldNameSql, field.getType().getTypeName(), result);
			}
			for (String str : listCol)
				if (str.equals(field.getName()))
					result = nativeNameCol ? this.getScriptHeadCol(fieldNameSql, field.getType().getTypeName(), result)
							: result.concat("v.").concat(field.getName()).concat(", ");
		}
		return result.substring(0, result.length() - 2);
	}

	@Override
	public DaoObject getListPaginator(SearchObject obj, Object objClass, String particularSpecifCondi) {
		DaoObject daoObject = new DaoObject();
		String sqlCond = "";
		String sqlCol = "";
		String sqlSort = "";
		String sqlPartSpecCondif = "";
		try {
			if (!obj.getDataSearch().isEmpty())
				sqlCond = new UtilsDao().getClauseSql(objClass, obj.getDataSearch(), false);
			if (particularSpecifCondi != null)
				sqlPartSpecCondif = particularSpecifCondi;
			
			if(obj.getSort().getNameCol() != null)
				sqlSort = obj.getSort().getNameCol() == null ? "" : obj.getSort().getNameCol();

			sqlCol = obj.getListCol().isEmpty() ? "v" : this.initSqlColumnName(objClass, obj.getListCol(), false);

			Query query = (Query) entityManager
					.createQuery("Select " + sqlCol + " from " + objClass.getClass().getName() + " v where 1=1 "
							.concat(sqlPartSpecCondif).concat(" ").concat(sqlCond).concat(" ").concat(sqlSort));
			if (obj.getPagination().getLimit() != null && obj.getPagination().getOffSet() != null)
				query.setFirstResult(obj.getPagination().getOffSet()).setMaxResults(obj.getPagination().getLimit());

			Query queryCount = (Query) entityManager.createQuery("Select count(1) from " + objClass.getClass().getName()
					+ " v where 1=1 ".concat(sqlPartSpecCondif).concat(" ").concat(sqlCond));

			daoObject.setCountTotal((Long) queryCount.getSingleResult());
			daoObject.setObjectReturn(sqlCol == "v" ? query.getResultList()
					: new UtilsDao().mappedDataTable(query.getResultList(), obj.getListCol()));
			daoObject.setOperationSucces(true);
			return daoObject;
		} catch (Exception e) {
			logger.error("Error CommonDao in method getListPaginator of class " + objClass.getClass().getName() + " :: "
					+ e.toString());
			daoObject.setOperationSucces(false);
			return daoObject;
		} finally {
			entityManager.close();
		}
	}

	@Override
	public DaoObject getListPaginatorNative(SearchObject obj, Object objClass, String particularSpecifCondi) {
		DaoObject daoObject = new DaoObject();
		listColNative = new ArrayList<String>();
		String sqlCol = "";
		String sqlCond = "";
		String sqlSort = "";
		String sqlPartSpecCondif = "";
		try {
			sqlCol = this.initSqlColumnName(objClass, obj.getListCol(), true);
			if (particularSpecifCondi != null)
				sqlPartSpecCondif = particularSpecifCondi;
			if (!obj.getDataSearch().isEmpty())
				sqlCond = new UtilsDao().getClauseSql(objClass, obj.getDataSearch(), true);

			if(obj.getSort().getNameCol() != null)
				sqlSort = obj.getSort().getNameCol() == null ? "" : obj.getSort().getNameCol();

			Table table = objClass.getClass().getAnnotation(Table.class);
			Query query = (Query) entityManager
					.createNativeQuery("Select " + sqlCol + " from " + table.name() + " v where 1=1 "
							.concat(sqlPartSpecCondif).concat(" ").concat(sqlCond).concat(" ").concat(sqlSort));
			if (obj.getPagination().getLimit() != null && obj.getPagination().getOffSet() != null)
				query.setFirstResult(obj.getPagination().getOffSet()).setMaxResults(obj.getPagination().getLimit());

			Query queryCount = (Query) entityManager.createNativeQuery("Select count(1) from " + table.name()
					+ " v where 1=1 ".concat(sqlPartSpecCondif).concat(" ").concat(sqlCond));

			daoObject.setCountTotal(Long.parseLong(queryCount.getSingleResult().toString()));
			daoObject.setObjectReturn(new UtilsDao().mappedDataTable(query.getResultList(), listColNative));
			daoObject.setOperationSucces(true);
			return daoObject;
		} catch (Exception e) {
			logger.error("Error CommonDao in method getListPaginatorNative of class " + objClass.getClass().getName()
					+ " :: " + e.toString());
			daoObject.setOperationSucces(false);
			return daoObject;
		} finally {
			entityManager.close();
		}
	}

}
