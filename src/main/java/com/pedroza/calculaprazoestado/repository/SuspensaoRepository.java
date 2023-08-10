package com.pedroza.calculaprazoestado.repository;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.pedroza.calculaprazoestado.common.HttpClient;
import com.pedroza.calculaprazoestado.model.Suspensao;

@Repository
public class SuspensaoRepository {

	private static final String BASE_URL = "https://www.tjsp.jus.br/CanaisComunicacao/Feriados/";
	private Gson gson = new Gson();

	@Autowired
	HttpClient httpClient;	
	
	public SuspensaoRepository() {
		
	}
	
	
	public SuspensaoRepository(HttpClient httpClient) {		
		this.httpClient = httpClient;
	}


	public List<Suspensao> getSuspensoes(int ano, String municipio) {
		
		final String url = BASE_URL + "PesquisarSuspensoes?nomeMunicipio=" + municipio + "&ano=" + ano;
		String response = null;
		try {
			response = httpClient.get(url);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return createSuspensao(response);
	}

	private List<Suspensao> createSuspensao(String json) {
		Map map = gson.fromJson(json, Map.class);
		ArrayList<Map> suspensoes = (ArrayList<Map>) map.get("data");
		ArrayList<Suspensao> suspensaoList = new ArrayList<Suspensao>();

		for (int i = 0; i < suspensoes.size(); i++) {
			Map feriadoObj = (Map) suspensoes.get(i);
			String descricao = feriadoObj.get("Descricao").toString();
			LocalDate dataInicial = parseDate(feriadoObj.get("DataInicial").toString());
			LocalDate dataFinal = parseDate(feriadoObj.get("DataFinal").toString());
			suspensaoList.add(new Suspensao(descricao, dataInicial, dataFinal));
		}		
		return suspensaoList;
	}

	private LocalDate parseDate(String date) {
		Long timeStamp = Long.parseLong(date.replaceAll("\\D", ""));
		return Instant.ofEpochMilli(timeStamp).atZone(ZoneId.systemDefault()).toLocalDate();		
	}

}
