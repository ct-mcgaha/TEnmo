package com.techelevator.tenmo.services;

import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Transfers;

public class TransfersService {
	
	private static String AUTH_TOKEN = "";
	private String BASE_URL;
	private RestTemplate restTemplate;
	
	public TransfersService (String url) {
		BASE_URL = url;
		restTemplate = new RestTemplate();
	}
	
	
	public Transfers[] viewTransferHistory(long id) {
		Transfers[] transfer = null;
		try {
		return restTemplate.getForObject(BASE_URL + "accounts" + id + "/transfers", Transfers[].class);
		} catch (RestClientResponseException ex) {
		      System.out.println(ex.getRawStatusCode() + " : " + ex.getStatusText());
	    } catch (ResourceAccessException ex) {
	    	System.out.println(ex.getMessage());
	    }
		return transfer;
	}
}
