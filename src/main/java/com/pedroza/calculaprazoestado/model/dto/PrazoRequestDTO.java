package com.pedroza.calculaprazoestado.model.dto;

import java.time.LocalDate;

public class PrazoRequestDTO {
    
    public LocalDate startDate;
    public int daysToAdd;
           
    public PrazoRequestDTO() {
		
	}

	public PrazoRequestDTO(LocalDate startDate, int daysToAdd) {
        this.startDate = startDate;
        this.daysToAdd = daysToAdd;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public int getdaysToAdd() {
        return daysToAdd;
    }

    public void setStartDate(LocalDate starDate) {
        this.startDate = starDate;
    }

    public void setdaysToAddtoAdd(int daysToAdd) {
        this.daysToAdd = daysToAdd;
    }

}
