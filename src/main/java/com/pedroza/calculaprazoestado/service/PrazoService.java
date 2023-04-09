package com.pedroza.calculaprazoestado.service;

import org.springframework.stereotype.Service;

@Service
public class PrazoService {
	
	private String municipio;
	private String ano;
	
	public PrazoService(String municipio, String ano) {
		this.municipio = municipio;
		this.ano = ano;
	}
	
	
	public String getMunicipio() {return this.municipio;}
	public void setMunicipio(String municipio) {this.municipio = municipio;}

	public String getAno() {return ano;}
	public void setAno(String ano) {this.ano = ano;}
	
	
	
	

}
