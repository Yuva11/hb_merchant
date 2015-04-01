package com.finateltechhbm.dto

 
class FoodCategoryDto implements Serializable{
	Long id;
	String name;
	
	String createdDate;
	
	UserDto user;
	
	String lastUpdate;
	
	boolean deletestatus;
}
