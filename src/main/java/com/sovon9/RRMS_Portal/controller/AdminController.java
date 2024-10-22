package com.sovon9.RRMS_Portal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sovon9.RRMS_Portal.dto.RegisterUserRequest;
import com.sovon9.RRMS_Portal.service.ExtractJwtTokenFromCookie;
import com.sovon9.RRMS_Portal.service.RegisterUserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/portal")
public class AdminController extends BaseController
{
	Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	private ExtractJwtTokenFromCookie jwtTokenFromCookie;
	@Autowired
	private RegisterUserService userService;
	
	@GetMapping("/admin")
	public String getAdminPage(Model model)
	{
		model.addAttribute("register", new RegisterUserRequest());
		return "administration";
	}
	
	@PostMapping("/register")
	public String registerNewUser(@ModelAttribute("register") RegisterUserRequest userRequest, Model model, HttpServletRequest request)
	{
		String jwtToken = jwtTokenFromCookie.extractJwtFromCookie(request);
		
		HttpHeaders header = new HttpHeaders();
		header.set("Content-Type", "application/json");
		header.set(HttpHeaders.AUTHORIZATION, "Bearer "+jwtToken);
		
		ResponseEntity<RegisterUserRequest> registerNewUser = userService.registerNewUser(userRequest, header);
		if(registerNewUser.getStatusCode() == HttpStatus.CREATED)
		{
			model.addAttribute("success", "User successfully Registered!");
		}
		else
		{
			model.addAttribute("error", "User Registration Failed with status: "+registerNewUser.getStatusCode());
		}
		return "administration";
	}
}
