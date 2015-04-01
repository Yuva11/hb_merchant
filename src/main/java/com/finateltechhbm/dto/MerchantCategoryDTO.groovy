package com.finateltechhbm.dto

import java.io.Serializable;
 

class MerchantCategoryDTO implements Serializable{

	Long id;
	
	String name;
	
	String createdDate;
	
	UserDto user;
	
	String lastUpdate; 
	
	boolean deletestatus;
}
