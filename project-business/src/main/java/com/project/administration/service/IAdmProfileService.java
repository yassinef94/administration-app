package com.project.administration.service;

import java.util.List;
import java.util.UUID;

import com.project.administration.model.AdmProfile;
import com.project.tools.model.SendObject;

public interface IAdmProfileService {

	public SendObject getListAdmProfileWs();

	public SendObject getAdmProfileByIdWs(UUID id);

	public SendObject getAdmProfileByCodeWs(String code);

	public SendObject saveOrUpdateAdmProfileWs(AdmProfile obj, Integer decisionSaveOrUpdate);

	public SendObject deleteAdmProfileByIdWs(UUID id);

	public List<AdmProfile> getListAdmProfile();

	public AdmProfile getAdmProfileById(UUID id);

	public AdmProfile getAdmProfileByCode(String code);

}
