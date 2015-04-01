package com.finateltechhbm.model

import com.finateltechhbm.dto.PaymentType;

class PaymentAtempt {

	Long id;
	
	Date paymentdate;
	
	Customer customer
	
	PaymentType paymentType
	
	String merchantTranactionId;
	
	DealOrders dOrders  
	
}
