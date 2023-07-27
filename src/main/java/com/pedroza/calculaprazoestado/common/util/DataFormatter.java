package com.pedroza.calculaprazoestado.common.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataFormatter {
	
	
	static Locale localBR = new Locale("pt", "BR");
	static final DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	static final DateTimeFormatter formatoBRextenso = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", localBR);
	
	public static String formatoBR(LocalDate date) {
		return date.format(formatoBR);
	}
	
	public static String formatoBRextenso(LocalDate date) {
		return date.format(formatoBRextenso);
	}

}
