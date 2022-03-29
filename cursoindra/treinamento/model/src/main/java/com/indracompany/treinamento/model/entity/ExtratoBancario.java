package com.indracompany.treinamento.model.entity;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.indracompany.treinamento.util.ExtratoOperacoes;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "extratos_brenovieira")
@Data
@EqualsAndHashCode(callSuper = true)
public class ExtratoBancario extends GenericEntity<Long>{
	
	private static final long serialVersionUID = -5824703733929187165L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	@Enumerated(EnumType.STRING)
	private ExtratoOperacoes tipoOperacao;
	
	@Column
	private double valorOperacao;
	
	@ManyToOne
	@JoinColumn(name = "fk_contaExtrato_id")
	private ContaBancaria contaExtrato;
	
	@ManyToOne
	@JoinColumn(name = "fk_contaOrigem_id")
	private ContaBancaria contaOrigem;
	
	@ManyToOne
	@JoinColumn(name = "fk_contaDestino_id")
	private ContaBancaria contaDestino;
	
	@Column(nullable = false)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime date;
	
}
