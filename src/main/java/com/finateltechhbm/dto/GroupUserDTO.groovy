package com.finateltechhbm.dto;
  
public class GroupUserDTO { 
	Long id;
	 
	String emailId; // EmailAddress
	
	String userName;
	
	String password;
	 
	String confirmPassword;
	 
	Status status;
	
	boolean deleteStatus;
	
	List<MerchantDto> merchant = new ArrayList<MerchantDto>(); 
	
	GroupEntityDTO groupentity;
    
	UserDto createdBy;
	
	String createdDate;
}
