package com.techelevator.tenmo.services;

import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Accounts;
import com.techelevator.tenmo.models.User;

public class UserService {

	private static String AUTH_TOKEN = "";
	private String BASE_URL;
	private RestTemplate restTemplate;
	
	public UserService (String url) {
		BASE_URL = url;
		restTemplate = new RestTemplate();
	}
	
	public User[] getUsers(long id) {
		User[] users = null;
		try {
			return restTemplate.getForObject(BASE_URL + "users", User[].class);
		} catch (RestClientResponseException ex) {
			System.out.println(ex.getRawStatusCode() + " : " + ex.getStatusText());
		} catch (ResourceAccessException ex) {
			System.out.println(ex.getMessage());
		}
		return users;
	}
}
