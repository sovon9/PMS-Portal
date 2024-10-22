package com.sovon9.RRMS_Portal.jwt;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtils
{
//	Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);
//	
//	@Value("${jwt.jwtSecret}")
//	private String jwtSecret;
//	
//	@Value("${jwt.jwtExpiration}")
//	private Long jwtExpiration;
//	
//	public String getJwtTokenFromHeader(HttpServletRequest httpServletRequest)
//	{
//		String header = httpServletRequest.getHeader("Authentication");
//		String jwtToken = null;
//		// JWT Token is in the form "Bearer token". Remove Bearer word and get only the
//		// Token
//		if (header != null && header.startsWith("Bearer "))
//		{
//			jwtToken  = header.substring(7); // Remove Bearer prefix
//		}
//		return jwtToken;
//	}
//	
//	public String generateToken(String username, Collection<? extends GrantedAuthority> authorities)
//	{
//		return Jwts.builder()
//				.subject(username)
//				.claim("roles", authorities)
//				.issuedAt(new Date(System.currentTimeMillis()))
//                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
//                .signWith(key())
//                .compact();
//	}
//
//	private Key key()
//	{
//		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
//	}
//	
//	public boolean validateToken(String jwtToken)
//	{
//		try {
//            System.out.println("Validate");
//            //verifying with secrt key and parse it
//            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(jwtToken);
//            return true;
//        } catch (MalformedJwtException e) {
//            LOGGER.error("Invalid JWT token: {}", e.getMessage());
//        } catch (ExpiredJwtException e) {
//            LOGGER.error("JWT token is expired: {}", e.getMessage());
//        } catch (UnsupportedJwtException e) {
//            LOGGER.error("JWT token is unsupported: {}", e.getMessage());
//        } catch (IllegalArgumentException e) {
//            LOGGER.error("JWT claims string is empty: {}", e.getMessage());
//        }
//        return false;
//	}
//	
//	public String getUserNameFromJwtToken(String token) {
//        return Jwts.parser()
//                        .verifyWith((SecretKey) key())
//                .build().parseSignedClaims(token)
//                .getPayload().getSubject(); /// The 'sub' claim is typically where the username is stored
//    }
//	
}
