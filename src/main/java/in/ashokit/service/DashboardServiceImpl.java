package in.ashokit.service;

import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.ashokit.binding.Quotes;

@Service
public class DashboardServiceImpl implements DashboardService{
	
	private String quoteUrl="https://type.fit/api/quotes";
	private Quotes[] quote=null;
	
	@Override
	public String getQuotes() {
		if(quote==null) {
			RestTemplate rt=new RestTemplate();
			ResponseEntity<String> forEntity = rt.getForEntity(quoteUrl, String.class);
			String body = forEntity.getBody();
			
			ObjectMapper mapper=new ObjectMapper();
			try {
				quote = mapper.readValue(body,Quotes[].class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
		
		Random r=new Random();
		int nextInt = r.nextInt(quote.length-1);
		
		return quote[nextInt].getText();
	}

}
