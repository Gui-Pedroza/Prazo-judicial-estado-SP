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
	public ResponseEntity<String> calculate(
			@RequestParam("ano") String ano,
			@RequestParam("startDate") LocalDate startDate,
			@RequestParam("daysToAdd") int daysToAdd, 
			@PathVariable("city") String city
			) {		
		LocalDate newDate = prazoService.addBusinessDays(startDate, daysToAdd, city);
		return new ResponseEntity<String>(newDate.toString(), HttpStatus.OK);
	}

}

