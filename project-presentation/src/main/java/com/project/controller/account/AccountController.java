package com.project.controller.account;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.administration.model.AdmUser;
import com.project.administration.service.IAdmFunctionService;
import com.project.administration.service.IAdmUserService;
import com.project.administration.view.service.IVAdmUserService;
import com.project.common.service.ISendWsService;
import com.project.security.AccountAccess;
import com.project.tools.ConstanteWs;
import com.project.tools.model.ResetPassword;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AccountController {

	private static final Logger logger = LogManager.getLogger(AccountController.class);
	
	@Autowired
	private IAdmUserService admUserService;
	
	@Autowired
	private AccountAccess accountAccess;
	
	@Autowired
	private ISendWsService sendWsService;
	
	@Autowired
	private IVAdmUserService vAdmUserService;
	
	@Autowired
	private IAdmFunctionService admFunctionService;
	
	@GetMapping(value="/whoiam", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUserWhoiam(Authentication authentication) {
		try {
			return sendWsService.sendResult(authentication, vAdmUserService.getVAdmUserByIdWs(UUID.fromString(authentication.getName())),
					"/account/whoiam", ConstanteWs._CODE_GET);
		} catch (Exception argEx) {
			logger.error("Error AccountController in method getUserWhoiam :: " + argEx.toString());
			return sendWsService.sendResultException(authentication, "/account/whoiam", ConstanteWs._CODE_GET);
		}
	}

	@PutMapping(value = "/reset/password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> resetPasswordWs(Authentication authentication, @RequestBody ResetPassword resetPassword) {
		try {
			return sendWsService.sendResult(authentication, accountAccess.resetPasswordSecurity(authentication, resetPassword),
					"/account/reset/password", ConstanteWs._CODE_PUT);
		} catch (Exception argEx) {
			logger.error("Error AccountController in method resetPasswordWs :: " + argEx.toString());
			return sendWsService.sendResultException(authentication, "/account/reset/password", ConstanteWs._CODE_PUT);
		}
	}
	
	@GetMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getProfileWs(Authentication authentication) {
		try {
			return sendWsService.sendResult(authentication, admUserService.findAdmUserWithOtherDataByIdWs(UUID.fromString(authentication.getName())),
					"/account", ConstanteWs._CODE_GET);
		} catch (Exception argEx) {
			logger.error("Error AccountController in method getProfileWs :: " + argEx.toString());
			return sendWsService.sendResultException(authentication, "/account", ConstanteWs._CODE_GET);
		}	
	}
	
	@PutMapping(value="/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> editProfile(Authentication authentication, @RequestBody AdmUser admUser) {
		try {
			return sendWsService.sendResult(authentication, admUserService.findAdmUserWithOtherDataByIdWs(UUID.fromString(authentication.getName())),
					"/account", ConstanteWs._CODE_PUT);
		} catch (Exception argEx) {
			logger.error("Error AccountController in method editProfile :: " + argEx.toString());
			return sendWsService.sendResultException(authentication, "/account", ConstanteWs._CODE_PUT);
		}
	}
	
	@DeleteMapping(value="/admin/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteProfileByAdminWs(Authentication authentication, @PathVariable("id") UUID id) {
		try {
			return sendWsService.sendResult(authentication, admUserService.deleteProfileByAdminWs(UUID.fromString(authentication.getName()), id),
					"/account/admin/{id}", ConstanteWs._CODE_DELETE);
		} catch (Exception argEx) {
			logger.error("Error AccountController in method deleteProfileByAdminWs :: " + argEx.toString());
			return sendWsService.sendResultException(authentication, "/account/admin/{id}", ConstanteWs._CODE_DELETE);
		}
	}
	
	@PostMapping(value="/admin/desactive/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> desActiveAccountProfileByIdWs(Authentication authentication, @PathVariable("id") UUID id) {
		try {
			return sendWsService.sendResult(authentication, admUserService.desActivedProfileByAdminWs(UUID.fromString(authentication.getName()), id),
					"/account/admin/desactive/{id}", ConstanteWs._CODE_POST);
		} catch (Exception argEx) {
			logger.error("Error AccountController in method desActiveAccountProfileByIdWs :: " + argEx.toString());
			return sendWsService.sendResultException(authentication, "/account/admin/desactive/{id}", ConstanteWs._CODE_POST);
		}
	}
	
	@PostMapping(value = "/admin/reactive/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> reActiveAccountProfileByIdWs(Authentication authentication, @PathVariable("id") UUID id) {
		try {
			return sendWsService.sendResult(authentication, admUserService.reActivedProfileByAdminWs(UUID.fromString(authentication.getName()), id),
					"/account/admin/reactive/{id}", ConstanteWs._CODE_POST);
		} catch (Exception argEx) {
			logger.error("Error AccountController in method reActiveAccountProfileByIdWs :: " + argEx.toString());
			return sendWsService.sendResultException(authentication, "/account/admin/reactive/{id}", ConstanteWs._CODE_POST);
		}
	}

	@GetMapping(value="/function", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getFunctionWs(Authentication authentication) {
		try {
			return sendWsService.sendResult(authentication, admFunctionService.getAdmFunctionByIdAdmUserWs(UUID.fromString(authentication.getName())),
					"/account/function", ConstanteWs._CODE_GET);
		} catch (Exception argEx) {
			logger.error("Error AccountController in method getFunctionWs :: " + argEx.toString());
			return sendWsService.sendResultException(authentication, "/account/function", ConstanteWs._CODE_GET);
		}	
	}
}
