package com.pedroza.calculaprazoestado.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.pedroza.calculaprazoestado.common.util.DataFormatter;
import com.pedroza.calculaprazoestado.model.dto.PrazoResponseDTO;

@Service
public class PrazoServicePenal extends PrazoService {

	@Override
	public PrazoResponseDTO addNormalDays(LocalDate startDate, int days, String municipio) {		
		return super.addNormalDays(startDate, days, municipio);
	}
	
	

}
