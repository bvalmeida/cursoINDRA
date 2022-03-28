package com.indracompany.treinamento.model.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.indracompany.treinamento.model.entity.ContaBancaria;
import com.indracompany.treinamento.model.entity.ExtratoBancario;

public interface ExtratoBancarioRepository extends GenericCrudRepository<ExtratoBancario, Long> {
	
	List<ExtratoBancario> findByContaOrigemAndDateBetween(ContaBancaria contaBancaria, LocalDateTime dataInicio, LocalDateTime dataFinal);
	
}
