package com.sovon9.RRMS_Portal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.sovon9.RRMS_Portal.dto.LoginRequest;
import com.sovon9.RRMS_Portal.dto.LoginResponse;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/portal")
public class LoginLogoutController
{
	Logger LOGGER = LoggerFactory.getLogger(GuestProfileController.class);
	
	@Value("${AUTH_SERVICE_URL}")
	private String AUTH_SERVICE_URL;
	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/login")
	public String login(Model model)
	{
		model.addAttribute("login", new LoginRequest());
		return "login2";
	}
	
	@ResponseBody
	@PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
//        Authentication authentication;
//        try {
//            authentication = authenticationManager
//                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//        } catch (AuthenticationException exception) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("message", "Bad credentials");
//            map.put("status", false);
//            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
//        }
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        // we can directly pass username as we only need username for JWT token creation
//        String jwtToken = jwtUtils.generateToken(userDetails.getUsername(), userDetails.getAuthorities());
        
        ResponseEntity<LoginResponse> responseEntity = restTemplate.postForEntity(AUTH_SERVICE_URL+"token", loginRequest, LoginResponse.class);
        if (responseEntity.getStatusCode() != HttpStatus.OK)
		{
			LOGGER.error("Error in fetching guest data "+responseEntity.getStatusCode().value());
		}
        // If password validation fails
        else if(responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST)
        {
        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // Set the JWT in an HttpOnly cookie
        Cookie jwtCookie = new Cookie("jwtToken", responseEntity.getBody().getJwtToken());
        jwtCookie.setHttpOnly(true); // Prevent access via JavaScript
        jwtCookie.setSecure(true);   // Ensure cookie is only sent over HTTPS
        jwtCookie.setPath("/");      // Set the path for the cookie
        jwtCookie.setMaxAge(60 * 60); // Token valid for 1 day

        response.addCookie(jwtCookie);

        return ResponseEntity.ok("Login successful");
    }
	
	@GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        // Clear the JWT token by setting an expired cookie
        Cookie jwtCookie = new Cookie("jwtToken", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);  // Use true if using HTTPS
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0); // Set expiry to zero to delete the cookie
        
        // Add the cookie to the response
        response.addCookie(jwtCookie);
        // Redirect to the login page
        return "forward:/portal/login";
    }
}
