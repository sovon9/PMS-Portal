package com.sovon9.RRMS_Portal.dto;

import java.util.Set;

public class RegisterUserRequest
{
	private String username;
	private String password;
	private String email;
	private Set<String> role;
	
	public RegisterUserRequest()
	{
		super();
	}
	public RegisterUserRequest(String username, String password)
	{
		super();
		this.username = username;
		this.password = password;
	}
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public Set<String> getRole()
	{
		return role;
	}
	public void setRole(Set<String> role)
	{
		this.role = role;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	
}
