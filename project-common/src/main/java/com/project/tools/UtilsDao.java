package com.project.tools;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.Column;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import com.project.tools.model.CriteriaSearch;
import com.project.tools.model.Pagination;
import com.project.tools.model.SearchObject;
import com.project.tools.model.Sort;

import lombok.Data;

@Data
public class UtilsDao {
	
	private static final Logger logger = LogManager.getLogger(UtilsDao.class);
	
	public Sort initSort(Sort sort) {
		if(sort == null)
			return new Sort();
		if (sort.getNameCol() == null)
			return sort;
		if (sort.getNameCol().trim().length() == 0) {
			sort.setNameCol(null);
			return sort;
		}
		if (sort.getDirection() == null)
			sort.setDirection("ASC");
		sort.setNameCol("Order By v." + sort.getNameCol() + " " + sort.getDirection());
		return sort;
	}

	public Pagination initPagination(Pagination pagination) {
		if(pagination == null)
			return new Pagination();

		if (pagination.getOffSet() == null)
			pagination.setOffSet(1);
		if (pagination.getOffSet() <= 1)
			pagination.setOffSet(1);

		if (pagination.getLimit() == null)
			pagination.setLimit(10);
		if (pagination.getLimit() <= 0)
			pagination.setLimit(10);

		pagination.setOffSet(pagination.getOffSet() - 1);
		pagination.setOffSet(pagination.getOffSet() * pagination.getLimit());
		return pagination;
	}
	
	public SearchObject initSearchObject(SearchObject obj) {
		obj.setPagination(this.initPagination(obj.getPagination()));
		obj.setSort(this.initSort(obj.getSort()));
		if(obj.getDataSearch() == null)
			obj.setDataSearch(new ArrayList<CriteriaSearch>());
		if(obj.getListCol() == null)
			obj.setListCol(new ArrayList<String>());
		return obj;
	}
	
	public String initTimesTampForClause(String value) throws ParseException {
		DateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
		DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX" ,Locale.getDefault());
		Date date = inputFormat.parse(value);
		return outputFormat.format(date);
	}
	
	public String initClauseIn(String value) {
		String str = "";
//		String[] arrayList = str.split(",");
//		for(String s : arrayList) {
//			str = str.concat(str)
//		}
		return str;
	}
	
	public String[] initClauseBetween(String value, String typeField) {
		try {
			String str = "";
			String[] list = str.split(",");
			if(list[0] != null && list[1] != null) {
				if(typeField.equals(ConstanteDao._CODE_OTHER)) {
					list[0] = "'"+list[0]+"'";
					list[1] = "'"+list[1]+"'";
				} else if(typeField.equals(ConstanteDao._CODE_DATE)) {
					SimpleDateFormat dt = new SimpleDateFormat("yyyy/MM/dd");
					list[0] = "'"+dt.parse(list[0])+"'";
					list[1] = "'"+dt.parse(list[1])+"'";
				}
				return list;
			}
			return null;
		} catch (Exception e) {
			logger.error("Error UtilsDao in method initClauseBetween :: "+e.toString());
			return null;
		}
	}

	public String getClauseSql(Object objClass, List<CriteriaSearch> listCritSearch, Boolean isNative) throws ParseException {
		String sqlCond = "";
		try {
			for (int index = 0; index < objClass.getClass().getDeclaredFields().length; index++) {
				Field field = objClass.getClass().getDeclaredFields()[index];
				field.setAccessible(true);
				for(CriteriaSearch c : listCritSearch)
					if(field.getName().equals(c.getKey()) && c.getValue() != null)
						sqlCond = this.prepationClause(c, objClass, field, sqlCond, isNative);
			}
		} catch (Exception e) {
			logger.error("Error UtilDao in method getClauseSqlHibernate of class "+objClass.getClass().getName()+" :: " + e.toString());
		}
		return sqlCond;
	}
	
	public String prepationClause(CriteriaSearch c, Object objClass, Field field, String sqlCond, Boolean isNative) {
		try {
			String fieldName = isNative == true ? field.getAnnotation(Column.class).name() : field.getName();
			if(field.getType().getTypeName().equals(ConstanteDao._CODE_DAO_STRING))  {
				if(c.getSpecificSearch() == null)
					return sqlCond.concat(" and v."+fieldName+"='"+c.getValue()+"' ");
				return this.returnDetailClauseForString(sqlCond, c, fieldName);
			} else if(field.getType().getTypeName().equals(ConstanteDao._CODE_DAO_UUID)) {
				if(c.getSpecificSearch() == null)
					return sqlCond.concat(" and v."+fieldName+"='"+c.getValue()+"' ");
				return this.returnDetailClauseForUUID(sqlCond, c, fieldName);
			} else if(field.getType().getTypeName().equals(ConstanteDao._CODE_DAO_LONG)
					|| field.getType().getTypeName().equals(ConstanteDao._CODE_DAO_INTEGER)
					|| field.getType().getTypeName().equals(ConstanteDao._CODE_DAO_DOUBLE)) {
				if(c.getSpecificSearch() == null)
					return sqlCond.concat(" and v."+fieldName+"='"+c.getValue()+"' ");
				return this.returnDetailClauseForUUID(sqlCond, c, fieldName);
			} else if(field.getType().getTypeName().equals(ConstanteDao._CODE_DAO_TIMESTAMP)) {
				if(c.getSpecificSearch() == null)
					return sqlCond.concat(" and DATE(v."+fieldName+")='"+this.initTimesTampForClause(c.getValue())+"' ");
				return this.returnDetailClauseForTimesTamp(sqlCond, c, fieldName, this.initTimesTampForClause(c.getValue()));
			}
		} catch (Exception e) {
			logger.error("Error UtilDao in method prepationClause of class " + objClass.getClass().getName()
					+ " in field " + field.getName() + " :: " + e.toString());
			return null;
		}
		return sqlCond;
	}
	
	public String returnDetailClauseForString(String sqlCond, CriteriaSearch c, String fieldName) {
		switch (c.getSpecificSearch()) {
		case ConstanteDao._CODE_DAO_EQUALS:
			return sqlCond.concat(" and v."+fieldName+"='"+c.getValue()+"' ");
		case ConstanteDao._CODE_DAO_DIFFERENCE:
			return sqlCond.concat(" and v."+fieldName+"!='"+c.getValue()+"' ");
		case ConstanteDao._CODE_DAO_LIKE:
			return sqlCond.concat(" and v."+fieldName+" like'%"+c.getValue()+"%' ");
		case ConstanteDao._CODE_DAO_NOT_LIKE:
			return sqlCond.concat(" and v."+fieldName+" not like '%"+c.getValue()+"%' ");
		case ConstanteDao._CODE_DAO_UPPER:
			return sqlCond.concat(" and UPPER(v."+fieldName+")='"+c.getValue().toUpperCase()+"' ");
		case ConstanteDao._CODE_DAO_UPPER_LIKE:
			return sqlCond.concat(" and UPPER(v."+fieldName+") like '%"+c.getValue().toUpperCase()+"%' ");
		case ConstanteDao._CODE_DAO_UPPER_NOT_LIKE:
			return sqlCond.concat(" and UPPER(v."+fieldName+") not like '%"+c.getValue().toUpperCase()+"%' ");
		case ConstanteDao._CODE_DAO_UPPER_DIFFERENCE:
			return sqlCond.concat(" and UPPER(v."+fieldName+")!='"+c.getValue().toUpperCase()+"' ");
		default:
			return sqlCond.concat(" and v."+fieldName+"='"+c.getValue()+"' ");
		}
	}
	
	public String returnDetailClauseForUUID(String sqlCond, CriteriaSearch c, String fieldName) {
		switch (c.getSpecificSearch()) {
		case ConstanteDao._CODE_DAO_EQUALS:
			return sqlCond.concat(" and v."+fieldName+"='"+c.getValue()+"' ");
		case ConstanteDao._CODE_DAO_DIFFERENCE:
			return sqlCond.concat(" and v."+fieldName+"!='"+c.getValue()+"' ");
		case ConstanteDao._CODE_DAO_IN:
			return sqlCond.concat(" and v."+fieldName+" in ["+c.getValue()+"] ");
		default:
			return sqlCond.concat(" and v."+fieldName+"='"+c.getValue()+"' ");
		}
	}
	
	public String returnDetailClauseForNumber(String sqlCond, CriteriaSearch c, String fieldName) {
		switch (c.getSpecificSearch()) {
		case ConstanteDao._CODE_DAO_EQUALS:
			return sqlCond.concat(" and v."+fieldName+"="+c.getValue()+" ");
		case ConstanteDao._CODE_DAO_DIFFERENCE:
			return sqlCond.concat(" and v."+fieldName+"!="+c.getValue()+" ");
		case ConstanteDao._CODE_DAO_IN:
			return sqlCond.concat(" and v."+fieldName+" in ["+c.getValue()+"] ");
		case ConstanteDao._CODE_DAO_BETWEEN:
			String[] str = this.initClauseBetween(c.getValue(), ConstanteDao._CODE_NUMBER);
			return sqlCond.concat(" and (v."+fieldName+" between"+str[0]+"and"+str[1]+")");
		default:
			return sqlCond.concat(" and v."+fieldName+"="+c.getValue()+" ");
		}
	}
	
	public String returnDetailClauseForTimesTamp(String sqlCond, CriteriaSearch c, String fieldName, String value) throws Exception {
		switch (c.getSpecificSearch()) {
		case ConstanteDao._CODE_DAO_EQUALS:
			return sqlCond.concat(" and DATE(v."+fieldName+")='"+value+"' ");
		case ConstanteDao._CODE_DAO_DIFFERENCE:
			return sqlCond.concat(" and DATE(v."+fieldName+")!='"+value+"' ");
		case ConstanteDao._CODE_DAO_INFERIOR:
			return sqlCond.concat(" and DATE(v."+fieldName+")<'"+value+"' ");
		case ConstanteDao._CODE_DAO_INFERIOR_EQUALS:
			return sqlCond.concat(" and DATE(v."+fieldName+")<='"+value+"' ");
		case ConstanteDao._CODE_DAO_SUPERIOR:
			return sqlCond.concat(" and DATE(v."+fieldName+")>'"+value+"' ");
		case ConstanteDao._CODE_DAO_SUPERIOR_EQUALS:
			return sqlCond.concat(" and DATE(v."+fieldName+")>='"+value+"' ");
		case ConstanteDao._CODE_DAO_BETWEEN:
			List<String> specificTreatment = treatmentBetween(value);
			if(specificTreatment == null)
				return sqlCond;
			return sqlCond.concat(" and ( DATE(v."+fieldName+") between '"+specificTreatment.get(0)+"' and '"+specificTreatment.get(1)+"' )");
		default:
			return sqlCond.concat(" and DATE(v."+fieldName+")='"+value+"' ");
		}
	}
	
	public List<String> treatmentIn(String condition, String instanceValue) {
		return null;
	}
	
	public List<String> treatmentBetween(String condition) {
		try {
			String[] listS = condition.split(",");
			return Arrays.asList(listS);
		} catch (Exception e) {
			logger.error("Error UtilsDao in method treatmentBetween :: "+e.toString());
			return null;
		}
	}
	
	public JSONArray mappedDataTable(List<Object> listO, List<String> listCol) {
		JSONArray listArray = new JSONArray();
		for(Object o : listO) {
			JSONObject j = new JSONObject();
			for(int i=0; i<listCol.size(); i++)
				j.put(listCol.get(i), ((Object[])o)[i] != null ? ((Object[])o)[i] : j.NULL);
			listArray.put(j);
		}
		return listArray; 
	}

}
