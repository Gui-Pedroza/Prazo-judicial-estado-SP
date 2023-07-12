package com.pedroza.calculaprazoestado.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pedroza.calculaprazoestado.common.FeriadoESuspensaoMerger;
import com.pedroza.calculaprazoestado.common.HttpClient;
import com.pedroza.calculaprazoestado.model.Feriado;
import com.pedroza.calculaprazoestado.model.Suspensao;
import com.pedroza.calculaprazoestado.repository.FeriadoRepository;
import com.pedroza.calculaprazoestado.repository.SuspensaoRepository;

@Service
public class PrazoService {

	private String municipio;
	private String ano;

	// @Autowired
	// HttpClient httpClient;	
	
	public PrazoService() {
		
	}
	public PrazoService(String municipio, String ano) {
		this.municipio = municipio;
		this.ano = ano;
	}

	public String getMunicipio() {
		return this.municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	FeriadoRepository feriadoRepository = new FeriadoRepository(new HttpClient()); // TODO tentar injetar essa porra
	SuspensaoRepository suspensaoRepository = new SuspensaoRepository(new HttpClient()); // ao invees de instanciar

	List<Feriado> feriados = feriadoRepository.getFeriados(ano, municipio);
	List<Suspensao> suspensoes = suspensaoRepository.getSuspensoes(ano, municipio);
	List<LocalDate> feriadosESuspensoes = FeriadoESuspensaoMerger.merge(feriados, suspensoes);

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
		return result;
	}

	public LocalDate diaUtilSubsequente(LocalDate startDate) {
		LocalDate proximoDia = startDate;
		while (isHoliday.or(isWeekend).test(proximoDia)) {
			proximoDia = proximoDia.plusDays(1);
		}
		return proximoDia;
	}

}
