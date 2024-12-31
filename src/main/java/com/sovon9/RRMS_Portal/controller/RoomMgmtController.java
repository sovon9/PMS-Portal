package com.sovon9.RRMS_Portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sovon9.RRMS_Portal.service.ExtractJwtTokenFromCookie;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/portal")
@Controller
public class RoomMgmtController extends BaseController
{
	@Autowired
	private ExtractJwtTokenFromCookie jwtTokenFromCookie;
	
	@GetMapping("/room-management")
	public String getRoomMgmtPage(Model model, HttpServletRequest request)
	{
		//String jwtToken = jwtTokenFromCookie.extractJwtFromCookie(request);
		return "room-management";
	}
}
