package com.pedroza.calculaprazoestado.config;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.pedroza.calculaprazoestado.model.dto.PrazoResponseDTO;
import com.pedroza.calculaprazoestado.repository.FeriadoRepository;
import com.pedroza.calculaprazoestado.repository.SuspensaoRepository;
import com.pedroza.calculaprazoestado.service.PrazoServicePenal;

@Configuration
@Profile("teste")
public class Test implements CommandLineRunner {
	
	@Autowired
	FeriadoRepository feriadoRepository;
	
	@Autowired
	SuspensaoRepository suspensaoRepository;
	
	@Autowired
	PrazoServicePenal prazoServicePenal;	

	@Override
	public void run(String... args) throws Exception {		
		var dto = new PrazoResponseDTO();
		LocalDate startDate = LocalDate.of(2023, 10, 02);		
		dto = prazoServicePenal.addNormalDays(startDate, 5, "Ribeirão Preto");			
	}

}
