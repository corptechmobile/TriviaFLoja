package br.com.webapp.web.util;




import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import br.com.webapp.model.web.receita.ReceitaWsResultDTO;


public class ReceitaWsUtils {
	
	public static final ReceitaWsResultDTO buscarEmpresa(String cnpj) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			Gson gson = new Gson();
			
			HttpGet request = new HttpGet("https://www.receitaws.com.br/v1/cnpj/"+cnpj+"");
			request.addHeader("content-type", "application/json; charset=utf-8");
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();

			InputStream source = entity.getContent();
			Reader reader = new InputStreamReader(source, "UTF-8");

			ReceitaWsResultDTO receitaWsResultDTO = gson.fromJson(reader, ReceitaWsResultDTO.class);
			if(receitaWsResultDTO!=null){
				if(receitaWsResultDTO.getStatus().equals("OK")) {
					return receitaWsResultDTO;
				}
			}
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		// cnpj exemplos
		// 12926992000161
		// 27865757002300
		// 60509239000709 
		// 60701521002141
		String cnpj = "12827199000105";
		ReceitaWsResultDTO dto = ReceitaWsUtils.buscarEmpresa(cnpj);
		
		if(dto!=null) {
			System.out.println(""+dto.getNome());
			System.out.println(""+dto.getTelefone());
		}
		
//		URL url;
//		try {
//			url = new URL("https://www.receitaws.com.br/v1/cnpj/"+cnpj);
//		
//			try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
//				 for (String line; (line = reader.readLine()) != null;) {
//				 System.out.println(line);
//				 }
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}

}
