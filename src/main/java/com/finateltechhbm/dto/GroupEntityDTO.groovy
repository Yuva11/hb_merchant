package com.finateltechhbm.dto
 
class GroupEntityDTO { 
	Long id;
	 
	String  entityName; 
	
	String mobileNumber;
	 
	String contactPerson;
	 
	String officeTelephoneNumber;
	 
	String headOfficeAddress;
 
	String groupEntityUrl; 
	
	String eMail;
	
	Status status;
	 
	List<MerchantDto> merchant;
	 
	List<GroupUserDTO> user;
}
