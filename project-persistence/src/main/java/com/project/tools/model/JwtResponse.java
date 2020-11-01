package com.project.tools.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -4834572221403325836L;

	private final String jwttoken;
	private final String jwtusername;
	private final String jwtfullname;
	private final String jwtemail;

}
