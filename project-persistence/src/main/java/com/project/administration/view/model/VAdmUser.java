package com.project.administration.view.model;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.Data;

@Data
@Entity
@Table(name = "v_adm_user", schema = "public")
public class VAdmUser {

	@Id
	@Type(type="pg-uuid")
	@Column(name = "ID", unique = true, nullable = false, length = 255)
	private UUID id;
	
	@Column(name = "LOGIN", unique = true, nullable = false, length = 255)
	private String login;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "FLG_ACTIF")
	private Long flgActif;
	
	@Column(name = "ID_ADM_PROFILE")
	@Type(type="pg-uuid")
	private UUID idAdmProfile;
	
	@Column(name = "LIBELLE_ADM_PROFILE")
	private String libelleAdmProfile;
	
	@Column(name = "ROLE")
	private String role;
	
	@Column(name = "ID_PERSONNEL")
	@Type(type="pg-uuid")
	private UUID idPersonnel;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "DATE_CREATION")
	private Timestamp dateCreation;

	@Column(name = "DATE_UPDATE")
	private Timestamp dateUpdate;
	
	@Column(name = "DATE_EXPIRED")
	private Timestamp dateExpired;

}
