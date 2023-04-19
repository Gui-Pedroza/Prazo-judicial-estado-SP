package com.pedroza.calculaprazoestado.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pedroza.calculaprazoestado.common.FeriadoESuspensaoMerger;
import com.pedroza.calculaprazoestado.common.HttpClient;
import com.pedroza.calculaprazoestado.model.Feriado;
import com.pedroza.calculaprazoestado.model.Suspensao;
import com.pedroza.calculaprazoestado.repository.FeriadoRepository;
import com.pedroza.calculaprazoestado.repository.SuspensaoRepository;

@Service
public class PrazoService {
	
	private String municipio;
	private String ano;	
	
	@Autowired
    HttpClient mHttpClient;
	
	public PrazoService(String municipio, String ano) {
		this.municipio = municipio;
		this.ano = ano;
	}	
	
	public String getMunicipio() {return this.municipio;}
	public void setMunicipio(String municipio) {this.municipio = municipio;}

	public String getAno() {return ano;}
	public void setAno(String ano) {this.ano = ano;}
	
	FeriadoRepository feriadoRepository = new FeriadoRepository(mHttpClient);
	SuspensaoRepository suspensaoRepository = new SuspensaoRepository(mHttpClient);
	
	
	// meter tudo List ou voltar para ArrayList
	List<Feriado> feriados = feriadoRepository.getFeriados("2023", "Ribeirão Preto");
	List<Suspensao> suspensoes = suspensaoRepository.getSuspensoes("2023", "Ribeirão Preto");
    List<LocalDate> feriadosESuspensoes = FeriadoESuspensaoMerger.merge(feriados, suspensoes);
	
	
	
	
	
	
	

}
