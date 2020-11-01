package com.project.administration.model;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "adm_user", schema = "public")
public class AdmUser {

	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 255, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
	private UUID id;

	@Column(name = "LOGIN", unique = true, nullable = false, length = 50)
	private String login;

	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@Column(name = "DATE_CREATION")
	private Timestamp dateCreation;

	@Column(name = "DATE_UPDATE")
	private Timestamp dateUpdate;
	
	@Column(name = "DATE_EXPIRED", nullable = false)
	private Timestamp dateExpired;

	@Column(name = "FLG_ACTIF", precision = 1, scale = 0, nullable = false)
	private Long flgActif = 0L;

	@Column(name = "ID_ADM_PROFILE", nullable = false)
	private UUID idAdmProfile;

	@Column(name = "ID_PERSONNEL", nullable = false)
	private UUID idPersonnel;
	
	@Column(name ="ip_address")
	private String ipAddress;

	@JsonIgnore
	@Column(name="FLG_DELETE")
	private Boolean flgDelete = false;

	@Transient
	private String confirmPassword;
	
	@Transient
	private AdmProfile admProfile;

	@Transient
	private Personnel personnel;

}
