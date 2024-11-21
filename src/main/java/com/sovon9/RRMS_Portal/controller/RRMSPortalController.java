package com.sovon9.RRMS_Portal.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.sovon9.RRMS_Portal.constants.ResConstants;
import com.sovon9.RRMS_Portal.dto.GuestDto;
import com.sovon9.RRMS_Portal.dto.Reservation;
import com.sovon9.RRMS_Portal.dto.ReservationSearchDTO;
import com.sovon9.RRMS_Portal.dto.RoomDto;
import com.sovon9.RRMS_Portal.service.DashBoardService;
import com.sovon9.RRMS_Portal.service.ExtractJwtTokenFromCookie;
import com.sovon9.RRMS_Portal.service.ReservationService;
import com.sovon9.RRMS_Portal.service.RoomsService;

import io.github.resilience4j.retry.annotation.Retry;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/portal")
public class RRMSPortalController extends BaseController{

	@Value("${RES_SERVICE_URL}")
	private String RES_SERVICE_URL;
	@Value("${GUESTINFO_SERVICE_URL}")
	private String GUESTINFO_SERVICE_URL;

	Logger LOGGER = LoggerFactory.getLogger(RRMSPortalController.class);
	
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	DashBoardService dashBoardService;
	@Autowired
	RoomsService roomsService;
	@Autowired
	ReservationService reservationService;
	@Autowired
	private ExtractJwtTokenFromCookie jwtTokenFromCookie;

    @GetMapping("/home")
	public String home(Model model, HttpServletRequest request, @PathVariable(value="status", required = false) String status)
	{
    	String jwtToken = jwtTokenFromCookie.extractJwtFromCookie(request);
    	// dashboard filter options
    	Map<String, String> dashboardFilter = new TreeMap<>();
    	dashboardFilter.put("Arrival", "RES");
    	dashboardFilter.put("In-House", "REG");
    	model.addAttribute("dashFilterOptions",dashboardFilter);
    	// fetch reservation data from res service
    	//dashBoardService.getTestAPIRes("RES"); 
    	Reservation[] reservations = dashBoardService.fetchDashBoardDataForRes(status==null?ResConstants.RES_STATUS:status, jwtToken);
		model.addAttribute("reservations", List.of(reservations));
		return "home";
	}
    
    @ResponseBody
    @GetMapping("/home/status/{status}")
	public  Reservation[] fetchDashBoardData(Model model, HttpServletRequest request, @PathVariable(value="status", required = false) String status)
	{
    	String jwtToken = jwtTokenFromCookie.extractJwtFromCookie(request);
    	// fetch reservation data from res service
    	return dashBoardService.fetchDashBoardDataForRes(status==null?ResConstants.RES_STATUS:status, jwtToken);
	}
    
    /**
     * For Guest Chart in dashboard
     * @param request
     * @return
     */
    @GetMapping("/guest-status-data")
    @ResponseBody
    public Map<String, Long> getGuestStatusData(HttpServletRequest request) 
    {
    	String jwtToken = jwtTokenFromCookie.extractJwtFromCookie(request);
    	Map<String, Long> fetchCountGuestByStatus = dashBoardService.fetchCountGuestByStatus(jwtToken);
		return fetchCountGuestByStatus;
    }
    
    @GetMapping("/create-res")
	public String createRes(Model model, HttpServletRequest request)
	{
		Reservation reservation = new Reservation();
		reservation.setStatus(ResConstants.RES_STATUS);
		String jwtToken = jwtTokenFromCookie.extractJwtFromCookie(request);
		
		//  fetch only VC rooms for availability
		List<RoomDto> allRateplanRoomData = roomsService.getAllAvlRateplanRoomData(jwtToken);
	    model.addAttribute("ratePlans", allRateplanRoomData);
		model.addAttribute("res", reservation);
		return "createReservation";
	}
    
    @PostMapping("/create-res")
	public String createReservation(@ModelAttribute("res") Reservation res, Model model, HttpServletRequest request)
	{
    	try
		{
			if (null == res)
			{
				throw new RuntimeException("Error while saving the reservation");
			}
			// getting JWT token from cookie
			String jwtToken = jwtTokenFromCookie.extractJwtFromCookie(request);
			
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", "application/json");
			headers.set("Authorization", "Bearer " + jwtToken); // adding JWT token to header
			//
			if (null!= res && null == res.getGuestID())
			{
				
				ResponseEntity<GuestDto> entity = reservationService.createGuestInfo(res, headers);
				if (entity.getStatusCode() == HttpStatus.OK)
				{
					GuestDto guest = entity.getBody();
					res.setGuestID(guest.getGuestID());
				}
				else
				{
					// Handle non-200 response codes
					model.addAttribute("error", "Reservation failed with status: " + entity.getStatusCode());
					return "createReservation";
				}
				
			}
			
			// setting Reservation status to RES if not set for create reservation
			if(null==res.getStatus())
			{
				res.setStatus(ResConstants.RES_STATUS);
			}
			// setting create date
			res.setCreateDate(LocalDate.now());
			
			ResponseEntity<Reservation> responseEntity = reservationService.createReservation(res, headers);
			// Check response status code
			if (responseEntity.getStatusCode() == HttpStatus.CREATED)
			{
				Reservation reservation = responseEntity.getBody();
				model.addAttribute("success", "Reservation saved successfully!");
				model.addAttribute("reservation", reservation);
				return "createReservation";

			}
			else
			{

				model.addAttribute("error", "Reservation failed with status: " + responseEntity.getStatusCode());
			}
			
		}
    	catch (HttpStatusCodeException e)
		{
    		// Handle HTTP errors (4xx and 5xx)
    		model.addAttribute("error", "Reservation failed with error: " + e.getResponseBodyAsString());
		}
    	catch (Exception e)
		{
    		// Handle any other exceptions
            model.addAttribute("error", "An unexpected error occurred: " + e.getMessage());
		}
    	return "createReservation";
	}
    
    /////////////////////////  modify res ///////////////////////
    @GetMapping("/modify-res/resID/{resID}")
   	public String modifyResPageForResID(Model model, @PathVariable("resID") Long resID, HttpServletRequest request)
   	{
       // fetch Res Data from RES table by ResID
    	HttpHeaders headers = new HttpHeaders();
    	headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
    	String jwtToken = jwtTokenFromCookie.extractJwtFromCookie(request);
    	headers.add(HttpHeaders.AUTHORIZATION, ResConstants.BEARER + jwtToken);
       ResponseEntity<Reservation> reservationData = reservationService.fetchReservationData(resID, headers);
       if(reservationData.getStatusCode() == HttpStatus.OK)
       {
    	   Reservation reservation = reservationData.getBody();
    	   //reservation.setStatus(ResConstants.MOD);
           model.addAttribute("res",reservation);
           List<RoomDto> allAvlRateplanRoomData = new ArrayList<>();
           allAvlRateplanRoomData.addAll(roomsService.getAllAvlRateplanRoomData(jwtToken));
           allAvlRateplanRoomData.add(new RoomDto(reservation.getRoomnum(), reservation.getRatePlan()));
   	       model.addAttribute("ratePlans", allAvlRateplanRoomData);
       }
   	   return "modifyReservation";
   	}
    
    @PostMapping("/modify-res")
   	public String modifyReservation(@ModelAttribute("res") Reservation model, HttpServletRequest request)
   	{
    	if(null==model)
    	{
    		throw new RuntimeException("Error while saving the reservation");
    	}
    	// setting Reservation status as RES for create reservation
    	model.setStatus(ResConstants.MOD);
    	// setting the header values
    	HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");

		HttpEntity<Reservation> requestEntity = new HttpEntity<Reservation>(model, headers);

    	ResponseEntity<Reservation> responseEntity = restTemplate.exchange(RES_SERVICE_URL+"reservaion", HttpMethod.POST,requestEntity, Reservation.class);
    	// Check response status code
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
        	LOGGER.error("........................."+responseEntity.getBody());
        	return "saveReservation";
        	
        } else {
        	throw new RuntimeException("Error while saving the reservation"+responseEntity.getStatusCode());
        }
   	}
    @GetMapping("/modify-res")
   	public String modifyResPage(Model model, HttpServletRequest request)
   	{
    	model.addAttribute("res",new Reservation());
    	model.addAttribute("ckin",true);
   	   return "modifyReservation";
   	}
    ///////		Check-In		///////
    @PostMapping("/checkin")
    public String performCheckInRes(@ModelAttribute("res") Reservation res, HttpServletRequest request)
    {
    	res.setStatus(ResConstants.REG_STATUS);
    	return "modifyReservation";
    }
    /////////////
    @ResponseBody
    @GetMapping("/fetchRes")
    public List<Reservation> fetchResData(@RequestParam(name="resID", required = false) Long resID, HttpServletRequest request)
    {
    	String jwtToken = jwtTokenFromCookie.extractJwtFromCookie(request);
    	String url = UriComponentsBuilder.fromHttpUrl(RES_SERVICE_URL+"search-reservaion?")
    			.queryParam("resID", resID)
    			.toUriString();
    	HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.set("Authorization", ResConstants.BEARER + jwtToken);
        
        ResponseEntity<Reservation[]> responseEntity = reservationService.searchReservation(url, headers);
 	   if (responseEntity.getStatusCode() == HttpStatus.OK)
 	   {
 		  Reservation[] reservations = responseEntity.getBody();
		  LOGGER.error("response: "+reservations);
 		   return List.of(reservations);
 	   }
        		
    	return null;
    }
    //////////////////////////  search reservation   /////////////////////////
    @GetMapping({"/search-res","/search-res/{search}"})
	public String searchReservation(@ModelAttribute("res") ReservationSearchDTO res,
			@PathVariable(name = "search", required = false) boolean search, Model model, HttpServletRequest request)
	{
       LOGGER.error("===>  "+search);
       String jwtToken = jwtTokenFromCookie.extractJwtFromCookie(request);
       if(search)
       {
    	   String url = UriComponentsBuilder.fromHttpUrl(RES_SERVICE_URL+"search-reservaion?")
    	   .queryParam("firstName", res.getFirstName().trim())
    	   .queryParam("lastName", res.getLastName().trim())
    	   .queryParam("status", res.getStatus())
    	   .queryParam("arriveDate", res.getArriveDate())
    	   .queryParam("arriveTime", res.getArriveTime())
    	   .queryParam("deptDate", res.getDeptDate())
    	   .queryParam("deptTime", res.getDeptTime())
    	   .queryParam("roomnum", res.getRoomnum())
    	   .queryParam("guestID", res.getGuestID())
    	   .toUriString();
    	   
    	   HttpHeaders headers = new HttpHeaders();
           headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
           headers.set("Authorization", ResConstants.BEARER + jwtToken); // adding JWT token to header
           
    	   //HttpEntity<ReservationSearchDTO> requestBody = new HttpEntity<>(res, headers);
    	   ResponseEntity<Reservation[]> responseEntity = reservationService.searchReservation(url, headers);
    	   if (responseEntity.getStatusCode() == HttpStatus.OK)
    	   {
    		   LOGGER.error("====>  "+List.of(responseEntity.getBody()));
    		   model.addAttribute("searchResult", null!=responseEntity.getBody()?List.of(responseEntity.getBody()):List.of());
    	   }
       }
   	   return "searchReservation";
   	}
    
    @GetMapping("/get-rooms-by-rateplan")
    @ResponseBody
    public List<Integer> getRoomsByRatePlan(@RequestParam("ratePlan") String ratePlan) {
        // Logic to fetch rooms based on the rate plan
        List<Integer> rooms = roomsService.getRoomsByRatePlan(ratePlan);
        return rooms;
    }


}
