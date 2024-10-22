package com.sovon9.RRMS_Portal.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler
{
	@ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ModelAndView handleUnauthorized(HttpServletRequest request, Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", "You are not authorized to view this page.");
        modelAndView.setViewName("unauthorized");  // unauthorized.html
        return modelAndView;
    }
}
