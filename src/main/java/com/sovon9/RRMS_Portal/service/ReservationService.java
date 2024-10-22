package com.sovon9.RRMS_Portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.sovon9.RRMS_Portal.dto.GuestDto;
import com.sovon9.RRMS_Portal.dto.Reservation;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class ReservationService
{
	@Value("${RES_SERVICE_URL}")
	private String RES_SERVICE_URL;
	@Value("${GUESTINFO_SERVICE_URL}")
	private String GUESTINFO_SERVICE_URL;
	
	@Autowired
	RestTemplate restTemplate;
	
	@CircuitBreaker(name="guestInfo", fallbackMethod = "fallbackGuestInfo")
	public ResponseEntity<GuestDto> createGuestInfo(Reservation res, HttpHeaders headers)
	{
		GuestDto guestDto = new GuestDto();
		guestDto.setFirstName(res.getFirstName());
		guestDto.setLastName(res.getLastName());
		guestDto.setGuestID(res.getGuestID());
		guestDto.setEmail(res.getEmail());
		// create Guest entity
		HttpEntity<GuestDto> guestEntity = new HttpEntity<>(guestDto, headers);

		ResponseEntity<GuestDto> exchange = restTemplate.exchange(GUESTINFO_SERVICE_URL + "guestinfo", HttpMethod.POST,
				guestEntity, GuestDto.class);
		return exchange;
	}
	
	public ResponseEntity<Reservation> createReservation(Reservation res, HttpHeaders headers)
	{
		HttpEntity<Reservation> requestEntity = new HttpEntity<Reservation>(res, headers);

		 String url = UriComponentsBuilder.fromHttpUrl(RES_SERVICE_URL+"reservaion")
				 .queryParam("email", res.getEmail().trim())
				 .toUriString();
		 ResponseEntity<Reservation> exchange = restTemplate.exchange(
					url, HttpMethod.POST, requestEntity, Reservation.class);
		 return exchange;
	}
	
	public ResponseEntity<Reservation[]> searchReservation(String url, HttpHeaders headers)
	{
		HttpEntity<?> httpEntity = new HttpEntity<>(headers);
		return restTemplate.exchange(url, HttpMethod.GET, httpEntity, Reservation[].class);
	}
	
	public ResponseEntity<Reservation> fetchReservationData(Long resID, HttpHeaders headers)
	{
		HttpEntity<?> httpEntity = new HttpEntity<>(headers);
		return restTemplate.exchange(RES_SERVICE_URL+"reservaion/resID/"+resID, HttpMethod.GET, httpEntity, Reservation.class);
	}
	
	public ResponseEntity<GuestDto> fallbackGuestInfo(Reservation res, HttpHeaders headers, Throwable throwable)
	{
		return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
	}
}
