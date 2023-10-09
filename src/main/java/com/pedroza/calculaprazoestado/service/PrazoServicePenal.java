package com.pedroza.calculaprazoestado.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.pedroza.calculaprazoestado.model.dto.PrazoResponseDTO;

@Service
public class PrazoServicePenal extends PrazoService {

	@Override
	public PrazoResponseDTO addNormalDays(LocalDate startDate, int days, String municipio) {		
		return super.addNormalDays(startDate, days, municipio);
	}
	
	

}
