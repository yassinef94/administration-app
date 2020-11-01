package com.project.tools.model;

import java.util.List;

import lombok.Data;

@Data
public class SearchObject {
		
	private Pagination pagination;
	private Sort sort;
	private List<CriteriaSearch> dataSearch;
	private List<String> listCol;

}
