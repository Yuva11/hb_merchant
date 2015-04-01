package com.finateltechhbm.model;

import java.util.Date;
import java.util.Set;


import lombok.Getter;
import lombok.Setter;

import com.finateltechhbm.dto.FoodType;

public class Deal {
	@Getter @Setter private Long id;
	
	
	@Getter @Setter private String name;
	
	@Getter @Setter private String details;
	
	@Getter @Setter private Double originalPrice;
	
	@Getter @Setter private Double dealPrice;

	@Getter @Setter private Double dealDiscountPercent;
	
	
	@Getter @Setter private Set<Cusine> dealCusines;
	
	@Getter @Setter private FoodCategory foodcategory;
	
	@Getter @Setter private FoodType foodType;
	
	@Getter @Setter private Date startDate;
	@Getter @Setter private Date endDate;
	
	@Getter @Setter private Integer openingQuantity;
	
	@Getter @Setter private Boolean delivery;//true - doorDelivery else pickup/deliveryNotAvailable
	@Getter @Setter private Double deliveryCharge;
	@Getter @Setter private String deliveryTime;
	
	@Getter @Setter private Boolean published;

	@Getter @Setter private String availableTime;

	@Getter @Setter private MerchantBranch merchantbranch;
	
	
}
