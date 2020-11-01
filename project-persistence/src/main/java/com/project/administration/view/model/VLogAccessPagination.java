package com.project.administration.view.model;

import com.project.tools.model.Pagination;
import com.project.tools.model.Sort;

import lombok.Data;

@Data
public class VLogAccessPagination {

	private Pagination pagination;
	private Sort sort;

}
