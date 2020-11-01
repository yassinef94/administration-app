package com.project.common.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import com.project.administration.model.LogData;
import com.project.administration.repository.ILogDataRepository;
import com.project.common.service.ISendWsService;
import com.project.tools.ConstanteWs;
import com.project.tools.model.SendObject;

import net.minidev.json.JSONObject;

@Service
public class SendWsService implements ISendWsService {
	
	private static final Logger logger = LogManager.getLogger(SendWsService.class);
	
	@Autowired
	private ILogDataRepository logDataRepository;

	@Override
	public ResponseEntity<?> sendResult(Authentication authentication, SendObject so, String uri, String httpMethod) {
		try {
			LogData logData = new LogData();
			logData.setIdAdmUser(UUID.fromString(authentication.getName()));
			logData.setDateLog(new Timestamp(new Date().getTime()));
			logData.setHttpMethod(httpMethod);
			logData.setUri(uri);
			logData.setResultWs(so.getCode());
			logData.setIpAddress(((WebAuthenticationDetails) authentication.getDetails()).getRemoteAddress());
			logDataRepository.save(logData);
			return new ResponseEntity<>(so.getJsonObject().toString(), new HttpHeaders(), so.getHttp());
		} catch (Exception argEx) {
			logger.error("Error SendWsService in method sendResult :: " + argEx.toString());
			return new ResponseEntity<>(so.getJsonObject().toString(), new HttpHeaders(), so.getHttp());
		}
	}

	
	@Override
	@SuppressWarnings("finally")
	public ResponseEntity<?> sendResultException(Authentication authentication, String uri, String httpMethod) {
		try {
			LogData logData = new LogData();
			logData.setIdAdmUser(UUID.fromString(authentication.getName()));
			logData.setDateLog(new Timestamp(new Date().getTime()));
			logData.setHttpMethod(httpMethod);
			logData.setUri(uri);
			logData.setResultWs(ConstanteWs._CODE_WS_ERROR);
			logData.setIpAddress(((WebAuthenticationDetails) authentication.getDetails()).getRemoteAddress());
			logDataRepository.save(logData);
		} catch (Exception argEx) {
			logger.error("Error SendWsService in method sendResult :: " + argEx.toString());
		} finally {
			return new ResponseEntity<>(new JSONObject(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@Override
	public ResponseEntity<?> sendResultPublic(Authentication authentication, SendObject so, String uri,
			String httpMethod) {
		return null;
	}

}
