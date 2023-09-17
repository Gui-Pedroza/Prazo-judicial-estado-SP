package com.pedroza.calculaprazoestado.model.dto;

import java.util.List;

public class PrazoResponseDTO {
	
	private String prazoFinal;
	private List<String> descricao;
	
		
	public PrazoResponseDTO() {
		
	}

	public PrazoResponseDTO(String prazoFinal, List<String> descricao) {
		this.prazoFinal = prazoFinal;
		this.descricao = descricao;
	}

	public String getPrazoFinal() {
		return prazoFinal;
	}

	public void setPrazoFinal(String prazoFinal) {
		this.prazoFinal = prazoFinal;
	}

	public List<String> getDescricao() {
		return descricao;
	}

	public void setDescricao(List<String> descricao) {
		this.descricao = descricao;
	}
	
	

}
