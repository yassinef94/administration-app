package com.project.controller.administration;

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

import com.project.administration.model.Personnel;
import com.project.administration.service.IPersonnelService;
import com.project.common.service.ICommonDaoService;
import com.project.common.service.ISendWsService;
import com.project.tools.ConstanteWs;
import com.project.tools.model.SearchObject;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/administration/personnel")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PersonnelController {

	private static final Logger logger = LogManager.getLogger(PersonnelController.class);
	
	@Autowired
	private IPersonnelService personnelService;
	
	@Autowired
	private ISendWsService sendWsService;
	
	@Autowired
	private ICommonDaoService commonDaoService;
	
	@Operation(summary = "Récupération donnée personnel d'après sont ident")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Succes"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "Un ou plus paramètre(s) est null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_NOT_EXISTS_ROW_DATA_BASE, description = "Personnel n'existe plus dans l'application", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Error service", content = @Content)})
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getPersonnelById(Authentication authentication, @PathVariable("id") UUID id) {
		try {
			return sendWsService.sendResult(authentication, personnelService.getPersonnelByIdWs(id),
					"/administration/personnel/{id}", ConstanteWs._CODE_GET);
		} catch (Exception e) {
			logger.error("Error PersonnelController in method getPersonnelById :: " + e.toString());
			return sendWsService.sendResultException(authentication, "/administration/personnel/{id}", ConstanteWs._CODE_GET);
		}
	}
	
	@Operation(summary = "Récupération liste des données du personnel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Succes"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Error service", content = @Content)})
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getListPersonnel(Authentication authentication) {
		try {
			return sendWsService.sendResult(authentication, personnelService.getListPersonnelWs(),
					"/administration/personnel", ConstanteWs._CODE_GET);
		} catch (Exception e) {
			logger.error("Error PersonnelController in method getListPersonnel :: " + e.toString());
			return sendWsService.sendResultException(authentication, "/administration/personnel", ConstanteWs._CODE_GET);
		}
	}
	
	@PostMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> savePersonnel(Authentication authentication, @RequestBody Personnel personnel) {
		try {
			return sendWsService.sendResult(authentication, personnelService.saveOrUpdatePersonnelWs(personnel, 0),
					"/administration/personnel", ConstanteWs._CODE_POST);
		} catch (Exception e) {
			logger.error("Error PersonnelController in method savePersonnel :: " + e.toString());
			return sendWsService.sendResultException(authentication, "/administration/personnel", ConstanteWs._CODE_POST);
		}
	}
	
	@PutMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updatePersonnel(Authentication authentication, @RequestBody Personnel personnel) {
		try {
			return sendWsService.sendResult(authentication, personnelService.saveOrUpdatePersonnelWs(personnel, 1),
					"/administration/personnel", ConstanteWs._CODE_PUT);
		} catch (Exception e) {
			logger.error("Error PersonnelController in method updatePersonnel :: " + e.toString());
			return sendWsService.sendResultException(authentication, "/administration/personnel", ConstanteWs._CODE_PUT);
		}
	}
	
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deletePersonnelById(Authentication authentication, @PathVariable("id") UUID id) {
		try {
			return sendWsService.sendResult(authentication, personnelService.deletePersonnelByIdWs(id),
					"/administration/personnel/{id}", ConstanteWs._CODE_DELETE);
		} catch (Exception e) {
			logger.error("Error PersonnelController in method deletePersonnelById :: " + e.toString());
			return sendWsService.sendResultException(authentication, "/administration/personnel/{id}", ConstanteWs._CODE_DELETE);
		}
	}
	
	@PostMapping(value="/page", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updatePersonnel(Authentication authentication, @RequestBody SearchObject obj) {
		try {
			return sendWsService.sendResult(authentication, commonDaoService.getListPaginator(obj, new Personnel(), null),
					"/administration/personnel/page", ConstanteWs._CODE_POST);
		} catch (Exception e) {
			logger.error("Error PersonnelController in method updatePersonnel :: " + e.toString());
			return sendWsService.sendResultException(authentication, "/administration/personnel/page", ConstanteWs._CODE_POST);
		}
	}
}
