package com.sovon9.RRMS_Portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sovon9.RRMS_Portal.dto.RegisterUserRequest;

@Service
public class RegisterUserService
{
	@Autowired
	private RestTemplate restTemplate;
	@Value("${AUTH_SERVICE_URL}")
	private String AUTH_SERVICE_URL;
	
	public ResponseEntity<RegisterUserRequest> registerNewUser(RegisterUserRequest userRequest, HttpHeaders header)
	{
		HttpEntity<RegisterUserRequest> httpEntity = new HttpEntity<>(userRequest, header);
		return restTemplate.exchange(AUTH_SERVICE_URL+"registerNewUser", HttpMethod.POST,httpEntity, RegisterUserRequest.class);
	}
}
