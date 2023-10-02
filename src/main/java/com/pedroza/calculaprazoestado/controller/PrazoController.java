package com.pedroza.calculaprazoestado.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pedroza.calculaprazoestado.model.dto.PrazoRequestDTO;
import com.pedroza.calculaprazoestado.model.dto.PrazoResponseDTO;
import com.pedroza.calculaprazoestado.service.PrazoServiceCivel;
import com.pedroza.calculaprazoestado.service.PrazoServicePenal;


@RestController
public class PrazoController {
	
	@Autowired
	PrazoServiceCivel prazoServiceCivel;
	
	@Autowired
	PrazoServicePenal prazoServicePenal;
	
	@PostMapping("/civel/{city}")
	public ResponseEntity<PrazoResponseDTO> calculateCivel(			
			@RequestParam("startDate") LocalDate startDate,
			@RequestParam("daysToAdd") int daysToAdd, 
			@PathVariable("city") String city
			) {		
		PrazoResponseDTO resultado = prazoServiceCivel.addBusinessDays(startDate, daysToAdd, city);
		return new ResponseEntity<PrazoResponseDTO>(resultado, HttpStatus.OK);
	}
	
	@PostMapping("/penal/{city}")
	public ResponseEntity<PrazoResponseDTO> calculatePenal(
			@RequestBody PrazoRequestDTO requestObj,
			@PathVariable("city") String city) {
		LocalDate startDate = requestObj.getStartDate();
		int daysToAdd = requestObj.getdaysToAdd();	
		System.out.println("StartDate: " + startDate);
	    System.out.println("DaysToAdd: " + daysToAdd);
		PrazoResponseDTO resultado = prazoServicePenal.addNormalDays(startDate, daysToAdd, city);
		return new ResponseEntity<PrazoResponseDTO>(resultado, HttpStatus.OK);
	}
	

}

