package com.pedroza.calculaprazoestado.common.util;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollectionsHandler {
	
	public static Set<LocalDate> removeFullMonth(Set<LocalDate> dateSet, Month month) {
		Set<LocalDate> result = new HashSet<>();
		for (LocalDate dates : dateSet) {
			if (!dates.getMonth().equals(month)) {
				result.add(dates);
			}
		}
		return result;
	}
	
	public static void addDate(Set<LocalDate> dateSet, LocalDate date) {
		dateSet.add(date);
	}
	
	public static void removeDate(Set<LocalDate> dateSet, LocalDate date) {
		dateSet.remove(date);
	}
	
	public static void removeElementFromList(List<String> list, String startsWithString) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).startsWith(startsWithString)) {
				list.remove(list.get(i));				
			}
		}
	}
		
}
