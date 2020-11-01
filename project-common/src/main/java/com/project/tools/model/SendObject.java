package com.project.tools.model;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class SendObject {

	private String code;
	private String jsonObject;
	private HttpStatus http;

}
