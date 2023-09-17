package com.pedroza.calculaprazoestado.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pedroza.calculaprazoestado.common.FeriadoESuspensaoMerger;
import com.pedroza.calculaprazoestado.common.util.DataFormatter;
import com.pedroza.calculaprazoestado.model.Feriado;
import com.pedroza.calculaprazoestado.model.Suspensao;
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
	
	protected boolean isWeekend(LocalDate date) {
		return date.getDayOfWeek() == DayOfWeek.SATURDAY
				|| date.getDayOfWeek() == DayOfWeek.SUNDAY;
	}
	
	protected boolean isHoliday(LocalDate date, Set<LocalDate> feriadosESuspensoes) {
		return feriadosESuspensoes.contains(date);
	}
	           
    	
	public PrazoResponseDTO addBusinessDays(LocalDate startDate, int days, String municipio) {
		int ano = startDate.getYear();
		PrazoResponseDTO prazoDTO = new PrazoResponseDTO();
		Set<LocalDate> feriadosESuspensoes = getMergedFeriadosESuspensoes(ano, municipio);
		// o programa irá carregar os feriados do ano corrente e do ano seguinte:
		feriadosESuspensoes.addAll(getMergedFeriadosESuspensoes(ano +1, municipio));
				
		LocalDate result = diaUtilSubsequente(startDate, feriadosESuspensoes);		
		while (days > 0) {
			result = result.plusDays(1);
			boolean isWeekend = isWeekend(result);
			boolean isHoliday = isHoliday(result, feriadosESuspensoes);
			if (!(isHoliday || isWeekend)) {
				days--;
			}			
		}
		List<String> descricao = new ArrayList<>();
		if (result.getYear() > startDate.getYear()) {			
			descricao.addAll(getDescricoes(startDate, result, ano, municipio));
			descricao.addAll(getDescricoes(startDate, result, ano + 1, municipio));
		} else {
			descricao = getDescricoes(startDate, result, ano, municipio);			
		}
		prazoDTO.setPrazoFinal(DataFormatter.formatoBRextenso(result));
		prazoDTO.setDescricao(descricao);
		return prazoDTO;
	}
	
	protected LocalDate diaUtilSubsequente(LocalDate startDate, Set<LocalDate> feriadosESuspensoes) {
		LocalDate proximoDia = startDate;
		while (isHoliday(proximoDia, feriadosESuspensoes) || isWeekend(proximoDia)) {
			proximoDia = proximoDia.plusDays(1);
		}
		return proximoDia;
	}
	
	protected Set<LocalDate> getMergedFeriadosESuspensoes(int ano, String municipio) {
        return FeriadoESuspensaoMerger.merge(
            feriadoRepository.getFeriados(ano, municipio),
            suspensaoRepository.getSuspensoes(ano, municipio)
        );
    }	
	
	// captura a descrição dos feriados dentro do período pesquisado
	protected List<String> getDescricoes(
			LocalDate diaInicial, 
			LocalDate diaFinal, 
			int ano, 
			String municipio
			) {
		
		List<Feriado> feriados = feriadoRepository.getFeriados(ano, municipio);
		List<Suspensao> suspensoes = suspensaoRepository.getSuspensoes(ano, municipio);
		
		List<String> feriadoDescricoes = new ArrayList<>();
		List<String> suspensaoDescricoes = new ArrayList<>();
		
		List<String> descricoesFeriadoESuspensao = new ArrayList<>();
		LocalDate dia = diaInicial;
		while (dia.isBefore(diaFinal)) {
			// captura descricoes de feriados:
			for (int i = 0; i < feriados.size(); i++) { 
				if (feriados.get(i).getDate().equals(dia) 
						&& !isWeekend(feriados.get(i).getDate())) { // verifica se feriado coincide com final de semana para não exibir				
					feriadoDescricoes.add(DataFormatter.formatoBR(feriados.get(i).getDate())
							+ " - " + feriados.get(i).getDescription());
				}
			}
			// captura descricoes de suspensoes
			for (int i = 0; i < suspensoes.size(); i++) {
				if (suspensoes.get(i).getInitialDate().equals(dia) 
						&& suspensoes.get(i).getInitialDate().equals(suspensoes.get(i).getFinalDate())) {
					suspensaoDescricoes.add(DataFormatter.formatoBR(suspensoes.get(i).getInitialDate())
					+ " - " + suspensoes.get(i).getDescription());
				} else if (suspensoes.get(i).getInitialDate().equals(dia)) {
					suspensaoDescricoes.add(DataFormatter.formatoBR(suspensoes.get(i).getInitialDate())
							+ " a " + DataFormatter.formatoBR(suspensoes.get(i).getFinalDate())
							+ " - " + suspensoes.get(i).getDescription());
				}
			}
			dia = dia.plusDays(1);
		}
		descricoesFeriadoESuspensao.addAll(feriadoDescricoes);
		descricoesFeriadoESuspensao.addAll(suspensaoDescricoes);		
		return descricoesFeriadoESuspensao;
	}		
	
}
