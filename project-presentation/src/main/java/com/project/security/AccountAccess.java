package com.project.security;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.project.administration.model.AdmUser;
import com.project.administration.service.IAdmUserService;
import com.project.administration.service.ILogAccessService;
import com.project.administration.view.model.VAdmUser;
import com.project.administration.view.service.IVAdmUserService;
import com.project.config.JwtTokenUtil;
import com.project.tools.ConstanteWs;
import com.project.tools.UtilsWs;
import com.project.tools.model.AuthRequest;
import com.project.tools.model.ResetPassword;
import com.project.tools.model.SendObject;

@Service
public class AccountAccess {
	
	private static final Logger logger = LogManager.getLogger(AccountAccess.class);
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private IVAdmUserService vAdmUserService;
	
	@Autowired
	private ILogAccessService logAccessService;
	
	@Autowired
	private IAdmUserService admUserService;
	
	public SendObject authenticationToApp(AuthRequest authenticationRequest, String ipAddress) {
		String token = null;
		try {
			VAdmUser admUser = vAdmUserService.getVAdmUserByLogin(authenticationRequest.getUsername());
			if(admUser.getId() == null)
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_USER_ERROR_AUTH, new JSONObject());

			this.authenticate(admUser.getId().toString(), authenticationRequest.getPassword());
			
			String code = admUser.getFlgActif() == 1L ? ConstanteWs._CODE_WS_SUCCESS
					: ConstanteWs._CODE_WS_SUCCESS_WAIT_PERMISSION;
			
			if(admUser.getFlgActif() == 1L)
				if(admUser.getDateExpired().before(new Timestamp(new Date().getTime())))
					code = ConstanteWs._CODE_WS_ACCOUNT_EXPIRED;
			
			logAccessService.saveLogAccess(code, admUser.getId(), authenticationRequest.getUsername(), ipAddress);
	
			token = jwtTokenUtil.generateJwtToken(admUser.getId().toString());
			if (token != null && code.equals(ConstanteWs._CODE_WS_SUCCESS)) {
//				JSONObject jsonResult = new JSONObject();
				JSONObject jsonObject = new JSONObject();					
				jsonObject.put("token", token);
				jsonObject.put("name", admUser.getFirstName().concat(" ").concat(admUser.getLastName()));
				jsonObject.put("profile", admUser.getLibelleAdmProfile());
				jsonObject.put("role", admUser.getRole());
//				jsonResult.put("payload", jsonObject);
				return new UtilsWs().resultWs(code, jsonObject);
			} else {
				return new UtilsWs().resultWs(code, new JSONObject());
			}
		} catch (Exception e) {
			logger.error("Error AccountAccess in method authenticationToApp :: "+e.toString());
		} finally {
			if(token == null)
				logAccessService.saveLogAccess(ConstanteWs._CODE_WS_USER_ERROR_AUTH, null, authenticationRequest.getUsername(), ipAddress);
		}
		return new UtilsWs().resultWs(ConstanteWs._CODE_WS_USER_ERROR_AUTH, new JSONObject());
	}
	
	public SendObject resetPasswordSecurity(Authentication authentication, ResetPassword resetPassword) {
		try {
			if(resetPassword.getConfrimPassword() == null 
					|| resetPassword.getNewPassword() == null 
					|| resetPassword.getOldPassword() == null)
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());
			AdmUser admUser = admUserService.getAdmUserById(UUID.fromString(authentication.getName()));
			if(admUser.getId() == null)
				return new UtilsWs().resultWs(ConstanteWs._CODE_WS_ERROR_NOT_EXISTS_ROW_DATA_BASE, new JSONObject());
			this.authenticate(admUser.getId().toString(), resetPassword.getOldPassword());
			return admUserService.resetPasswordWs(admUser, resetPassword);
		} catch (Exception e) {
			logger.error("Error AccountAccess in method resetPasswordSecurity :: "+e.toString());
			return new UtilsWs().resultWs(ConstanteWs._CODE_WS_USER_ERROR_AUTH, new JSONObject());
		}
	}
	
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
