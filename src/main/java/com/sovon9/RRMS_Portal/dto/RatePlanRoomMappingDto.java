package com.sovon9.RRMS_Portal.dto;

public class RatePlanRoomMappingDto
{
	private Integer roomnum;
	private String rateplan;
	public RatePlanRoomMappingDto()
	{
		super();
	}
	public RatePlanRoomMappingDto(Integer roomnum, String rateplan)
	{
		super();
		this.roomnum = roomnum;
		this.rateplan = rateplan;
	}
	public Integer getRoomnum()
	{
		return roomnum;
	}
	public void setRoomnum(Integer roomnum)
	{
		this.roomnum = roomnum;
	}
	public String getRateplan()
	{
		return rateplan;
	}
	public void setRateplan(String rateplan)
	{
		this.rateplan = rateplan;
	}
	
}
