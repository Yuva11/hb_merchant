package com.finateltechhbm.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.finateltechhbm.dto.NotificationPreference;
import com.finateltechhbm.model.Cusine;


public class Customer  {// extends BaseEntity {
	private Long id;
	
	private Date createdDate;
	
	private Date modifiedDate;

	String firstName,lastName;

	String email;
	
	String address;
	
	String mobileNumber;
	private Set<Cusine> favouriteCusines = new HashSet<Cusine>();
	
	private Set<NotificationPreference> NotificationPreference = new HashSet<NotificationPreference>() ;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Set<Cusine> getFavouriteCusines() {
		return favouriteCusines;
	}

	public void setFavouriteCusines(Set<Cusine> favouriteCusines) {
		this.favouriteCusines = favouriteCusines;
	}

	public Set<NotificationPreference> getNotificationPreference() {
		return NotificationPreference;
	}

	public void setNotificationPreference(
			Set<NotificationPreference> notificationPreference) {
		NotificationPreference = notificationPreference;
	}

	
	
	
	
}
