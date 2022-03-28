package com.indracompany.treinamento.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.indracompany.treinamento.util.ExtratoOperacoes;

import lombok.Data;

@Data
public class ExtratoBancarioDTO implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = -3466393431700409027L;

	private ExtratoOperacoes tipoOperacao;
	
	private String agenciaOrigem;
	
	private String agenciaDestino;
	
	private String numeroOrigem;
	
	private String numeroDestino;
	
	private double valorOperacao;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime dataTransacao;
	
}
