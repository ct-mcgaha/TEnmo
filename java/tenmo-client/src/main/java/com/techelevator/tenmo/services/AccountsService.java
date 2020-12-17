package com.techelevator.tenmo.services;

import java.math.BigDecimal;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class AccountsService {
	
	private static String AUTH_TOKEN = "";
	private String BASE_URL;
	private RestTemplate restTemplate;
	
	public AccountsService (String url) {
		BASE_URL = url;
		restTemplate = new RestTemplate();
	}
	
	public BigDecimal getAccountBalance(long id) {
		BigDecimal theAccount = null;
		try {
		 return restTemplate.getForObject(BASE_URL + "accounts/" + id + "/balance", BigDecimal.class);
		} catch (RestClientResponseException ex) {
		      System.out.println(ex.getRawStatusCode() + " : " + ex.getStatusText());
	    } catch (ResourceAccessException ex) {
	    	System.out.println(ex.getMessage());
	    }
		return theAccount;
	}
	
	private HttpEntity makeAuthEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(AUTH_TOKEN);
		HttpEntity entity = new HttpEntity(headers);
		return entity;
	}

}
