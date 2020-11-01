package com.project.administration.service;

import java.util.UUID;

public interface ILogAccessService {

	public void saveLogAccess(String codeAccess, UUID idAdmUser, String login, String ipAddress);

}
