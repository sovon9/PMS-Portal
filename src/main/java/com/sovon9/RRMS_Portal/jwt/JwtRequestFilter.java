package com.sovon9.RRMS_Portal.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter //extends OncePerRequestFilter
{
//	Logger LOGGER=LoggerFactory.getLogger(JwtRequestFilter.class);
//	@Autowired
//	private JwtUtils jwtUtils;
//	@Autowired
//	private UserDetailsService userDetailsService;
//	
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException
//	{
//		try
//		{
//			String jwtToken = jwtUtils.getJwtTokenFromHeader(request);
//			LOGGER.error("Jwt Token : " + jwtToken);
//			if (null != jwtToken && jwtUtils.validateToken(jwtToken))
//			{
//				String userName = jwtUtils.getUserNameFromJwtToken(jwtToken);
//				if (null != userName && SecurityContextHolder.getContext().getAuthentication() == null)
//				{
//					UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
//					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//							userDetails, null, userDetails.getAuthorities());
//					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//				}
//			}
//		}
//		catch (Exception e)
//		{
//			LOGGER.error("Authentication Set Failed : "+e);
//		}
//		
//	}

}
