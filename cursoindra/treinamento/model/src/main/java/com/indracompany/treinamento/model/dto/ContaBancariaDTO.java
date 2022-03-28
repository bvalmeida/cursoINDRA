package com.indracompany.treinamento.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ContaBancariaDTO implements Serializable{
	
	private static final long serialVersionUID = 6580691251825623494L;

	private String agencia;
	
	private String numero;
	
	private double saldo;
	
	private String cpf;
	
	private String nomeTitular;
	
}
