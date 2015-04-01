package com.finateltechhbm.dto

class MerchantBranchDto implements Serializable{
	Long id;

	MerchantDto merchant; // this Branch belongs to which Merchant

	String branchName; //Name of the Branch

	String address;

	long pincode;

	String startTime;//Merchant Branch start time

	String endTime;//Merchant Branch end time

	CityDto city;

	String mobileNumber;

	String altMobileNumberOne;

	String altMobileNumberTwo;

	String altMobileNumberThree;

	String eMail;

	String altEmailOne;

	String altEmailTwo;

	String altEmailThree;

	LocationDTO location;

	Double lattitue,longitude; // Latitude and Longitude of the Branch

	Status status;	//Is the Branch is Active or Blocked

	List<DealDTO> deals; //Deals Created by this branch

	Integer rating;

	boolean deletestatus;

	UserDto createdBy;

	String createdDate;
}
