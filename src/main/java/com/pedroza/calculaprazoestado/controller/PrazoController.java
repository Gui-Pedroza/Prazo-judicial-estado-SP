package com.pedroza.calculaprazoestado.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pedroza.calculaprazoestado.service.PrazoService;


@Controller
public class PrazoController {
	
	@Autowired
	PrazoService prazoService;		
	
	@PostMapping("/{city}")
	public ResponseEntity<LocalDate> calculate(
			@RequestParam("ano") String ano,
			@RequestParam("startDate") LocalDate startDate,
			@RequestParam("daysToAdd") int daysToAdd, 
			@PathVariable("city") String city
			) {
		prazoService.setAno(ano);
		prazoService.setMunicipio(city);		
		LocalDate newDate = prazoService.addBusinessDays(startDate, daysToAdd);
		return new ResponseEntity<LocalDate>(newDate, HttpStatus.OK);
	}

}

