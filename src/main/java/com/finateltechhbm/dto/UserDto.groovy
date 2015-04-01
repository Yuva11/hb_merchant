package com.finateltechhbm.dto

import java.io.Serializable;
  

class UserDto implements Serializable{
	Long id;
	
	String userName;
	
	String name;
	
	String passwordHash;
	
	String confirmPassword;
	
	Long contactNumber;
	
	Status status;
	
	String provider;
	
	String socialUserId;
			
	boolean deleteStatus;
	
	RoleDto role;
	
	MerchantDto merchant;
	
	Role userType;
	 
	String createdDate;
	
}
