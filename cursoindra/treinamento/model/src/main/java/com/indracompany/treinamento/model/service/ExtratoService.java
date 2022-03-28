package com.indracompany.treinamento.model.service;

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

@Service
public class ExtratoService extends GenericCrudService<ExtratoBancario, Long, ExtratoBancarioRepository> {
	
	@Autowired
	private ContaBancariaService contaBancariaService;
	
	public List<ExtratoBancarioDTO> obterExtratos(String agencia, String numero) {
		
		List<ExtratoBancarioDTO> listExtratoRetorno = new ArrayList<>();
		
		ContaBancaria contaBancaria = contaBancariaService.consultarConta(agencia, numero);
		
		List<ExtratoBancario> extratoBancarioList = this.repository.findByContaBancaria(contaBancaria);
		
		if(contaBancaria == null) {
			throw new AplicacaoException(ExceptionValidacoes.ERRO_OBJETO_NAO_ENCONTRADO);
		}
		
		if(extratoBancarioList == null || extratoBancarioList.isEmpty()) {
			throw new AplicacaoException(ExceptionValidacoes.ERRO_EXTRATO_NAO_ENCONTRADO);
		}
		
		for(ExtratoBancario extrato : extratoBancarioList) {
			
			ExtratoBancarioDTO dtoExtrato = new ExtratoBancarioDTO();
			
			dtoExtrato.setAgenciaOrigem(extrato.getAgenciaOrigem());
			dtoExtrato.setNumeroOrigem(extrato.getNumeroOrigem());
			dtoExtrato.setDataTransacao(extrato.getDate());
			dtoExtrato.setTipoOperacao(extrato.getTipoOperacao());
			dtoExtrato.setValorOperacao(extrato.getValorOperacao());
			listExtratoRetorno.add(dtoExtrato);
			
		}
		
		return listExtratoRetorno;
		
	}
	
	/*
	 * public List<ExtratoBancarioDTO> obterExtratosPorDatas(String agencia, String
	 * numero, LocalDateTime dataInicio, LocalDateTime dataFinal){
	 * 
	 * List<ExtratoBancarioDTO> listExtratoPorDatas = new ArrayList<>();
	 * 
	 * obterExtratos(agencia, numero);
	 * 
	 * List<ExtratoBancario> list
	 * 
	 * 
	 * 
	 * }
	 */
	
	
	
	public void salvarNoExtrato(ExtratoBancario extratoBancario) {
		this.repository.save(extratoBancario);
	}
}
