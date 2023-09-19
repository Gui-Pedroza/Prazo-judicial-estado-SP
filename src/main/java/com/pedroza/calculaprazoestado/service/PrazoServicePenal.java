package com.pedroza.calculaprazoestado.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.pedroza.calculaprazoestado.common.util.DataFormatter;
import com.pedroza.calculaprazoestado.model.dto.PrazoResponseDTO;

@Service
public class PrazoServicePenal extends PrazoService {
	
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
				String prazoFinalFDS = "O prazo final em " + fds + " será prorrogado para o próximo dia útil subsequente";
				feriadoESuspensaoDescricao.add(prazoFinalFDS);
			} else if (feriadosESuspensoes.contains(finalDate)) {
				// TODO: CAPTURAR A DESCRIÇÃO
				String prazoFinalEmFeriado = "O prazo final em feriado/suspensao. Prazo prorrogado para o próximo dia útil subsequente";
				feriadoESuspensaoDescricao.add(prazoFinalEmFeriado);
			}

			
			finalDate = diaUtilSubsequente(finalDate, feriadosESuspensoes);
			
		}
		prazoDTO.setPrazoFinal(DataFormatter.formatoBRextenso(finalDate));
		prazoDTO.setDescricao(feriadoESuspensaoDescricao);
		return prazoDTO;		
	}

}
