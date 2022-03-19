package com.indracompany.treinamento.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.indracompany.treinamento.exception.AplicacaoException;
import com.indracompany.treinamento.exception.ExceptionValidacoes;
import com.indracompany.treinamento.model.dto.ClienteDTO;
import com.indracompany.treinamento.model.entity.Cliente;
import com.indracompany.treinamento.model.repository.ClienteRepository;
import com.indracompany.treinamento.util.CpfUtil;

@Service
public class ClienteService extends GenericCrudService<Cliente, Long, ClienteRepository>{

	public ClienteDTO buscarClientePorCpf(String cpf) {
		
		boolean cpfValido = cpf != null && CpfUtil.validaCPF(cpf);
		
		if(!cpfValido) {
			throw new AplicacaoException(ExceptionValidacoes.ERRO_CPF_INVALIDO);
		}
		
		Cliente c = repository.findTopByCpfOrderByIdDesc(cpf);
		
		if(c == null) {
			throw new AplicacaoException(ExceptionValidacoes.ALERTA_NENHUM_REGISTRO_ENCONTRADO);
		}
		
		ClienteDTO retorno = new ClienteDTO();
		retorno.setEmail(c.getEmail());
		retorno.setNome(c.getNome());
		
		return retorno;
	}
	
	public List<ClienteDTO> buscarClientePorCpfLista(String cpf) {
		
		boolean cpfValido = cpf != null && CpfUtil.validaCPF(cpf);
		
		if(!cpfValido) {
			throw new AplicacaoException(ExceptionValidacoes.ERRO_CPF_INVALIDO);
		}
		
		List<Cliente> clientes = repository.findByCpf(cpf);
		
		if(clientes == null || clientes.isEmpty()) {
			throw new AplicacaoException(ExceptionValidacoes.ALERTA_NENHUM_REGISTRO_ENCONTRADO);
		}
		
		List<ClienteDTO> retorno = new ArrayList<ClienteDTO>();
		for(Cliente c: clientes) {
			ClienteDTO dto = new ClienteDTO();
			dto.setEmail(c.getEmail());
			dto.setNome(c.getNome());
			retorno.add(dto);
		}
		
		return retorno;
	}
	
	public List<ClienteDTO> buscarClientePorNome(String nome){
		
		boolean nomeValido = nome != null;
		
		if(!nomeValido) {
			throw new AplicacaoException(ExceptionValidacoes.ERRO_OBJETO_NAO_ENCONTRADO);
		}
		
		List<Cliente> clientes = repository.findByNomeContaining(nome);
		
		if(clientes == null || clientes.isEmpty()) {
			throw new AplicacaoException(ExceptionValidacoes.ALERTA_NENHUM_REGISTRO_ENCONTRADO);
		}
		
		List<ClienteDTO> retorno = new ArrayList<ClienteDTO>();
		for(Cliente c: clientes) {
			ClienteDTO dto = new ClienteDTO();
			dto.setNome(c.getNome());
			dto.setEmail(c.getEmail());
			retorno.add(dto);
		}
		
		return retorno;
		
	}
	
}
