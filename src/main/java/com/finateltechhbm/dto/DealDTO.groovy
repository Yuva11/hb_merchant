package com.finateltechhbm.dto;  
import com.finateltechhbm.dto.DeliveryTypeDTO;
import com.finateltechhbm.dto.FoodCategoryDto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set; 
 
class DealDTO implements Serializable{
	Long id;
	
	String name;//Name of the Deal
	
	ContentType contentType ;//Type of Deal -{BUYOUT,PROMOTION,ADVERTISEMENT}
	
	String details;//Details of the Deal
	
	String detailText;//Details of the Deal
	
	DealTemplateDTO dealTemplate;//NORMAL OR FULL_IMAGE	
	
	Double originalPrice;//Original Price of the Deal
	
	Double dealPrice;//Deal Price for the Deal

	Double dealDiscountPercent;//Discount Price for the Deal
	
	String deliveryTime;//Delivery TIme for the deal
	
	Double deliveryCharge;//Delivery Charge on ordering Deal 
	
	FoodType foodType;//Food Type for the Deal -- VEG/NON_VEG
	
	FoodCategoryDto foodcategory;//Category of food for deal 
	
	Set<CusineDto> dealCusines;//Cusines for the Deal
	
	List<DeliveryTypeDTO> deliveryType;
	
	String startDate;//Deal Offer Start Date
	
	String endDate;//Deal Offer End Date
	
	/*String availableTimeFrom;//Deal Order From Available time 
	
	String availableTimeTo;//Deal Order To Available time
	*/
	Integer openingQuantity;//NO of openeing quantities for the deal
	
	DealStatus status;//Is the deal is ACTIVE , BLOCKED , PUBLISHED
	
	boolean published = false;//Is the deal is Published or Not
	
	RedeemType reedem;//Payment or Loyality Points
	
	String couponcode;//Only for promotions
	 
	MerchantBranchDto merchantbranch;//Deal belongs to which Merchant Branch
	
	boolean deletestatus;//Is the deal delete status
	
	String createdDate;//Deal created date
	 
	String lastModifiedDate;
	
	String imageURL; 
	
	Integer rating;
	
	double distance;
	
	boolean favourite;
	
	List<CustomerDto> customer;
	
	Boolean isliked;
	
	List<DealFeedbackDTO> feedbacks;
	
	List<DealViewsDTO> views;
	
	Integer likeCount;
	
	Integer feedBackCount;
	
	Integer viewCount;
	
	Integer served;
	
	Integer pending;

	Integer availability;
	
	def totalAmount;
	
	Boolean imageIsAvail;
}
