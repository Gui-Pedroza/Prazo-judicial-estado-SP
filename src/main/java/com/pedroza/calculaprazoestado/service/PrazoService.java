package com.pedroza.calculaprazoestado.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pedroza.calculaprazoestado.common.FeriadoESuspensaoMerger;
import com.pedroza.calculaprazoestado.common.util.DataFormatter;
import com.pedroza.calculaprazoestado.common.util.CollectionsHandler;
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
		var prazoDTO = new PrazoResponseDTO();
		
		Set<LocalDate> feriadosESuspensoes = getMergedFeriadosESuspensoes(ano, municipio);
		/* 
		 * Em caráter transitório, devido a suspensão de prazos do mes inteiro de outubro
		 * foi necessário remover as datas de suspensões manualmente (via classe utilitária CollectionsHandler)
		 * já que a suspensão não se aplica ao cível, conforme as 3 linhas abaixo
		 *  
		 */
		Set<LocalDate> feriadosESuspensoesSemOutubro = CollectionsHandler.removeFullMonth(feriadosESuspensoes, Month.OCTOBER);
		CollectionsHandler.addDate(feriadosESuspensoesSemOutubro, LocalDate.of(2023, 10, 12));
		CollectionsHandler.addDate(feriadosESuspensoesSemOutubro, LocalDate.of(2023, 10, 13));
		
		// o programa irá carregar os feriados do ano corrente e do ano seguinte:
		feriadosESuspensoesSemOutubro.addAll(getMergedFeriadosESuspensoes(ano +1, municipio));
				
		LocalDate result = diaUtilSubsequente(startDate, feriadosESuspensoesSemOutubro);		
		while (days > 0) {
			result = result.plusDays(1);
			boolean isWeekend = isWeekend(result);
			boolean isHoliday = isHoliday(result, feriadosESuspensoesSemOutubro);
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
		/*
		 * Neste trecho, foi necessário também remover a descriçào do período do mês de outubro
		 * Foi feita a remoção via classe utilitária da descricao
		 * */
		String descricaoOutubroRemover = "03-10-2023 a 31-10-2023";
		CollectionsHandler.removeElementFromList(descricao, descricaoOutubroRemover);		
		prazoDTO.setPrazoFinal(DataFormatter.formatoBRextenso(result));
		prazoDTO.setDescricao(descricao);
		return prazoDTO;
	}
	
	public PrazoResponseDTO addNormalDays(LocalDate startDate, int days, String municipio) {
		var prazoDTO = new PrazoResponseDTO();
		List<String> feriadoESuspensaoDescricao = new ArrayList<>();
		int ano = startDate.getYear();
		Set<LocalDate> feriadosESuspensoes = getMergedFeriadosESuspensoes(ano, municipio);
		if (startDate.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
			startDate = startDate.plusDays(1);
			startDate = diaUtilSubsequente(startDate, feriadosESuspensoes);
		}
		LocalDate finalDate = startDate;
		// o programa irá carregar os feriados do ano corrente e do ano seguinte:
		feriadosESuspensoes.addAll(getMergedFeriadosESuspensoes(ano +1, municipio));
		while (days > 0) {
			finalDate = finalDate.plusDays(1);
			days--;
		}
		if (feriadosESuspensoes.contains(finalDate) || isWeekend(finalDate)) {
			if (isWeekend(finalDate)) {
				String fds = finalDate.getDayOfWeek().equals(DayOfWeek.SATURDAY) ? "Sábado" : "Domingo";				
				String prazoFinalFDS = "O prazo final caiu em um " + fds + ". O resultado acima é o dia útil subsequente";
				feriadoESuspensaoDescricao.add(prazoFinalFDS);
			} else if (feriadosESuspensoes.contains(finalDate)) {				
				String prazoFinalEmFeriado = "O prazo final caiu em feriado ou suspensao. O resultado acima é o dia útil subsequente";
				feriadoESuspensaoDescricao.add(prazoFinalEmFeriado);
			}

			
			finalDate = diaUtilSubsequente(finalDate, feriadosESuspensoes);
			
		}
		prazoDTO.setPrazoFinal(DataFormatter.formatoBRextenso(finalDate));
		prazoDTO.setDescricao(feriadoESuspensaoDescricao);		
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
		// esse laço while fará uma verredura dentro do período pesquisado:
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
