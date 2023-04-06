package com.pedroza.calculaprazoestado.model;

import java.time.LocalDate;

public class Feriado {
	private String description;
	private LocalDate date;
	
	public Feriado(String description, LocalDate date) {	
		this.description = description;
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}	
	

}