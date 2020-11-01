package com.project.administration.model;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name = "adm_profile", schema = "public")
public class AdmProfile {

	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 255, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
	private UUID id;

	@Column(name = "CODE", unique = true, nullable = false, length = 50)
	private String code;

	@Column(name = "ROLE", nullable = false, length = 50)
	private String role;

	@Column(name = "LIBELLE", nullable = false, length = 255)
	private String libelle;

	@Column(name = "DATE_CREATION")
	private Timestamp dateCreation;

	@Column(name = "DATE_UPDATE")
	private Timestamp dateUpdate;

}
