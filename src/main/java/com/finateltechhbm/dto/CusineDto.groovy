package com.finateltechhbm.dto

import java.io.Serializable;
 

class CusineDto implements Serializable{
	Long id;
	
	String name;
	
	String createdDate;
	
	UserDto user;
	
	String lastUpdate;
	
	boolean deletestatus;
}
