package com.indracompany.treinamento.model.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.indracompany.treinamento.exception.AplicacaoException;
import com.indracompany.treinamento.exception.ExceptionValidacoes;
import com.indracompany.treinamento.model.dto.ExtratoBancarioDTO;
import com.indracompany.treinamento.model.entity.ContaBancaria;
import com.indracompany.treinamento.model.entity.ExtratoBancario;
import com.indracompany.treinamento.model.repository.ExtratoBancarioRepository;
import com.indracompany.treinamento.util.ExtratoOperacoes;

@Service
public class ExtratoService extends GenericCrudService<ExtratoBancario, Long, ExtratoBancarioRepository> {

	@Autowired
	private ContaBancariaService contaBancariaService;

	public List<ExtratoBancarioDTO> obterExtratos(String agencia, String numero, String dataInicio, String dataFinal) {

		List<ExtratoBancarioDTO> listExtratoRetorno = new ArrayList<>();

		ContaBancaria contaBancaria = contaBancariaService.consultarConta(agencia, numero);
		
		DateTimeFormatter parser = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		  
		LocalDateTime dateTimeInicio = LocalDate.parse(dataInicio,
		parser).atStartOfDay();
		  
		LocalDateTime dateTimeFinal = LocalDate.parse(dataFinal,
		parser).atTime(23, 59, 59);
		 
		
		List<ExtratoBancario> extratoBancarioList = this.repository.findByContaExtratoAndDateBetween(contaBancaria, dateTimeInicio, dateTimeFinal);

		if (extratoBancarioList == null || extratoBancarioList.isEmpty()) {
			throw new AplicacaoException(ExceptionValidacoes.ERRO_EXTRATO_NAO_ENCONTRADO);
		}

		for (ExtratoBancario extrato : extratoBancarioList) {

			ExtratoBancarioDTO dtoExtrato = new ExtratoBancarioDTO();
			
			dtoExtrato.setAgenciaOrigem(extrato.getContaExtrato().getAgencia());
			dtoExtrato.setNumeroOrigem(extrato.getContaExtrato().getNumero());
			
			if(extrato.getTipoOperacao() == ExtratoOperacoes.TRANSFERENCIA_ENVIADA) {
				dtoExtrato.setAgenciaDestino(extrato.getContaSecundaria().getAgencia());
				dtoExtrato.setNumeroDestino(extrato.getContaSecundaria().getNumero());	
			}
			
			if(extrato.getTipoOperacao() == ExtratoOperacoes.TRANSFERENCIA_RECEBIDA) {
				dtoExtrato.setAgenciaOrigem(extrato.getContaSecundaria().getAgencia());
				dtoExtrato.setNumeroOrigem(extrato.getContaSecundaria().getNumero());
				dtoExtrato.setAgenciaDestino(extrato.getContaExtrato().getAgencia());
				dtoExtrato.setNumeroDestino(extrato.getContaExtrato().getNumero());
			}
			
			dtoExtrato.setDataTransacao(extrato.getDate());
			dtoExtrato.setTipoOperacao(extrato.getTipoOperacao());
			dtoExtrato.setValorOperacao(extrato.getValorOperacao());
			listExtratoRetorno.add(dtoExtrato);

		}

		return listExtratoRetorno;

	}

	public void salvarNoExtrato(ExtratoBancario extratoBancario) {
		this.repository.save(extratoBancario);
	}
}
