
package com.pedroza.calculaprazoestado.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Suspensao {
	private String description;
	private LocalDate initialDate;
	private LocalDate finalDate;

	public Suspensao() {

	}

	public Suspensao(String description, LocalDate initialDate, LocalDate finalDate) {
		super();
		this.description = description;
		this.initialDate = initialDate;
		this.finalDate = finalDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getInitialDate() {
		return initialDate;
	}

	public void setInitialDate(LocalDate initialDate) {
		this.initialDate = initialDate;
	}

	public LocalDate getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(LocalDate finalDate) {
		this.finalDate = finalDate;
	}
		

	@Override
	public String toString() {
		return "Suspensao [description=" + description + ", initialDate=" + initialDate + ", finalDate=" + finalDate
				+ "]";
	}

	public List<LocalDate> getPeriodoSuspensao() {
		List<LocalDate> dates = new ArrayList<>();
		LocalDate date = this.initialDate;
		while (date.isBefore(this.finalDate)) {
			dates.add(date);
			date = date.plusDays(1);
		}
		return dates;
	}

}
