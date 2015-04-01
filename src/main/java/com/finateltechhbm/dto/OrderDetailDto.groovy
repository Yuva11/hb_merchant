package com.finateltechhbm.dto

import java.io.Serializable;

class OrderDetailDto implements Serializable{
	Long id;
	
	Double cost;
	
	Integer quantity;
	
	String deliveryAddress;
	
	String deliveryType;
	
	Integer rating;

	DealDTO deal
	 
	DealOrdersDto dealorders;
}
