package com.indracompany.treinamento.model.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.indracompany.treinamento.exception.AplicacaoException;
import com.indracompany.treinamento.exception.ExceptionValidacoes;
import com.indracompany.treinamento.model.dto.ContaBancariaDTO;
import com.indracompany.treinamento.model.dto.TransferenciaBancariaDTO;
import com.indracompany.treinamento.model.entity.Cliente;
import com.indracompany.treinamento.model.entity.ContaBancaria;
import com.indracompany.treinamento.model.entity.ExtratoBancario;
import com.indracompany.treinamento.model.repository.ContaBancariaRepository;
import com.indracompany.treinamento.util.ExtratoOperacoes;


@Service
public class ContaBancariaService extends GenericCrudService<ContaBancaria, Long, ContaBancariaRepository>{
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ExtratoService extratoService;
	
	public List<ContaBancariaDTO> obterContasPorCpf(String cpf){

		List<ContaBancariaDTO> listaContasRetorno = new ArrayList<>();
		Cliente cliente = clienteService.buscarCliente(cpf);

		List<ContaBancaria> listaContasCliente = repository.findByCliente(cliente);
		
		for (ContaBancaria conta : listaContasCliente) {
			
			ContaBancariaDTO dtoConta = new ContaBancariaDTO();
			BeanUtils.copyProperties(conta, dtoConta);
			dtoConta.setCpf(conta.getCliente().getCpf());
			dtoConta.setNomeTitular(conta.getCliente().getNome());
			listaContasRetorno.add(dtoConta);
		}

		return listaContasRetorno;
	}
	
	public double consultarSaldo(String agencia, String numero) {
		ContaBancaria c = consultarConta(agencia, numero);
		
		return c.getSaldo();
	}	
	
	public ContaBancaria consultarConta(String agencia, String numero) {
		ContaBancaria cB = repository.findByAgenciaAndNumero(agencia, numero);
		
		if(cB == null) {
			throw new AplicacaoException(ExceptionValidacoes.ERRO_CONTA_INVALIDA);
		}
		
		return cB;
	}
	
	public void depositar(String agencia, String numero, double valor) {
		
		ContaBancaria contaBancaria = consultarConta(agencia, numero);
		
		if(valor <= 0) {
			throw new AplicacaoException(ExceptionValidacoes.ERRO_VALOR_INVALIDO);
		}
		
		contaBancaria.setSaldo(contaBancaria.getSaldo() + valor);
		
		LocalDateTime date = LocalDateTime.now();
		
		ExtratoBancario extratoBancario = new ExtratoBancario();
		
		extratoBancario.setContaExtrato(contaBancaria);
		extratoBancario.setDate(date);
		extratoBancario.setTipoOperacao(ExtratoOperacoes.DEPOSITO);
		extratoBancario.setValorOperacao(valor);
		
		extratoService.salvarNoExtrato(extratoBancario);
		
		super.salvar(contaBancaria);
	}
	
	public void sacar(String agencia, String numero, double valor) {
		
		ContaBancaria contaBancaria = consultarConta(agencia, numero);
		
		if(valor <= 0) {
			throw new AplicacaoException(ExceptionValidacoes.ERRO_VALOR_INVALIDO);
		}
		
		if(contaBancaria.getSaldo() < valor) {
			throw new AplicacaoException(ExceptionValidacoes.ERRO_SALDO_INEXISTENTE);
		}
		
		contaBancaria.setSaldo(contaBancaria.getSaldo() - valor);
		
		LocalDateTime date = LocalDateTime.now();
		
		ExtratoBancario extratoBancario = new ExtratoBancario();
		
		extratoBancario.setContaExtrato(contaBancaria);
		extratoBancario.setDate(date);
		extratoBancario.setTipoOperacao(ExtratoOperacoes.SAQUE);
		extratoBancario.setValorOperacao(valor);
		
		extratoService.salvarNoExtrato(extratoBancario);
		
		super.salvar(contaBancaria);
	}
	
	
	@Transactional(rollbackFor = Exception.class)
	public void transferir(TransferenciaBancariaDTO dto) {
		
		ContaBancaria contaBancariaSaque = consultarConta(dto.getAgenciaOrigem(), dto.getNumeroContaOrigem());
		
		if(dto.getValor() <= 0) {
			throw new AplicacaoException(ExceptionValidacoes.ERRO_VALOR_INVALIDO);
		}
		
		if(contaBancariaSaque.getSaldo() < dto.getValor()) {
			throw new AplicacaoException(ExceptionValidacoes.ERRO_SALDO_INEXISTENTE);
		}
		
		contaBancariaSaque.setSaldo(contaBancariaSaque.getSaldo() - dto.getValor());
		
		super.salvar(contaBancariaSaque);
		
		ContaBancaria contaBancariaDeposito = consultarConta(dto.getAgenciaDestino(), dto.getNumeroContaDestino());
		contaBancariaDeposito.setSaldo(contaBancariaDeposito.getSaldo() + dto.getValor());
		
		super.salvar(contaBancariaDeposito);
		
		LocalDateTime date = LocalDateTime.now();
		
		ExtratoBancario extratoBancarioContaSaque = new ExtratoBancario();
		
		extratoBancarioContaSaque.setContaExtrato(contaBancariaSaque);
		extratoBancarioContaSaque.setContaSecundaria(contaBancariaDeposito);
		extratoBancarioContaSaque.setDate(date);
		extratoBancarioContaSaque.setTipoOperacao(ExtratoOperacoes.TRANSFERENCIA_ENVIADA);
		extratoBancarioContaSaque.setValorOperacao(dto.getValor());
		extratoService.salvarNoExtrato(extratoBancarioContaSaque);
		
		ExtratoBancario extratoBancarioContaDeposito = new ExtratoBancario();
		
		extratoBancarioContaDeposito.setContaExtrato(contaBancariaDeposito);
		extratoBancarioContaDeposito.setContaSecundaria(contaBancariaSaque);
		extratoBancarioContaDeposito.setDate(date);
		extratoBancarioContaDeposito.setTipoOperacao(ExtratoOperacoes.TRANSFERENCIA_RECEBIDA);
		extratoBancarioContaDeposito.setValorOperacao(dto.getValor());
		extratoService.salvarNoExtrato(extratoBancarioContaDeposito);
		
	}
	

}
