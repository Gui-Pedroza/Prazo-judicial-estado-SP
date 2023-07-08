package com.pedroza.calculaprazoestado.common;

import java.io.IOException;

import org.springframework.stereotype.Component;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class HttpClient {

	private static final OkHttpClient client = new OkHttpClient();   
    
	public String get(String url) throws IOException {
        // Usage doc: https://square.github.io/okhttp/#requirements
        Request request = new Request.Builder()
            .url(url)
            .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
	
}
