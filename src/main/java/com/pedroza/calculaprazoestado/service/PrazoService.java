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
    
       
    	
	public LocalDate addBusinessDays(LocalDate startDate, int days, String municipio) {
		String ano = String.valueOf(startDate.getYear());
		List<LocalDate> feriadosESuspensoes = getMergedFeriadosESuspensoes(ano, municipio);
		Predicate<LocalDate> isWeekend = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
				|| date.getDayOfWeek() == DayOfWeek.SUNDAY;

		Predicate<LocalDate> isHoliday = date -> feriadosESuspensoes.contains(date);
		LocalDate result = diaUtilSubsequente(startDate, isWeekend, isHoliday);
		while (days > 0) {
			result = result.plusDays(1);
			if (isHoliday.or(isWeekend).negate().test(result)) {
				days--;
			}
		}		
		return result;
	}

	public LocalDate diaUtilSubsequente(LocalDate startDate, Predicate<LocalDate> isWeekend, Predicate<LocalDate> isHoliday) {
		LocalDate proximoDia = startDate;
		while (isHoliday.or(isWeekend).test(proximoDia)) {
			proximoDia = proximoDia.plusDays(1);
		}
		return proximoDia;
	}
	
	private List<LocalDate> getMergedFeriadosESuspensoes(String ano, String municipio) { // TODO : fazer SET?
        return FeriadoESuspensaoMerger.merge(
            feriadoRepository.getFeriados(ano, municipio),
            suspensaoRepository.getSuspensoes(ano, municipio)
        );
    }
	
}
