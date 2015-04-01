package com.finateltechhbm.dto
 
import java.io.Serializable;

import com.finateltechhbm.dto.CustomerDto;
import com.finateltechhbm.dto.PaymentAttemptDto;

class DealOrdersDto implements Serializable{
	Long id;
	Double amount;
	
	String dealordersdate;
	
	String orderId;
	
	OrderStatus status
	
	CustomerDto customer
	
	List<OrderDetailDto> orderDetails = new ArrayList<OrderDetailDto>()
	
	MerchantDto merchant
	
	PaymentAttemptDto paymentAtempt
	
	boolean deliveryStatus;
}
