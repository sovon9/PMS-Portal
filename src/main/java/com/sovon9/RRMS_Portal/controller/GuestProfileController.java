package com.sovon9.RRMS_Portal.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.sovon9.RRMS_Portal.dto.GuestDto;
import com.sovon9.RRMS_Portal.service.ExtractJwtTokenFromCookie;
import com.sovon9.RRMS_Portal.service.GuestInfoService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/portal")
public class GuestProfileController extends BaseController
{
	@Value("${GUESTINFO_SERVICE_URL}")
	private String GUESTINFO_SERVICE_URL;
	@Autowired
	private ExtractJwtTokenFromCookie jwtTokenFromCookie;
	@Autowired
	private GuestInfoService guestInfoService;
	
	Logger LOGGER = LoggerFactory.getLogger(GuestProfileController.class);
	
	 ///////////////////////    search guest ID    ///////////////////
    @PostMapping("/search-guest")
    @ResponseBody
    public List<GuestDto> searchGuest(@RequestBody Map<String, String> searchCriteria, HttpServletRequest request, Model model)
    {
//    	try
//		{
			// get data from guestifo service if any guest matches the search criteria
			String url = UriComponentsBuilder.fromHttpUrl(GUESTINFO_SERVICE_URL + "guestinfo?")
					.queryParam("guestID", searchCriteria.get("guestID"))
					.queryParam("firstName", searchCriteria.get("firstName"))
					.queryParam("lastName", searchCriteria.get("lastName"))
					.queryParam("birthDate", searchCriteria.get("birthDate"))
					.queryParam("phno", searchCriteria.get("phno")).toUriString();
			// getting JWT token from cookie
			String jwtToken = jwtTokenFromCookie.extractJwtFromCookie(request);
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", "application/json");
			headers.set("Authorization", "Bearer " + jwtToken); // adding JWT token to header

			ResponseEntity<GuestDto[]> responseEntity = guestInfoService.searchGuestData(url, headers);
			if (responseEntity.getStatusCode() != HttpStatus.OK)
			{
				LOGGER.error("Error in fetching guest data " + responseEntity.getStatusCode().value());
			}

			return List.of(responseEntity.getBody());
//		}
//    	catch (Exception e) {
//			model.addAttribute("error", "Error happened while fetching Guest Data: "+e.getMessage());
//			return List.of();
//		}
    }
    
    @GetMapping("/guest-profile-update")
    public String getGuestProfileUpdatePage(Model model)
    {
    	model.addAttribute("guest",new GuestDto());
    	return "guestProfileUpdate";
    }
    
    @GetMapping("/save-guest")
    public String test(@ModelAttribute("guest") GuestDto guest, Model model, HttpServletRequest request)
	{
		if (null != guest && null != guest.getGuestID())
		{
			String url = UriComponentsBuilder.fromHttpUrl(GUESTINFO_SERVICE_URL + "guestinfo").toUriString();
			// setting the header values
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", "application/json");
			// save guest details
			ResponseEntity<GuestDto> entity = guestInfoService.saveGuestData(url, guest, headers);
			if (entity.getStatusCode() == HttpStatus.OK)
			{
				model.addAttribute("success", "Guest Info saved successfully!");
			}
			else
			{
				// Handle non-200 response codes
				model.addAttribute("error", "Guest Info failed with status: " + entity.getStatusCode());
			}
		}
		else
		{
			// if guestid or guest data not populated correctly
			model.addAttribute("error", "No Guest Details/Guest ID populate properly");
		}
		return "guestProfileUpdate";
	}
    
}
