package com.project.administration.service;

import com.project.tools.model.AuthRequest;
import com.project.tools.model.SendObject;

public interface IAccountService {

	public SendObject authenticationToApp(AuthRequest authenticationRequest);
	
}
