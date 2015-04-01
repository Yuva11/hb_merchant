package com.finateltechhbm.dto

import java.io.Serializable;
import java.util.Date;
import java.util.List;
 
class RoleDto implements Serializable{

	Long id;
	
	String rolename;
	
	String status;
	
	String description;
	
	Date serverdate;
	
    List<FeatureDTO> featuresList; 	
}
