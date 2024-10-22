package com.sovon9.RRMS_Portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController
{
	@GetMapping("/access-denied")
	public String accessDenied()
	{
		return "access-denied"; // Returns the custom 403 page
	}

	@GetMapping("/unauthorized")
	public String unauthorized()
	{
		return "unauthorized"; // Returns the custom 401 page
	}
}
