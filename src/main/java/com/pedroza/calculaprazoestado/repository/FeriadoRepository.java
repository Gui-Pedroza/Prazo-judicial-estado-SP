package com.pedroza.calculaprazoestado.repository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.pedroza.calculaprazoestado.common.HttpClient;
import com.pedroza.calculaprazoestado.model.Feriado;

@Repository
public class FeriadoRepository {

	private static final String BASE_URL = "https://www.tjsp.jus.br/CanaisComunicacao/Feriados/";
	private Gson gson = new Gson();

	
	@Autowired
	HttpClient httpClient;
	

	public FeriadoRepository() {
		
	}	
		
	public FeriadoRepository(HttpClient httpClient) {		
		this.httpClient = httpClient;
	}

	public List<Feriado> getFeriados(int ano, String municipio) {
		final String url = BASE_URL + "PesquisarFeriados?nomeMunicipio=" + municipio + "&ano=" + ano;
		String response = null;
		try {
			response = httpClient.get(url);
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return createFeriadoResponse(response);
	}

	private List<Feriado> createFeriadoResponse(String json) {
		Map map = gson.fromJson(json, Map.class);
		List<Map> feriados = (List<Map>) map.get("data");
		List<Feriado> feriadosList = new ArrayList<Feriado>();

		for (int i = 0; i < feriados.size(); i++) {
			Map feriadoObj = (Map) feriados.get(i);
			String descricao = feriadoObj.get("Descricao").toString();
			LocalDate data = parseDate(feriadoObj.get("Data").toString());
			feriadosList.add(new Feriado(descricao, data));
		}
		return feriadosList;
	}

	private LocalDate parseDate(String date) {
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return LocalDate.parse(date, inputFormatter);
	}

}
