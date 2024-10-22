package com.sovon9.RRMS_Portal.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.sovon9.RRMS_Portal.service.FooterProperties;

import jakarta.servlet.http.HttpServletRequest;

public abstract class BaseController
{
	@Autowired
	private FooterProperties footerProperties;
	@ModelAttribute
	public void addCommonAttributes(HttpServletRequest request, Model model)
	{
		// Get current date and time
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedDate = currentDate.format(formatter);
		String agentName=null;

		// Get logged-in agent name
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if (authentication != null && authentication.isAuthenticated()) {
			// Extract username from the custom header
			agentName = request.getHeader("X-Username");
//		}
		String rolesFromHeader = request.getHeader("X-Roles");
		List<String> roles = rolesFromHeader!=null?Arrays.asList(rolesFromHeader.split(",")):List.of();	
		
		//String agentName = "1200";
		// Add to model
		model.addAttribute("currentDate", formattedDate);
		model.addAttribute("agentName", agentName);
		model.addAttribute("agentRole", roles);
		model.addAttribute("footer",footerProperties);
	}
}
