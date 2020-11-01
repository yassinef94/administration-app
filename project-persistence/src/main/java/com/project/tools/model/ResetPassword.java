package com.project.tools.model;

import lombok.Data;

@Data
public class ResetPassword {

	private String newPassword;
	private String confrimPassword;
	private String oldPassword;
	
}
