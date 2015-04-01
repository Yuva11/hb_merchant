package com.finateltechhbm.dto

import java.io.Serializable;

import com.finateltechhbm.dto.CustomerDto;

class PaymentExceptionDto implements Serializable{
	Long id;
	
	Date exceptiondatetime;
	
	CustomerDto customer
	
	String merchantTransactionId
}
