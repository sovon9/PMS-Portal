package com.sovon9.RRMS_Portal.dto;

import java.util.List;

public class LoginResponse
{
    private String username;
    private List<String> roles;
	private String jwtToken;
	public LoginResponse()
	{
		super();
	}
	public LoginResponse(String username, List<String> roles, String jwtToken)
	{
		super();
		this.username = username;
		this.roles = roles;
		this.jwtToken = jwtToken;
	}
	public String getJwtToken()
	{
		return jwtToken;
	}
	public void setJwtToken(String jwtToken)
	{
		this.jwtToken = jwtToken;
	}
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public List<String> getRoles()
	{
		return roles;
	}
	public void setRoles(List<String> roles)
	{
		this.roles = roles;
	}
    
}
