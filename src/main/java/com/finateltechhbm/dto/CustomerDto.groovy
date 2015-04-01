
package com.finateltechhbm.dto

import java.io.Serializable;
import java.util.List;
 
import com.finateltechhbm.dto.CusineDto;


class CustomerDto implements Serializable {

	Long id;
	
	String createdDate;
	
	String modifiedDate;
	
	String dob;
	
	String firstName,lastName;
	
	String email;
	
	Boolean status;
	
	String authenticationId;
	
	String address;
	
	String mobileNumber;
	
	Set<CusineDto> favouriteCusines=new HashSet<CusineDto>();

	List<FoodTypeDTO> favouriteFoodtype = new ArrayList<FoodTypeDTO>();
	
	Set<MerchantBranchDto> favouriteRestaurant = new HashSet<MerchantBranchDto>(); 
	
	UserDto user;
	
}

