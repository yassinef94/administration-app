package com.project.administration.model;

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
@Table(name = "function_profile", schema = "public")
public class FunctionProfile {

	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "seq_function_profile", name = "seq_function_profile")
	@GeneratedValue(generator = "seq_function_profile", strategy = GenerationType.SEQUENCE)
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	private Long id;

	@Column(name = "ID_ADM_PROFILE", nullable = false)
	private UUID idAdmProfile;

	@Column(name = "ID_ADM_FUNCTION", nullable = false, precision = 22, scale = 0)
	private Long idAdmFunction;

}
