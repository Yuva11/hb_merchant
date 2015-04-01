package com.finateltechhbm.dto

import groovy.transform.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;
import java.util.Set; 

import lombok.ToString;
 
@EqualsAndHashCode(includes="id")
@ToString
class MerchantDto implements Serializable{
   Long id;
	 
	String name; //Brand name should be unique - AlphaNumeric & symbols[75]
	  
	MerchantCategoryDTO category; //Merchant serves Different Categories
	 
	MerchantSpecialityDTO speciality;//Merchant can prepare many kind of cusines
	   
	boolean partyDealAllowed; //Party Allowed or not
	 
	Status status; //Is the Merchant is Active or not 
	
	String webUrl;
	 
	String logoUrl;
	
	String code
	
	List<MerchantBranchDto> branches; // Merchant Can Have many Branches
  
	//Bank Details 
	MerchantBankDTO bank;
	
	String bankBranch;
	 
	String bankIFSC;
	
	String bankAccno; //Merchant Account Number
	
	boolean deletestatus; 
	
	String createdDate;
	
	GroupEntityDTO groupentity; 
	
	UserDto createdBy; 
	
	boolean checkImage;
}
