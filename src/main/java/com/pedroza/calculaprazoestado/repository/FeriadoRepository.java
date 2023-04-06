package com.pedroza.calculaprazoestado.repository;

import com.google.gson.Gson;
import com.pedroza.calculaprazoestado.common.HttpClient;

public class FeriadoRepository {
	private HttpClient mHttpClient;
    private static final String BASE_URL = "https://www.tjsp.jus.br/CanaisComunicacao/Feriados/";
    private Gson gson = new Gson();
    
    public FeriadoRepository(HttpClient httpClient) {
        mHttpClient = httpClient;
    }
    
    	
}
