package com.sovon9.RRMS_Portal.service;

import java.time.LocalDate;

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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.sovon9.RRMS_Portal.dto.GuestDto;
import com.sovon9.RRMS_Portal.dto.Reservation;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class ReservationService
{
	Logger LOGGER = LoggerFactory.getLogger(ReservationService.class);
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
		guestDto.setCreateDate(LocalDate.now());
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
	
	public ResponseEntity<Reservation> modifyReservation(Reservation res, String jwtToken)
	{
    	// setting the header values
		HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);

		HttpEntity<Reservation> requestEntity = new HttpEntity<Reservation>(res, headers);

		 String url = UriComponentsBuilder.fromHttpUrl(RES_SERVICE_URL+"reservaion")
				 .toUriString();
		 ResponseEntity<Reservation> exchange = null;
		 try
		 {
			 exchange = restTemplate.exchange(
					url, HttpMethod.POST, requestEntity, Reservation.class);
		 }
		 catch (Exception e) {
			 LOGGER.error("Exception occured during modifyReservation: {}",e.getMessage());
			 exchange = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		 return exchange;
	}
	
	public ResponseEntity<Reservation> checkInReservation(Reservation res, HttpHeaders headers)
	{
		HttpEntity<Reservation> requestEntity = new HttpEntity<Reservation>(res, headers);

		 String url = UriComponentsBuilder.fromHttpUrl(RES_SERVICE_URL+"reservaion/checkin/resID/").path("{resID}")
				 .buildAndExpand(res.getResID())
				 .toUriString();
		 ResponseEntity<Reservation> exchange = null;
		 try
		 {
			 exchange = restTemplate.exchange(
					url, HttpMethod.PUT, requestEntity, Reservation.class);
		 }
		 catch (Exception e) {
			 LOGGER.error("Exception occured during checkInReservation: {}",e.getMessage());
			 exchange = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		 return exchange;
	}
	
	public ResponseEntity<Reservation> checkOutReservation(Reservation res, HttpHeaders headers)
	{
		HttpEntity<Reservation> requestEntity = new HttpEntity<Reservation>(res, headers);

		 String url = UriComponentsBuilder.fromHttpUrl(RES_SERVICE_URL+"reservaion/checkout/resID/").path("{resID}")
				 .buildAndExpand(res.getResID())
				 .toUriString();
		 ResponseEntity<Reservation> exchange = null;
		 try
		 {
			 exchange = restTemplate.exchange(
					url, HttpMethod.PUT, requestEntity, Reservation.class);
		 }
		 catch (Exception e) {
			 LOGGER.error("Exception occured during checkOutReservation: {}",e.getMessage());
			 exchange = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		 return exchange;
	}
}
