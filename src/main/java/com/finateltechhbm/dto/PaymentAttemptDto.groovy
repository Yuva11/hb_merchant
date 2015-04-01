package com.finateltechhbm.dto

import java.io.Serializable;

import com.finateltechhbm.dto.CustomerDto;
import com.finateltechhbm.dto.DealOrdersDto;

class PaymentAttemptDto implements Serializable{
	Long id;
	
	String paymentdate;
	
	CustomerDto customer
	
	PaymentType paymentType
	
	String merchantTranactionId
	
	DealOrdersDto dOrders
}
