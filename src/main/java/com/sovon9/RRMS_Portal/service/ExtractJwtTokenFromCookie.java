package com.sovon9.RRMS_Portal.service;

import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class ExtractJwtTokenFromCookie
{
	/**
	 *  Helper method to extract JWT token from the cookie
	 * @param request
	 * @return
	 */
	public String extractJwtFromCookie(HttpServletRequest request) {
		// webutil utility method to extract cookie from HttpServletRequest
	    Cookie jwtCookie = WebUtils.getCookie(request, "jwtToken");
	    return (jwtCookie != null) ? jwtCookie.getValue() : null;
	}
}
