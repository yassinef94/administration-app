package com.project.common.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import com.project.tools.model.SendObject;

public interface ISendWsService {

	public ResponseEntity<?> sendResult(Authentication authentication, SendObject so, 
			String uri, String httpMethod);
	
	public ResponseEntity<?> sendResultException(Authentication authentication, String uri, String httpMethod);
	
	public ResponseEntity<?> sendResultPublic(Authentication authentication, SendObject so, 
			String uri, String httpMethod);

}
