package com.pedroza.calculaprazoestado.config;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.pedroza.calculaprazoestado.common.FeriadoESuspensaoMerger;
import com.pedroza.calculaprazoestado.model.Feriado;
import com.pedroza.calculaprazoestado.repository.FeriadoRepository;
import com.pedroza.calculaprazoestado.repository.SuspensaoRepository;
import com.pedroza.calculaprazoestado.service.PrazoService;

@Configuration
@Profile("teste")
public class Test implements CommandLineRunner {
	
	@Autowired
	FeriadoRepository feriadoRepository;
	
	@Autowired
	SuspensaoRepository suspensaoRepository;
	
	@Autowired
	PrazoService prazoService;

	@Override
	public void run(String... args) throws Exception {
		
		
		
		List<LocalDate> feriadosESuspensoes = FeriadoESuspensaoMerger
				.merge(feriadoRepository.getFeriados("2021", "Bauru"), suspensaoRepository.getSuspensoes("2021", "Bauru"));
		
		List<LocalDate> holydaysList = feriadoRepository.getFeriados("2021", "Bauru")
				.stream()
				.map(d -> d.getDate())
				.collect(Collectors.toList());
		
		List<LocalDate> datasIniciais = suspensaoRepository.getSuspensoes("2020", "Lins")
				.stream()
				.map(d -> d.getInitialDate())
				.collect(Collectors.toList());
		
		List<Feriado> feriados = feriadoRepository.getFeriados("2021", "Bauru");
		
		// System.out.println(datasIniciais);
		// System.out.println(holydaysList);
		System.out.println(feriadosESuspensoes);
	}

}
