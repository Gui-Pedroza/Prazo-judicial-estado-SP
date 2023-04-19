package com.pedroza.calculaprazoestado.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pedroza.calculaprazoestado.service.PrazoService;

@RestController
public class PrazoController {
	
	@Autowired
	PrazoService service;
		
	
	@PostMapping("/{city}")
	public ResponseEntity<LocalDate> calculate(@RequestParam("startDate") LocalDate startDate,
			@RequestParam("daysToAdd") int daysToAdd, @PathVariable("city") String city) {
		service.setAno("2020"); // hard code para teste, após, trocar para argumento a receber via @RequestParam
		service.setMunicipio(city); 
		service.loadHolidays();
		LocalDate newDate = service.addBusinessDays(startDate, daysToAdd);
		return new ResponseEntity<LocalDate>(newDate, HttpStatus.OK);
	}

}

