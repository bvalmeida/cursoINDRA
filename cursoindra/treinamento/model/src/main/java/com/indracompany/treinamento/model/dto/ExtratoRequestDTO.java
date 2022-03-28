package com.indracompany.treinamento.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ExtratoRequestDTO implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = -3999290524416903276L;
	
	private String agencia;
	
	private String numero;
	
	private LocalDateTime dataInicio;
	
	private LocalDateTime dataFinal;

}
