package com.pedroza.calculaprazoestado.repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.pedroza.calculaprazoestado.common.HttpClient;
import com.pedroza.calculaprazoestado.model.Suspensao;

public class SuspensaoRepository {
	
	private static final String BASE_URL = "https://www.tjsp.jus.br/CanaisComunicacao/Feriados/";
    private Gson gson = new Gson();
    
    @Autowired
    HttpClient mHttpClient;
    
    public SuspensaoRepository(HttpClient httpClient) {
        mHttpClient = httpClient;
    }
	
	 public ArrayList<Suspensao> getSuspensoes(String ano, String municipio) throws Exception {
	        final String url = BASE_URL + "PesquisarSuspensoes?nomeMunicipio=" + municipio + "&ano=" + ano;
	        final String response = mHttpClient.get(url);
	        return createSuspensao(response);
	    }
	 
	 private ArrayList<Suspensao> createSuspensao(String json) {
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
	    	DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    	return LocalDate.parse(date, inputFormatter);
	    }

}
