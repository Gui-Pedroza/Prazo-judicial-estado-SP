package com.pedroza.calculaprazoestado.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pedroza.calculaprazoestado.model.dto.PrazoResponseDTO;
import com.pedroza.calculaprazoestado.service.PrazoService;


@RestController
public class PrazoController {
	
	@Autowired
	PrazoService prazoServiceCivel;		
	
	@PostMapping("/{city}")
	public ResponseEntity<PrazoResponseDTO> calculate(			
			@RequestParam("startDate") LocalDate startDate,
			@RequestParam("daysToAdd") int daysToAdd, 
			@PathVariable("city") String city
			) {		
		PrazoResponseDTO resultado = prazoServiceCivel.addBusinessDays(startDate, daysToAdd, city);
		return new ResponseEntity<PrazoResponseDTO>(resultado, HttpStatus.OK);
	}
	
	

}

