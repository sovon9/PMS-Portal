package com.sovon9.RRMS_Portal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.Model;
import org.springframework.web.util.UriComponentsBuilder;

import com.sovon9.RRMS_Portal.constants.ResConstants;
import com.sovon9.RRMS_Portal.controller.RRMSPortalController;
import com.sovon9.RRMS_Portal.dto.Reservation;
import com.sovon9.RRMS_Portal.dto.RoomDto;
import com.sovon9.RRMS_Portal.service.ExtractJwtTokenFromCookie;
import com.sovon9.RRMS_Portal.service.ReservationService;
import com.sovon9.RRMS_Portal.service.RoomsService;

import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
public class ReservationControllerTest
{
	 @InjectMocks
	    private RRMSPortalController reservationController;

	    @Mock
	    private ReservationService reservationService;
	    
	    @Mock
	    private RoomsService roomsService;

	    @Mock
	    private ExtractJwtTokenFromCookie jwtTokenFromCookie;

	    @Mock
	    private Model model;

	    @Mock
	    private HttpServletRequest request;

	    private static final Long RES_ID = 1L;
	    private static final String JWT_TOKEN = "abcd";
	    @BeforeEach
	    void setUp() 
	    {
	    	ReflectionTestUtils.setField(reservationController, "RES_SERVICE_URL", "http://mockurl.com/");
	    }
	    
	    @Test
	    void testModifyResPageForResIDSuccessCase() {
	        Reservation mockReservation = new Reservation();
	        mockReservation.setResID(RES_ID);
	        ResponseEntity<Reservation> mockResponse = new ResponseEntity<>(mockReservation, HttpStatus.OK);

	        when(jwtTokenFromCookie.extractJwtFromCookie(request)).thenReturn(JWT_TOKEN);
	        when(reservationService.fetchReservationData(eq(RES_ID), any(HttpHeaders.class))).thenReturn(mockResponse);
	        //
	        List<RoomDto> roomDto=new ArrayList<>();
	        roomDto.add(new RoomDto(100, "12REGA"));
	        when(roomsService.getAllAvlRateplanRoomData(JWT_TOKEN)).thenReturn(roomDto);
	        // Act
	        String viewName = reservationController.modifyResPageForResID(model, RES_ID, request);

	        // Assert
	        assertEquals("modifyReservation", viewName);
	        verify(model, times(1)).addAttribute(eq("res"), eq(mockReservation));
	    }
	    
	    @Test
	    void testFetcResData_Sucess()
	    {
	    	 Reservation[] res=new Reservation[1];
	    	 Reservation reservation = new Reservation();
	    	 reservation.setFirstName("sovon");
	    	 reservation.setLastName("singha");
			 res[0]=reservation;
	    	 
	    	 ResponseEntity<Reservation[]> mockResponse = new ResponseEntity<>(res, HttpStatus.OK);
	    	 when(jwtTokenFromCookie.extractJwtFromCookie(request)).thenReturn(JWT_TOKEN);
	    	 when(reservationService.searchReservation(any(String.class), any(HttpHeaders.class))).thenReturn(mockResponse);
	    	 
	    	 List<Reservation> fetchResData = reservationController.fetchResData(RES_ID, request);
	    	 assertEquals("sovon", fetchResData.get(0).getFirstName());
	    	 verifyNoMoreInteractions(reservationService);
	    }
	    
	    @Test
	    void testFetchResData_NotFound() {
	        // Arrange
	        Reservation mockReservation = new Reservation();
	        mockReservation.setResID(RES_ID);
	        Reservation[] mockResponseData = {mockReservation};
	        ResponseEntity<Reservation[]> mockResponse = new ResponseEntity<>(mockResponseData, HttpStatus.NOT_FOUND);

	        when(jwtTokenFromCookie.extractJwtFromCookie(request)).thenReturn(JWT_TOKEN);	        
	        when(reservationService.searchReservation(any(String.class), any(HttpHeaders.class))).thenReturn(mockResponse);

	        // Act
	        List<Reservation> result = reservationController.fetchResData(RES_ID, request);

	        // Assert
	        assertTrue(result.isEmpty());
	    }


}
