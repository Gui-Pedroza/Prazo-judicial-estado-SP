package com.pedroza.calculaprazoestado.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.pedroza.calculaprazoestado.model.dto.PrazoResponseDTO;

@Service
public class PrazoServiceCivel extends PrazoService {

	@Override
	public PrazoResponseDTO addBusinessDays(LocalDate startDate, int days, String municipio) {		
		return super.addBusinessDays(startDate, days, municipio);
	}
	
	 

}
