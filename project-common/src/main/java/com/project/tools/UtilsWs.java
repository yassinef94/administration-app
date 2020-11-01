package com.project.tools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import com.project.tools.model.SendObject;

import lombok.Data;

@Data
public class UtilsWs {

	private static final Logger logger = LogManager.getLogger(UtilsWs.class);

	public SendObject resultWs(String codeElem, Object o) {
		SendObject so = new SendObject();
		JSONObject jsonResult = new JSONObject();
		try {
			jsonResult.put("code", codeElem);
			jsonResult.put("payload", o);
			so.setCode(codeElem);
			so.setJsonObject(jsonResult.toString());
			so.setHttp(getHttpStatus(codeElem));
			return so;
		} catch (Exception argEx) {
			jsonResult.put("code", ConstanteWs._CODE_WS_ERROR_CONVERT);
			jsonResult.put("payload", new JSONObject());
			so.setJsonObject(jsonResult.toString());
			so.setCode(ConstanteWs._CODE_WS_ERROR_CONVERT);
			so.setHttp(getHttpStatus(ConstanteWs._CODE_WS_ERROR_CONVERT));
			logger.error("Error UtilWs in method resultWs :: " + argEx.toString());
			return so;
		}
	}

	public SendObject resultPaginationWs(String codeElem, Object o, Long countPage) {
		SendObject so = new SendObject();
		JSONObject jsonResult = new JSONObject();
		JSONObject jsonData = new JSONObject();
		try {
			jsonData.put("data", o);
			jsonData.put("total", countPage);

			jsonResult.put("code", codeElem);
			jsonResult.put("payload", jsonData);
			so.setCode(codeElem);
			so.setJsonObject(jsonResult.toString());
			so.setHttp(getHttpStatus(codeElem));
			return so;
		} catch (Exception argEx) {
			jsonResult.put("code", ConstanteWs._CODE_WS_ERROR_CONVERT);
			jsonResult.put("payload", new JSONObject());
			so.setJsonObject(jsonResult.toString());
			so.setCode(ConstanteWs._CODE_WS_ERROR_CONVERT);
			so.setHttp(getHttpStatus(ConstanteWs._CODE_WS_ERROR_CONVERT));
			logger.error("Error UtilWs in method resultWs :: " + argEx.toString());
			return so;
		}
	}

	public HttpStatus getHttpStatus(String codeElem) {
		switch (codeElem) {
		case ConstanteWs._CODE_WS_SUCCESS:
			return HttpStatus.OK;
		case ConstanteWs._CODE_WS_BAD_REQUEST:
			return HttpStatus.BAD_REQUEST;
		case ConstanteWs._CODE_WS_UNAUTHORIZED:
			return HttpStatus.UNAUTHORIZED;
		case ConstanteWs._CODE_WS_NO_ACCESS:
			return HttpStatus.FORBIDDEN;
		case ConstanteWs._CODE_WS_URI_NOT_FOUND:
			return HttpStatus.NOT_FOUND;
		case ConstanteWs._CODE_WS_ERROR:
			return HttpStatus.INTERNAL_SERVER_ERROR;
		default:
			return HttpStatus.OK;
		}
	}

}
