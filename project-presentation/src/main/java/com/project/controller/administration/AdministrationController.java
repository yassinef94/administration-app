package com.project.controller.administration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.administration.view.model.VAdmUser;
import com.project.administration.view.model.VLogAccess;
import com.project.administration.view.model.VLogData;
import com.project.common.service.ICommonDaoService;
import com.project.common.service.ISendWsService;
import com.project.tools.ConstanteWs;
import com.project.tools.model.SearchObject;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/administration")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdministrationController {

	private static final Logger logger = LogManager.getLogger(AdministrationController.class);
	
	@Autowired
	private ISendWsService sendWsService;
	
	@Autowired
	private ICommonDaoService commonDaoService;

	@Operation(summary = "Get list of users application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "Un ou plus paramètre(s) est null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_NOT_EXISTS_ROW_DATA_BASE, description = "Personnel n'existe plus dans l'application", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Error service", content = @Content)})
	@PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getListUsers(Authentication authentication, @RequestBody SearchObject obj) {
		try {
			return sendWsService.sendResult(authentication, commonDaoService.getListPaginatorNative(obj, new VAdmUser(), null),
					"/administration/user", ConstanteWs._CODE_POST);
		} catch (Exception argEx) {
			logger.error("Error AdministrationController in method getListUsers :: " + argEx.toString());
			return sendWsService.sendResultException(authentication, "/administration/users", ConstanteWs._CODE_POST);
		}
	}
	
	@PostMapping(value ="/log/data", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getListLogDataWs(Authentication authentication, @RequestBody SearchObject obj) {
		try {
			return sendWsService.sendResult(authentication, commonDaoService.getListPaginator(obj, new VLogData(), null),
					"/administration/log/data", ConstanteWs._CODE_POST);
		} catch (Exception argEx) {
			logger.error("Error AdministrationController in method getListLogDataWs :: " + argEx.toString());
			return sendWsService.sendResultException(authentication, "/administration/log/data", ConstanteWs._CODE_POST);
		}
	}
	
	@PostMapping(value ="/log/access", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getListLogAccessWs(Authentication authentication, @RequestBody SearchObject obj) {
		try {
			return sendWsService.sendResult(authentication, commonDaoService.getListPaginatorNative(obj, new VLogAccess(), null),
					"/administration/log/access", ConstanteWs._CODE_POST);
		} catch (Exception argEx) {
			logger.error("Error AdministrationController in method getListLogAccessWs :: " + argEx.toString());
			return sendWsService.sendResultException(authentication, "/administration/log/access", ConstanteWs._CODE_POST);
		}
	}

}
