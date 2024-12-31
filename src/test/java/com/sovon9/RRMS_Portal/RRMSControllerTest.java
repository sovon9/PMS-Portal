package com.sovon9.RRMS_Portal;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import com.sovon9.RRMS_Portal.config.FooterProperties;
import com.sovon9.RRMS_Portal.constants.ResConstants;
import com.sovon9.RRMS_Portal.controller.BaseController;
import com.sovon9.RRMS_Portal.controller.RRMSPortalController;
import com.sovon9.RRMS_Portal.dto.Reservation;
import com.sovon9.RRMS_Portal.service.DashBoardService;
import com.sovon9.RRMS_Portal.service.ExtractJwtTokenFromCookie;

import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(RRMSPortalController.class)
public class RRMSControllerTest
{
	@Mock
	private ExtractJwtTokenFromCookie jwtTokenFromCookie;
	
	@Mock
	private HttpServletRequest httpServletRequest;
	@Mock
	private FooterProperties footerProperties;
	@Mock
	private DashBoardService dashBoardService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@InjectMocks
	private RRMSPortalController controller;
	
//	@Mock
//	private ResConstants constants;
	
	@BeforeEach
	void setup()
	{
		
	}
	
	@Disabled
	public void homeTest() throws Exception
	{
		when(footerProperties.getEmail()).thenReturn("");
		when(footerProperties.getPhone()).thenReturn("");
		when(footerProperties.getAddress()).thenReturn("");
		
		when(jwtTokenFromCookie.extractJwtFromCookie(httpServletRequest)).thenReturn("abc");
		Reservation[] reservatiion = new Reservation[0];
		when(dashBoardService.fetchDashBoardDataForRes("RES", "abc")).thenReturn(reservatiion);
		MockedStatic<ResConstants> mockStaticVal = mockStatic(ResConstants.class);
		//mockStaticVal.when(ResConstants.ARRIVAL_STATUS).thenReturn();
		mockMvc.perform(get("/home")).andExpect(status().isOk());
	}
}
