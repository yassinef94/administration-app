package com.project.tools.model;

import lombok.Data;

@Data
public class DaoObject {

	private Boolean operationSucces = false;
	private Object objectReturn;
	private Long countTotal;
	
}
