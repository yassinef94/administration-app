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
@Table(name="adm_user_historique")
public class AdmUserHistorique {

	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "seq_adm_user_historique", name = "seq_adm_user_historique")
	@GeneratedValue(generator = "seq_adm_user_historique", strategy = GenerationType.SEQUENCE)
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	private Long id;
	
	@Column(name = "ID_ADM_USER", nullable = false)
	private UUID idAdmUser;
	
	@Column(name = "DATE_UPDATE")
	private Timestamp dateUpdate;
	
	@Column(name = "FLG_VALID")
	private Boolean flgValid = false;
	
	@Column(name = "OLD_LOGIN")
	private String oldLogin;
	
	@Column(name = "FLG_CHANGE_LOGIN")
	private Boolean flgChangeLogin = false;
	
//	private UUID idValid;
	
//	private Timestamp dateValid;
	
}
