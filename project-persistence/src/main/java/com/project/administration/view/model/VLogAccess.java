package com.project.administration.view.model;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="v_log_access")
public class VLogAccess {

	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 255)
	private Long id;
	
	@Column(name="DATE_AUTH")
	private Timestamp dateAuth;
	
	@Column(name="CODE_ACCESS")
	private String codeAccess;
	
	@Column(name="LIBELLE_LOG_ACCESS")
	private String libelleLogAccess;
	
	@Column(name="ID_ADM_USER")
	private UUID idAdmUser;
	
	@Column(name="LOGIN")
	private String login;
	
	@Column(name="ID_ADM_PROFILE")
	private UUID idAdmProfile;
	
	@Column(name="LIBELLE_ADM_PROFILE")
	private String libelleAdmProfile;
	
	@Column(name="role")
	private String role;
	
	@Column(name="ID_PERSONNEL")
	private UUID idPersonnel;
	
	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Column(name="LAST_NAME")
	private String lastName;

}
