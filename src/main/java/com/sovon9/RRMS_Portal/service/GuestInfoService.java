package com.sovon9.RRMS_Portal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.sovon9.RRMS_Portal.dto.GuestDto;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class GuestInfoService
{
	Logger LOGGER = LoggerFactory.getLogger(GuestInfoService.class);
	@Autowired
	RestTemplate restTemplate;
	
	@CircuitBreaker(name = "guestinfo", fallbackMethod = "fallbackGuestData")
	public ResponseEntity<GuestDto[]> searchGuestData(String url, HttpHeaders headers)
	{
		HttpEntity<?> httpEntity = new HttpEntity<>(headers);
		LOGGER.error("service call from GuestInfoService() to URL:{}");
		return restTemplate.exchange(url, HttpMethod.GET, httpEntity, GuestDto[].class);
	}
	
	@CircuitBreaker(name = "guestinfo", fallbackMethod = "fallbackSaveGuestData")
	public ResponseEntity<GuestDto> saveGuestData(String url,GuestDto guest, HttpHeaders headers)
	{
		// create Guest entity
		HttpEntity<GuestDto> guestEntity = new HttpEntity<>(guest, headers);
		LOGGER.error("service call from GuestInfoService() to URL:{}",url);
		return restTemplate.exchange(url, HttpMethod.POST, guestEntity, GuestDto.class);
	}
	
	public ResponseEntity<GuestDto[]> fallbackGuestData(Exception e)
	{
		return new ResponseEntity<GuestDto[]>(new GuestDto[] {}, HttpStatus.OK);
	}
	
	public ResponseEntity<GuestDto> fallbackSaveGuestData(ResourceAccessException ra)
	{
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
}
