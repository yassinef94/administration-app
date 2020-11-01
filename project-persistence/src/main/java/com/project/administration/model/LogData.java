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
@Table(name="log_data", schema = "public")
public class LogData {

	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "seq_log_data", name = "seq_log_data")
	@GeneratedValue(generator = "seq_log_data", strategy = GenerationType.SEQUENCE)
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	private Long id;
	
	@Column(name = "ID_ADM_USER", nullable = false, length = 255)
	private UUID idAdmUser;

	@Column(name = "DATE_LOG", nullable = false)
	private Timestamp dateLog;

	@Column(name ="URI", nullable = false, length = 255)
	private String uri;

	@Column(name ="HTTP_METHOD", nullable = false, length = 50)
	private String httpMethod;

	@Column(name ="IP_ADDRESS", nullable = false, length = 255)
	private String ipAddress;
	
	@Column(name ="RESULT_WS", nullable = false, length = 255)
	private String resultWs;

}
