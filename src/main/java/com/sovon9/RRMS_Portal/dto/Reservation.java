package com.sovon9.RRMS_Portal.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class Reservation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long rrID;
	private Long resID;
	private Long guestID;
	@Pattern(regexp = "[a-zA-Z]+")
	private String firstName;
	@Pattern(regexp = "[a-zA-Z]+")
	private String lastName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate createDate;
	private String status;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate arriveDate;
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime arriveTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate deptDate;
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime deptTime;
	private String email;
	private String ratePlan;
	private Integer roomnum;
	// payment data
	@NotBlank
	private String paymentType;
	@Pattern(regexp = "\\d{16}", message = "Card number must be 16 digits")
    private String cardNumber;
    private String cardExpiry;

	public Reservation() {
		super();
	}
	public Long getRrID() {
		return rrID;
	}
	public void setRrID(Long rrID) {
		this.rrID = rrID;
	}
	public Long getResID() {
		return resID;
	}
	public void setResID(Long resID) {
		this.resID = resID;
	}
	public Long getGuestID() {
		return guestID;
	}
	public void setGuestID(Long guestID) {
		this.guestID = guestID;
	}
	public String getFirstName()
	{
		return firstName;
	}
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	public String getLastName()
	{
		return lastName;
	}
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getRoomnum()
	{
		return roomnum;
	}
	public void setRoomnum(Integer roomnum)
	{
		this.roomnum = roomnum;
	}
	public LocalDate getCreateDate()
	{
		return createDate;
	}
	public void setCreateDate(LocalDate createDate)
	{
		this.createDate = createDate;
	}
	public LocalDate getArriveDate()
	{
		return arriveDate;
	}
	public void setArriveDate(LocalDate arriveDate)
	{
		this.arriveDate = arriveDate;
	}
	public LocalTime getArriveTime()
	{
		return arriveTime;
	}
	public void setArriveTime(LocalTime arriveTime)
	{
		this.arriveTime = arriveTime;
	}
	public LocalDate getDeptDate()
	{
		return deptDate;
	}
	public void setDeptDate(LocalDate deptDate)
	{
		this.deptDate = deptDate;
	}
	public LocalTime getDeptTime()
	{
		return deptTime;
	}
	public void setDeptTime(LocalTime deptTime)
	{
		this.deptTime = deptTime;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getRatePlan()
	{
		return ratePlan;
	}
	public void setRatePlan(String ratePlan)
	{
		this.ratePlan = ratePlan;
	}
	public String getPaymentType()
	{
		return paymentType;
	}
	public void setPaymentType(String paymentType)
	{
		this.paymentType = paymentType;
	}
	public String getCardNumber()
	{
		return cardNumber;
	}
	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}
	public String getCardExpiry()
	{
		return cardExpiry;
	}
	public void setCardExpiry(String cardExpiry)
	{
		this.cardExpiry = cardExpiry;
	}
	
}
