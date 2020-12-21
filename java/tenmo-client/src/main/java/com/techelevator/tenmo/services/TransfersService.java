package com.techelevator.tenmo.services;

import java.math.BigDecimal;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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
	
	
	public Transfers[] viewTransferHistory(long id, String authToken) {
		Transfers[] transfer = null;
		AUTH_TOKEN = authToken;
		HttpEntity<?> entity = makeAuthEntity();
		try {
		transfer = restTemplate.exchange(BASE_URL + "accounts/" + id + "/transfers", HttpMethod.GET, entity, Transfers[].class).getBody();
		} catch (RestClientResponseException ex) {
		      System.out.println(ex.getRawStatusCode() + " : " + ex.getStatusText());
	    } catch (ResourceAccessException ex) {
	    	System.out.println(ex.getMessage());
	    }
		return transfer;
	}
	
	public Transfers viewSingleTransfer(long id, String authToken) {
		Transfers transfer = null;
		AUTH_TOKEN = authToken;
		HttpEntity<?> entity = makeAuthEntity();
		try {
			transfer = restTemplate.exchange(BASE_URL + "/transfers" + id, HttpMethod.GET, entity, Transfers.class).getBody();
		} catch (RestClientResponseException ex) {
		      System.out.println(ex.getRawStatusCode() + " : " + ex.getStatusText());
	    } catch (ResourceAccessException ex) {
	    	System.out.println(ex.getMessage());
	    }
		return transfer;
	}
	
	public Transfers sendTransfer(long transferTypeId, long transferStatusId, long accountFrom, long accountTo, BigDecimal amount, String authToken) {
		AUTH_TOKEN = authToken;
		Transfers transfer = new Transfers();
		transfer.setAmount(amount);
		transfer.setTransferTypeId(transferTypeId);
		transfer.setTransferStatusId(transferStatusId);
		transfer.setAccountFrom(accountFrom);
		transfer.setAccountTo(accountTo);
		
		try {
			return restTemplate.postForObject(BASE_URL + "transfers/send", transferEntity(transfer), Transfers.class);
		} catch (RestClientResponseException ex) {
		      System.out.println(ex.getRawStatusCode() + " : " + ex.getMessage());
	    } 
		return transfer;
	}
	
	private HttpEntity<Transfers> transferEntity(Transfers sendTransfer) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(AUTH_TOKEN);
		HttpEntity<Transfers> entity = new HttpEntity<>(sendTransfer, headers);
		return entity;
	}
	
	private HttpEntity<?> makeAuthEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(AUTH_TOKEN);
		HttpEntity entity = new HttpEntity(headers);
		return entity;
	}
}
