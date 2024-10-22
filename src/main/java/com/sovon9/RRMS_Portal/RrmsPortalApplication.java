package com.sovon9.RRMS_Portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableCaching
@SpringBootApplication
public class RrmsPortalApplication {

	public static void main(String[] args) {
		try
		{
		SpringApplication.run(RrmsPortalApplication.class, args);
		}
		catch(Exception e)
		{
			if(e.getClass().getName().equals("SilentExitException"))
			{
				// don't do anything
			}
		}
	}
	@Bean
	public RestTemplate getRestTemplate()
	{
		return new RestTemplate();
	}

}
