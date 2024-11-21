package com.sovon9.RRMS_Portal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sovon9.RRMS_Portal.dto.RegisterUserRequest;

import io.github.resilience4j.retry.annotation.Retry;

@Service
public class RegisterUserService
{
	Logger LOGGER = LoggerFactory.getLogger(RegisterUserService.class);
	@Autowired
	private RestTemplate restTemplate;
	@Value("${AUTH_SERVICE_URL}")
	private String AUTH_SERVICE_URL;
	
	@Retry(name="retry-registerUser", fallbackMethod = "retryRegisterUser")
	public ResponseEntity<RegisterUserRequest> registerNewUser(RegisterUserRequest userRequest, HttpHeaders header)
	{
		HttpEntity<RegisterUserRequest> httpEntity = new HttpEntity<>(userRequest, header);
		return restTemplate.exchange(AUTH_SERVICE_URL+"registerNewUser", HttpMethod.POST,httpEntity, RegisterUserRequest.class);
	}
	
	public ResponseEntity<RegisterUserRequest> retryRegisterUser(RegisterUserRequest userRequest, HttpHeaders header, Throwable throwable)
	{
		LOGGER.error("Registration failed for user {}: {}", userRequest.getUsername(), throwable.getMessage());
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
	}
}
