package com.pedroza.calculaprazoestado.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pedroza.calculaprazoestado.common.FeriadoESuspensaoMerger;
import com.pedroza.calculaprazoestado.repository.FeriadoRepository;
import com.pedroza.calculaprazoestado.repository.SuspensaoRepository;

@Service
public class PrazoService {

	
	private String ano;
	private String municipio;
		
	@Autowired
	FeriadoRepository feriadoRepository;
	
	@Autowired
    SuspensaoRepository suspensaoRepository;
	
    List<LocalDate> feriadosESuspensoes;
    
		
	public PrazoService() {
		
	}	
			
	public PrazoService(String ano, String municipio, FeriadoRepository feriadoRepository,
			SuspensaoRepository suspensaoRepository, List<LocalDate> feriadosESuspensoes) {
		super();
		this.ano = ano;
		this.municipio = municipio;
		this.feriadoRepository = feriadoRepository;
		this.suspensaoRepository = suspensaoRepository;
		this.feriadosESuspensoes = loadFeriadosESuspensoes(ano, municipio);;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}


	private Predicate<LocalDate> isWeekend = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
			|| date.getDayOfWeek() == DayOfWeek.SUNDAY;

	private Predicate<LocalDate> isHoliday = date -> feriadosESuspensoes.contains(date);

	
	public LocalDate addBusinessDays(LocalDate startDate, int days) {

		LocalDate result = diaUtilSubsequente(startDate);
		while (days > 0) {
			result = result.plusDays(1);
			if (isHoliday.or(isWeekend).negate().test(result)) {
				days--;
			}
		}
		System.out.println(feriadosESuspensoes); // SAÍDA DE TESTE
		return result;
	}

	public LocalDate diaUtilSubsequente(LocalDate startDate) {
		LocalDate proximoDia = startDate;
		while (isHoliday.or(isWeekend).test(proximoDia)) {
			proximoDia = proximoDia.plusDays(1);
		}
		return proximoDia;
	}
	
	public List<LocalDate> loadFeriadosESuspensoes (String ano, String municipio) {
		return FeriadoESuspensaoMerger
		.merge(feriadoRepository.getFeriados(ano, municipio), suspensaoRepository.getSuspensoes(ano, municipio));
	}

}
