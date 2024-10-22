package com.sovon9.RRMS_Portal.dto;

import java.time.LocalDate;

public class GuestDto {

	private Long guestID;
	private String firstName;
	private String lastName;
	private LocalDate birthDate;
	private LocalDate lastStay;// can be created as a seperate class to store last stay details
	private String phno;
	private String email;
	private AddressDto address;
	private LocalDate createDate;
	private LocalDate purgeDate;
	public GuestDto() {
		super();
	}
	public Long getGuestID() {
		return guestID;
	}
	public void setGuestID(Long guestID) {
		this.guestID = guestID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public AddressDto getAddress() {
		return address;
	}
	public void setAddress(AddressDto address) {
		this.address = address;
	}
	public String getPhno() {
		return phno;
	}
	public void setPhno(String phno) {
		this.phno = phno;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public LocalDate getBirthDate()
	{
		return birthDate;
	}
	public void setBirthDate(LocalDate birthDate)
	{
		this.birthDate = birthDate;
	}
	public LocalDate getLastStay()
	{
		return lastStay;
	}
	public void setLastStay(LocalDate lastStay)
	{
		this.lastStay = lastStay;
	}
	public LocalDate getCreateDate()
	{
		return createDate;
	}
	public void setCreateDate(LocalDate createDate)
	{
		this.createDate = createDate;
	}
	public LocalDate getPurgeDate()
	{
		return purgeDate;
	}
	public void setPurgeDate(LocalDate purgeDate)
	{
		this.purgeDate = purgeDate;
	}
	
}
