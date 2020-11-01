package com.project.controller.nomenclature;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.administration.model.AdmProfile;
import com.project.administration.service.IAdmProfileService;
import com.project.common.service.ISendWsService;
import com.project.tools.ConstanteWs;

@RestController
@RequestMapping("/nomenclature/adm/profile")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NomenclatureAdminiAdmProfileController {

	private static final Logger logger = LogManager.getLogger(NomenclatureAdminiAdmProfileController.class);
	
	@Autowired
	private IAdmProfileService admProfileService;
	
	@Autowired
	private ISendWsService sendWsService;
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAdmProfileById(Authentication authentication, @PathVariable(name = "id") UUID id) {
		try {
			return sendWsService.sendResult(authentication, admProfileService.getAdmProfileByIdWs(id), "/nomenclature/adm/profile/{id}", ConstanteWs._CODE_GET);
		} catch (Exception argEx) {
			logger.error("Error NomenclatureAdminiAdmProfileController in method getAdmProfileById :: " + argEx.toString());
			return sendWsService.sendResultException(authentication, "/nomenclature/adm/profile/{id}", ConstanteWs._CODE_GET);
		}
	}
	
	@GetMapping(value = "/code", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAdmProfileByCode(Authentication authentication, @RequestParam(name = "code", required = true) String code) {
		try {
			return sendWsService.sendResult(authentication, admProfileService.getAdmProfileByCodeWs(code), "/nomenclature/adm/profile/code", ConstanteWs._CODE_GET);
		} catch (Exception argEx) {
			logger.error("Error NomenclatureAdminiAdmProfileController in method getAdmProfileByCode :: " + argEx.toString());
			return sendWsService.sendResultException(authentication, "/nomenclature/adm/profile/code", ConstanteWs._CODE_GET);
		}
	}
	
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getListAdmProfile(Authentication authentication) {
		try {
			return sendWsService.sendResult(authentication, admProfileService.getListAdmProfileWs(), "/nomenclature/adm/profile", ConstanteWs._CODE_GET);
		} catch (Exception argEx) {
			logger.error("Error NomenclatureAdminiAdmProfileController in method getListAdmProfile :: " + argEx.toString());
			return sendWsService.sendResultException(authentication, "/nomenclature/adm/profile", ConstanteWs._CODE_GET);
		}
	}
	
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> pushAdmProfile(Authentication authentication, @RequestBody AdmProfile admProfile) {
		try {
			return sendWsService.sendResult(authentication, admProfileService.saveOrUpdateAdmProfileWs(admProfile, 0), "/nomenclature/adm/profile", ConstanteWs._CODE_POST);
		} catch (Exception argEx) {
			logger.error("Error NomenclatureAdminiAdmProfileController in method pushAdmProfile :: " + argEx.toString());
			return sendWsService.sendResultException(authentication, "/nomenclature/adm/profile", ConstanteWs._CODE_POST);
		}
	}
	
	@PutMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateAdmProfile(Authentication authentication, @RequestBody AdmProfile admProfile) {
		try {
			return sendWsService.sendResult(authentication, admProfileService.saveOrUpdateAdmProfileWs(admProfile, 1), "/nomenclature/adm/profile", ConstanteWs._CODE_PUT);
		} catch (Exception argEx) {
			logger.error("Error NomenclatureAdminiAdmProfileController in method updateAdmProfile :: " + argEx.toString());
			return sendWsService.sendResultException(authentication, "/nomenclature/adm/profile", ConstanteWs._CODE_PUT);
		}
	}
	
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteAdmProfileById(Authentication authentication, @PathVariable("id") UUID id) {
		try {
			return sendWsService.sendResult(authentication, admProfileService.deleteAdmProfileByIdWs(id), "/nomenclature/adm/profile/{id}", ConstanteWs._CODE_DELETE);
		} catch (Exception argEx) {
			logger.error("Error NomenclatureAdminiAdmProfileController in method deleteAdmProfileById :: " + argEx.toString());
			return sendWsService.sendResultException(authentication, "/nomenclature/adm/profile/{id}", ConstanteWs._CODE_DELETE);
		}
	}

}
