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
@Table(name ="v_log_data")
public class VLogData {

	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 255)
	private Long id;
	
	@Column(name = "DATE_LOG")
	private Timestamp dateLog;
	
	@Column(name = "HTTP_METHOD")
	private String httpMethod;
	
	@Column(name = "IP_ADDRESS")
	private String IpAddress;
	
	@Column(name = "RESULT_WS")
	private String resultWs;
	
	@Column(name = "URI")
	private String uri;
	
	@Column(name = "ID_ADM_USER")
	private UUID idAdmUser;
	
	@Column(name = "ID_ADM_PROFILE")
	private UUID idAdmProfile;
	
	@Column(name = "LIBELLE_ADM_PROFILE")
	private String libelleAdmProfile;
	
	@Column(name = "ROLE")
	private String role;
	
	@Column(name = "ID_PERSONNEL")
	private UUID idPersonnel;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
}
