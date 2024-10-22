package com.sovon9.RRMS_Portal.dto;

public class RoomDto
{
	private int roomNum;
	private String roomStatus;
	private String ratePlan;
	private String roomDesc;
	public RoomDto()
	{
		super();
	}
	public int getRoomNum()
	{
		return roomNum;
	}
	public void setRoomNum(int roomNum)
	{
		this.roomNum = roomNum;
	}
	public String getRoomStatus()
	{
		return roomStatus;
	}
	public void setRoomStatus(String roomStatus)
	{
		this.roomStatus = roomStatus;
	}
	public String getRatePlan()
	{
		return ratePlan;
	}
	public void setRatePlan(String ratePlan)
	{
		this.ratePlan = ratePlan;
	}
	public String getRoomDesc()
	{
		return roomDesc;
	}
	public void setRoomDesc(String roomDesc)
	{
		this.roomDesc = roomDesc;
	}
	
}
