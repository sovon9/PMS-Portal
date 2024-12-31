package com.sovon9.RRMS_Portal;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.sovon9.RRMS_Portal.dto.GuestDto;

@SpringBootTest
class RrmsPortalApplicationTests {

	@Mock
	public RestTemplate restTemplate;
	
	@BeforeAll
	public static void beforeClass()
	{
		System.out.println("before");
	}
	
	@Test
	void contextLoads() {
		
		//when(restTemplate.exchange("", HttpMethod.POST,null, GuestDto.class)).thenReturn(null);
	}

}
