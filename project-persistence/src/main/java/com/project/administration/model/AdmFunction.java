package com.project.administration.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
@Table(name="adm_function", schema = "public")
public class AdmFunction {

	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "seq_adm_function", name = "seq_adm_function")
	@GeneratedValue(generator = "seq_adm_function", strategy = GenerationType.SEQUENCE)
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	private Long id;
	
	@Column(name="ID_PARENT")
	private Long idParent;
		
	@Column(name = "CODE", unique = true, nullable = false, length = 50)
	private String code;
	
	@Column(name = "Libelle", length = 255)
	private String libelle;
	
	@Column(name="ICON")
	private String icon;
	
	@Column(name="ROUTER")
	private String router;
	
	@Column(name ="ACTIF_FONC")
	private Boolean actifFonc = false;
	
	@Column(name = "DATE_CREATION")
	private Timestamp dateCreation;

	@Column(name = "DATE_UPDATE")
	private Timestamp dateUpdate;
	
	@Column(name ="FLG_ADMIN")
	private Long flgAdmin;
	
	@Transient
	private Boolean takeMenu = false;
	
	@Transient
	private List<AdmFunction> listFunction = new ArrayList<AdmFunction>();

}