package com.project.administration.model;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="log_access", schema = "public")
public class LogAccess {

	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "seq_log_access", name = "seq_log_access")
	@GeneratedValue(generator = "seq_log_access", strategy = GenerationType.SEQUENCE)
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	private Long id;
	
	@Column(name = "LOGIN", nullable = false, length = 255)
	private String login;
	
	@Column(name = "CODE_ACCESS", nullable = false, length = 50)
	private String codeAccess;
	
	@Column(name = "DATE_AUTH")
	private Timestamp dateAuth;
	
	@Column(name ="ID_ADM_USER")
	private UUID idAdmUser;
	
	@Column(name="IP_ADDRESS")
	private String ipAddress;
	
}
