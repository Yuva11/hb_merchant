package com.finateltechhbm.model;

import java.util.Date;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import com.finateltechhbm.dto.Status;

public class MerchantBranch {

	@Getter @Setter private Long id;
	@Getter @Setter private Merchant merchant;
	
	@Getter @Setter private String branchName;
	
	@Getter @Setter private String logo;
	
	@Getter @Setter private Double serviceTax;
	
	@Getter @Setter private String tin;
	
	@Getter @Setter private String bankName;
	@Getter @Setter private String accountNumber;
	@Getter @Setter private String address,city,pincode,landline,mobile,url;
	@Getter @Setter private MerchantCategory category;
	
	@Getter @Setter private Set<Cusine> specialities;
	
	@Getter @Setter private Set<OperatingTime> timeSlots;
	
	@Getter @Setter private Double lattitue,longitude;
	
	@Getter @Setter private Status status;	

	@Getter @Setter private User createdBy;
	
	@Getter @Setter private Date createdDate,modifiedDate;
	@Getter @Setter private Set<Deal> deals;


}
