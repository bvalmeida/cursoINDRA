package com.indracompany.treinamento.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.indracompany.treinamento.model.dto.ExtratoBancarioDTO;
import com.indracompany.treinamento.model.entity.ExtratoBancario;
import com.indracompany.treinamento.model.service.ExtratoService;

@RestController
@RequestMapping("rest/extratos")
public class ExtratoBancarioRest extends GenericCrudRest<ExtratoBancario, Long, ExtratoService> {
	
	@Autowired
	private ExtratoService extratoService;
	
	@GetMapping(value = "/consultar-extratos/{agencia}/{numero}/{dataInicio}/{dataFinal}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<List<ExtratoBancarioDTO>> consultarExtrato(@PathVariable String agencia, String numero, String dataInicio, String dataFinal){
		List<ExtratoBancarioDTO> listExtratoBancarioDTO = extratoService.obterExtratos(agencia, numero, dataInicio, dataFinal);
		
		return new ResponseEntity<List<ExtratoBancarioDTO>>(listExtratoBancarioDTO, HttpStatus.OK);
	}
	
}
