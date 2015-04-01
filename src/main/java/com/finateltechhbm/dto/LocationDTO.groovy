package com.finateltechhbm.dto

import java.io.Serializable;
  
class LocationDTO implements Serializable{ 
	Long id;
	
	String name;
	
	String createdDate;
	
	UserDto user;
	
	CityDto city;
	
	String lastUpdate;
	
	boolean deletestatus;
	 
	Double latitude;
	
	Double longitude;
}
