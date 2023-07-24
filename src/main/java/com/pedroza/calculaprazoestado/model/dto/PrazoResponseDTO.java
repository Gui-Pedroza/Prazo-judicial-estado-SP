package com.pedroza.calculaprazoestado.model.dto;

import java.time.LocalDate;
import java.util.List;

public class PrazoResponseDTO {
	
	private LocalDate prazoFinal;
	private List<String> descricao;
	
	
	
	public PrazoResponseDTO() {
		
	}

	public PrazoResponseDTO(LocalDate prazoFinal, List<String> descricao) {
		this.prazoFinal = prazoFinal;
		this.descricao = descricao;
	}

	public LocalDate getPrazoFinal() {
		return prazoFinal;
	}

	public void setPrazoFinal(LocalDate prazoFinal) {
		this.prazoFinal = prazoFinal;
	}

	public List<String> getDescricao() {
		return descricao;
	}

	public void setDescricao(List<String> descricao) {
		this.descricao = descricao;
	}
	
	

}
