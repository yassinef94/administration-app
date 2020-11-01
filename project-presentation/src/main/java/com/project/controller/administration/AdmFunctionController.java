package com.project.controller.administration;

import java.util.List;
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

import com.project.administration.model.AdmFunction;
import com.project.administration.service.IAdmFunctionService;
import com.project.common.service.ICommonDaoService;
import com.project.common.service.ISendWsService;
import com.project.tools.ConstanteWs;
import com.project.tools.model.SearchObject;

@RestController
@RequestMapping("/administration/adm/function")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdmFunctionController {

	private static final Logger logger = LogManager.getLogger(AdmFunctionController.class);

	@Autowired
	private IAdmFunctionService admFunctionService;

	@Autowired
	private ISendWsService sendWsService;

	@Autowired
	private ICommonDaoService commonDaoService;

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAdmFunctionByIdWs(Authentication authentication, @PathVariable("id") Long id) {
		try {
			return sendWsService.sendResult(authentication, admFunctionService.getAdmFunctionByIdWs(id),
					"/administration/adm/function/{id}", ConstanteWs._CODE_GET);
		} catch (Exception e) {
			logger.error("Error AdmFunctionController in method getAdmFunctionByIdWs :: " + e.toString());
			return sendWsService.sendResultException(authentication, "/administration/adm/function/{id}",
					ConstanteWs._CODE_GET);
		}
	}

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getListAdmFunctionWs(Authentication authentication) {
		try {
			return sendWsService.sendResult(authentication, admFunctionService.getListAdmFunctionWs(),
					"/administration/adm/function", ConstanteWs._CODE_GET);
		} catch (Exception e) {
			logger.error("Error AdmFunctionController in method getListAdmFunctionWs :: " + e.toString());
			return sendWsService.sendResultException(authentication, "/administration/adm/function",
					ConstanteWs._CODE_GET);
		}
	}

	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveAdmFunctionWs(Authentication authentication, @RequestBody AdmFunction obj) {
		try {
			return sendWsService.sendResult(authentication, admFunctionService.saveOrUpdateAdmFunctionWs(obj, 0),
					"/administration/adm/function", ConstanteWs._CODE_POST);
		} catch (Exception e) {
			logger.error("Error AdmFunctionController in method saveAdmFunctionWs :: " + e.toString());
			return sendWsService.sendResultException(authentication, "/administration/adm/function",
					ConstanteWs._CODE_POST);
		}
	}

	@PutMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateAdmFunctionWs(Authentication authentication, @RequestBody AdmFunction obj) {
		try {
			return sendWsService.sendResult(authentication, admFunctionService.saveOrUpdateAdmFunctionWs(obj, 1),
					"/administration/adm/function", ConstanteWs._CODE_PUT);
		} catch (Exception e) {
			logger.error("Error AdmFunctionController in method updateAdmFunctionWs :: " + e.toString());
			return sendWsService.sendResultException(authentication, "/administration/adm/function",
					ConstanteWs._CODE_PUT);
		}
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteAdmFunctionByIdWs(Authentication authentication, @PathVariable("id") Long id) {
		try {
			return sendWsService.sendResult(authentication, admFunctionService.deleteAdmFunctionWs(id),
					"/administration/adm/function/{id}", ConstanteWs._CODE_DELETE);
		} catch (Exception e) {
			logger.error("Error AdmFunctionController in method deleteAdmFunctionByIdWs :: " + e.toString());
			return sendWsService.sendResultException(authentication, "/administration/adm/function/{id}",
					ConstanteWs._CODE_DELETE);
		}
	}

	@PostMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listAdmFunctionWithSearchWs(Authentication authentication, @RequestBody SearchObject obj) {
		try {
			return sendWsService.sendResult(authentication, commonDaoService.getListPaginator(obj, new AdmFunction(), null),
					"/administration/adm/function/page", ConstanteWs._CODE_POST);
		} catch (Exception e) {
			logger.error("Error AdmFunctionController in method listAdmFunctionWithSearchWs :: " + e.toString());
			return sendWsService.sendResultException(authentication, "/administration/adm/function/page",
					ConstanteWs._CODE_POST);
		}
	}
	
	@PostMapping(value="/assignment/{idAdmProfile}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> assignmentFunctionForProfileWs(Authentication authentication, @PathVariable("idAdmProfile") UUID idAdmProfile,
			@RequestBody List<AdmFunction> list) {
		try {
			return sendWsService.sendResult(authentication, admFunctionService.assignmentFunctionToProfile(list, idAdmProfile),
					"/administration/adm/function/assignment/{idAdmProfile}", ConstanteWs._CODE_POST);
		} catch (Exception e) {
			logger.error("Error AdmFunctionController in method assignmentFunctionForProfileWs :: " + e.toString());
			return sendWsService.sendResultException(authentication, "/administration/adm/function/assignment/{idAdmProfile}",
					ConstanteWs._CODE_POST);
		}
	}
}
