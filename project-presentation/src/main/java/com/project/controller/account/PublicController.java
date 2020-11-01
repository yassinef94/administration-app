package com.project.controller.account;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.administration.model.AdmUser;
import com.project.administration.service.IAdmUserService;
import com.project.security.AccountAccess;
import com.project.tools.model.AuthRequest;
import com.project.tools.model.SendObject;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PublicController {

	private static final Logger logger = LogManager.getLogger(PublicController.class);

	@Autowired
	private IAdmUserService admUserService;

	@Autowired
	private AccountAccess accountAccess;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> authenticationToApp(@RequestBody AuthRequest authenticationRequest,
			HttpServletRequest request) throws Exception {
		try {
			SendObject o = accountAccess.authenticationToApp(authenticationRequest, request.getRemoteAddr());
			return new ResponseEntity<>(o.getJsonObject(), new HttpHeaders(), o.getHttp());
		} catch (Exception argEx) {
			logger.error("Error PublicController in method authenticationToApp :: " + argEx.toString());
			return new ResponseEntity<>(new JSONObject().toString(), new HttpHeaders(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveUser(@RequestBody AdmUser user, HttpServletRequest request) throws Exception {
		try {
			SendObject so = admUserService.registerWs(user, request.getRemoteAddr());
			return new ResponseEntity<>(so.getJsonObject(), new HttpHeaders(), so.getHttp());
		} catch (Exception argEx) {
			logger.error("Error PublicController in method saveUser :: " + argEx.toString());
			return new ResponseEntity<>(new JSONObject().toString(), new HttpHeaders(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	@RequestMapping(value = "/menu", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<?> menuApp() throws Exception {
//		try {
//			SendObject so = admFunctionService.getAdmFunctionByIdProfile(null);
//			return new ResponseEntity<>(so.getJsonObject(), new HttpHeaders(), HttpStatus.OK);
//		} catch (Exception argEx) {
//			logger.error("Error PublicController in method saveUser :: " + argEx.toString());
//			return new ResponseEntity<>(new JSONObject().toString(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

}
