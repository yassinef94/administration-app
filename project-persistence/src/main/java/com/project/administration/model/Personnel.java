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
@Table(name = "personnel", schema = "public")
public class Personnel {

	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 255, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
	private UUID id;

	@Column(name = "FIRST_NAME", nullable = false,  length = 100)
	private String firstName;

	@Column(name = "LAST_NAME", nullable = false, length = 100)
	private String lastName;

	@Column(name = "NUM_PHONE")
	private String numPhone;

	@Column(name = "DATE_CREATION")
	private Timestamp dateCreation;

	@Column(name = "DATE_UPDATE")
	private Timestamp dateUpdate;

}