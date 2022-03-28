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
		
		contaBancaria.setSaldo(contaBancaria.getSaldo() + valor);
		
		LocalDateTime date = LocalDateTime.now();
		
		ExtratoBancario extratoBancario = new ExtratoBancario();
		
		extratoBancario.setAgenciaOrigem(contaBancaria.getAgencia());
		extratoBancario.setNumeroOrigem(contaBancaria.getNumero());
		extratoBancario.setContaBancaria(contaBancaria);
		extratoBancario.setDate(date);
		extratoBancario.setTipoOperacao(ExtratoOperacoes.DEPOSITO);
		extratoBancario.setValorOperacao(valor);
		
		extratoService.salvarNoExtrato(extratoBancario);
		
		super.salvar(contaBancaria);
	}
	
	public void sacar(String agencia, String numero, double valor) {
		
		ContaBancaria contaBancaria = consultarConta(agencia, numero);
		
		if(contaBancaria.getSaldo() < valor) {
			throw new AplicacaoException(ExceptionValidacoes.ERRO_SALDO_INEXISTENTE);
		}
		
		contaBancaria.setSaldo(contaBancaria.getSaldo() - valor);
		
		LocalDateTime date = LocalDateTime.now();
		
		ExtratoBancario extratoBancario = new ExtratoBancario();
		
		extratoBancario.setAgenciaOrigem(contaBancaria.getAgencia());
		extratoBancario.setNumeroOrigem(contaBancaria.getNumero());
		extratoBancario.setContaBancaria(contaBancaria);
		extratoBancario.setDate(date);
		extratoBancario.setTipoOperacao(ExtratoOperacoes.SAQUE);
		extratoBancario.setValorOperacao(valor);
		
		extratoService.salvarNoExtrato(extratoBancario);
		
		super.salvar(contaBancaria);
	}
	
	
	@Transactional(rollbackFor = Exception.class)
	public void transferir(TransferenciaBancariaDTO dto) {
		
		ContaBancaria contaBancariaSaque = consultarConta(dto.getAgenciaOrigem(), dto.getNumeroContaOrigem());
		
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
		
		extratoBancarioContaSaque.setAgenciaOrigem(contaBancariaDeposito.getAgencia());
		extratoBancarioContaSaque.setNumeroOrigem(contaBancariaDeposito.getNumero());
		extratoBancarioContaSaque.setContaBancaria(contaBancariaSaque);
		extratoBancarioContaSaque.setDate(date);
		extratoBancarioContaSaque.setTipoOperacao(ExtratoOperacoes.TRANSFERENCIA);
		extratoBancarioContaSaque.setValorOperacao(dto.getValor());
		
		ExtratoBancario extratoBancarioContaDeposito = new ExtratoBancario();
		
		extratoBancarioContaDeposito.setAgenciaOrigem(contaBancariaSaque.getAgencia());
		extratoBancarioContaDeposito.setNumeroOrigem(contaBancariaSaque.getNumero());
		extratoBancarioContaDeposito.setContaBancaria(contaBancariaDeposito);
		extratoBancarioContaDeposito.setDate(date);
		extratoBancarioContaDeposito.setTipoOperacao(ExtratoOperacoes.TRANSFERENCIA);
		extratoBancarioContaDeposito.setValorOperacao(dto.getValor());
		
		extratoService.salvarNoExtrato(extratoBancarioContaSaque);
		extratoService.salvarNoExtrato(extratoBancarioContaDeposito);
		
	}
	

}
