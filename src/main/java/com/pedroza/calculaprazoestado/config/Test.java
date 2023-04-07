package com.pedroza.calculaprazoestado.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.pedroza.calculaprazoestado.repository.FeriadoRepository;

@Configuration
@Profile("teste")
public class Test implements CommandLineRunner {
	
	@Autowired
	FeriadoRepository feriadoRepository;

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println(feriadoRepository.getFeriados("2023", "Ribeirão Preto"));
	}

}
