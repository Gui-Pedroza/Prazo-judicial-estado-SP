package com.pedroza.calculaprazoestado.common;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.pedroza.calculaprazoestado.model.Feriado;
import com.pedroza.calculaprazoestado.model.Suspensao;

public class FeriadoESuspensaoMerger {

    public static ArrayList<LocalDate> merge(ArrayList<Feriado> feriados, ArrayList<Suspensao> suspensao) {
        final List<LocalDate> mFeriados = feriados.stream().map(feriado -> feriado.getDate()).collect(Collectors.toList());
        final List<LocalDate> mSuspensao = suspensao.stream().map(suspensaoResponse -> suspensaoResponse.getPeriodoSuspensao())
                .flatMap(List::stream).collect(Collectors.toList());
        final ArrayList<LocalDate> merged = new ArrayList<>();
        merged.addAll(mFeriados);
        merged.addAll(mSuspensao);
        return merged;
    }

	public static List<LocalDate> merge(List<Feriado> feriados, List<Suspensao> suspensoes) {
		// TODO Auto-generated method stub
		return null;
	}
}
