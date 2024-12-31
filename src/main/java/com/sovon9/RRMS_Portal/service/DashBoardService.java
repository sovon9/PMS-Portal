package com.sovon9.RRMS_Portal.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.sovon9.RRMS_Portal.constants.ResConstants;
import com.sovon9.RRMS_Portal.dto.Reservation;

import io.github.resilience4j.retry.annotation.Retry;

@Service
public class DashBoardService
{
	Logger LOGGER = LoggerFactory.getLogger(DashBoardService.class);
	
	@Autowired
	RestTemplate restTemplate;
	@Value("${RES_SERVICE_URL}")
	private String RES_SERVICE_URL;
	
	@Retry(name="dashboardService", fallbackMethod = "fallBackDashBoardData")
	//@Cacheable(value = "reservation", key = "#status")
	public Reservation[] fetchDashBoardDataForRes(String status, String jwtToken)
	{
		ResponseEntity<Reservation[]> responseEntity = null;
		Reservation[] reservations = {};
		try
		{
			 HttpHeaders headers = new HttpHeaders();
		        headers.set("Authorization", "Bearer " + jwtToken);
	        if(status.equals(ResConstants.DEP_STATUS_CODE))
	        {
	        	responseEntity= fetchReservationForDashFilter("REG", headers);
	        }
			else
			{
				responseEntity = fetchReservationForDashFilter(status, headers);
			}
	        
			if (responseEntity.getStatusCode() == HttpStatus.OK)
			{
				reservations = responseEntity.getBody();
				if (status.equals(ResConstants.RES_STATUS))
				{
					responseEntity = fetchReservationForDashFilter(ResConstants.MOD_STATUS, headers);
					if (responseEntity.getStatusCode() == HttpStatus.OK)
					{
						Reservation[] reservations2 = responseEntity.getBody();
						Reservation[] arrReservations = new Reservation[reservations.length+reservations2.length];
						System.arraycopy(reservations, 0, arrReservations, 0, reservations.length);
						System.arraycopy(reservations2, 0, arrReservations, reservations.length, reservations2.length);
						reservations = arrReservations;
					}
				}
				if(status.equals(ResConstants.DEP_STATUS_CODE))
				{
					reservations = Arrays.stream(reservations).filter(res->res.getDeptDate()
							.equals(LocalDate.now())).toArray(Reservation[]::new);
				}
			}
			else
			{
				LOGGER.error("Error while fetching dashboard data with Status : "+responseEntity.getStatusCode());
			}
		}
		catch (HttpClientErrorException.Unauthorized e)
		{
			LOGGER.error("Unauthorized error while fetching dashboard data: " + e.getMessage());
			// Handle 401 Unauthorized error specifically
		}

		return reservations;
	}
	
	public ResponseEntity<Reservation[]> fetchReservationForDashFilter(String status, HttpHeaders headers)
	{
		LOGGER.debug("fetching dashboard data for status: {}",status);
	    HttpEntity<?> httpEntity = new HttpEntity<>(headers);
		return restTemplate.exchange(
				RES_SERVICE_URL+"reservaion/status/"+status, HttpMethod.GET, httpEntity, Reservation[].class);
	}
	
	public Reservation[] fallBackDashBoardData(String status, String jwtToken, Throwable throwable) {
	    LOGGER.error("Fallback method called due to: {}", throwable.getMessage());
	    return new Reservation[0];
	}

	@Retry(name="guestCount", fallbackMethod = "fallbackCountGuestByStatus")
	public Map<String, Long> fetchCountGuestByStatus( String jwtToken)
	{
		Reservation[] fetchDashData = fetchDashBoardDataForRes("ALL", jwtToken);
		return Arrays.stream(fetchDashData).collect(Collectors.groupingBy(res->chartStatus(res),Collectors.counting()));
	}
	
	public Map<String, Long> fallbackCountGuestByStatus( String jwtToken, Exception e)
	{
		LOGGER.error("fallback method called for fetchCountGuestByStatus: {}"+e.getMessage());
		return Map.of();
	}
	
	public String chartStatus(Reservation res)
	{
		if(res.getStatus().equals(ResConstants.RES_STATUS) || res.getStatus().equals(ResConstants.MOD_STATUS))
		{
			return "ARRIVING";
		}
		else if(res.getStatus().equals(ResConstants.REG_STATUS))
		{
			if(res.getDeptDate().equals(LocalDate.now()))
			{
				return "DEPARTING";
			}
			else
			{
				return "INHOUSE";
			}
		}
		return null;
	}
	
}
