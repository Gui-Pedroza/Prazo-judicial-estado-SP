package com.pedroza.calculaprazoestado.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pedroza.calculaprazoestado.common.FeriadoESuspensaoMerger;
import com.pedroza.calculaprazoestado.model.Feriado;
import com.pedroza.calculaprazoestado.model.dto.PrazoResponseDTO;
import com.pedroza.calculaprazoestado.repository.FeriadoRepository;
import com.pedroza.calculaprazoestado.repository.SuspensaoRepository;

@Service
public class PrazoService {
		
			
	@Autowired
	FeriadoRepository feriadoRepository;
	
	@Autowired
    SuspensaoRepository suspensaoRepository;	
	
			
	public PrazoService() {
		
	}	
			
	public PrazoService(FeriadoRepository feriadoRepository, SuspensaoRepository suspensaoRepository) {
        this.feriadoRepository = feriadoRepository;
        this.suspensaoRepository = suspensaoRepository;        
    }
	
	private boolean isWeekend(LocalDate date) {
		return date.getDayOfWeek() == DayOfWeek.SATURDAY
				|| date.getDayOfWeek() == DayOfWeek.SUNDAY;
	}
	
	private boolean isHoliday(LocalDate date, Set<LocalDate> feriadosESuspensoes) {
		return feriadosESuspensoes.contains(date);
	}
           
    	
	public PrazoResponseDTO addBusinessDays(LocalDate startDate, int days, String municipio) {
		String ano = String.valueOf(startDate.getYear());
		PrazoResponseDTO prazoDTO = new PrazoResponseDTO();
		Set<LocalDate> feriadosESuspensoes = getMergedFeriadosESuspensoes(ano, municipio);		
		
		boolean isStartDateWeekend = isWeekend(startDate);
		boolean isStartDateHoliday = isHoliday(startDate, feriadosESuspensoes);
		
		LocalDate result = diaUtilSubsequente(startDate, isStartDateWeekend, isStartDateHoliday, feriadosESuspensoes);
		while (days > 0) {
			boolean isWeekend = isWeekend(result);
			boolean isHoliday = isHoliday(result, feriadosESuspensoes);
			result = result.plusDays(1);
			if (isHoliday || isWeekend) {
				days--;
			}
		}
		List<String> descricao = getFeriadoDescricao(startDate, result, ano, municipio);
		prazoDTO.setPrazoFinal(result);
		prazoDTO.setDescricao(descricao);
		return prazoDTO;
	}

	public LocalDate diaUtilSubsequente(LocalDate startDate, boolean isWeekend, boolean isHoliday, Set<LocalDate> feriadosESuspensoes) {
		LocalDate proximoDia = startDate;
		while (isHoliday(proximoDia, feriadosESuspensoes) || isWeekend(proximoDia)) {
			proximoDia = proximoDia.plusDays(1);
		}
		return proximoDia;
	}
	
	private Set<LocalDate> getMergedFeriadosESuspensoes(String ano, String municipio) {
        return FeriadoESuspensaoMerger.merge(
            feriadoRepository.getFeriados(ano, municipio),
            suspensaoRepository.getSuspensoes(ano, municipio)
        );
    }
	
	
	// testar saporra: 
	private List<String> getFeriadoDescricao(
			LocalDate diaInicial, 
			LocalDate diaFinal, 
			String ano, 
			String municipio
			) {
		List<Feriado> feriados = feriadoRepository.getFeriados(ano, municipio);
		List<String> descricoes = new ArrayList<>();
		LocalDate dia = diaInicial;
		while (dia.isBefore(diaFinal)) {
			
			for (int i = 0; i < feriados.size(); i++) {
				if (feriados.get(i).getDate().equals(dia)) {
					descricoes.add(feriados.get(i).getDescription());
				}
			}
			dia = dia.plusDays(1);
		}
		
		return descricoes;
	}
	
}
