package com.pedroza.calculaprazoestado.common;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.pedroza.calculaprazoestado.model.Feriado;
import com.pedroza.calculaprazoestado.model.Suspensao;

@Component
public class FeriadoESuspensaoMerger {

   
	public static Set<LocalDate> merge(List<Feriado> feriados, List<Suspensao> suspensoes) {
		final List<LocalDate> mFeriados = feriados.stream().map(feriado -> feriado.getDate()).collect(Collectors.toList());
        final List<LocalDate> mSuspensao = suspensoes.stream().map(suspensao -> suspensao.getPeriodoSuspensao())
                .flatMap(List::stream).collect(Collectors.toList());
        final Set<LocalDate> merged = new HashSet<>();
        merged.addAll(mFeriados);
        merged.addAll(mSuspensao);
        return merged;
	}
	
				
}
