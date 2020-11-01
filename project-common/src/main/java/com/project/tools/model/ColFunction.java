package com.project.tools.model;

import java.util.List;

import lombok.Data;

@Data
public class ColFunction {
	
	private Long id;
	private String nameMenu;
	private String icon;
	private String router;
	private List<ColFunction> listCol;

}
