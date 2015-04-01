package com.finateltechhbm.dto

import java.io.Serializable;

class PaymentsDto implements Serializable{
	Long id;
	
	String merchantId
	
	String merchantKeyId//merchant_key_id
	
	String merchantTransactionId //merchant_transaction_id
	
	String payzippyTransactionId //payzippy_transaction_id
	
	TransactionStatus transactionStatus;
	
	String transactionResponseCode//transaction_response_code
	
	String transactionResponseMessage //transaction_response_message
	
	String paymentMethod//payment_method
	
	String bankName// bank_name
	
	Integer transactionAmount //transaction_amount
	
	String transactionCurrency//transaction_currency
	
	String transactionTime//transaction_time
	
	String fraudAction //fraud_action
	
	String fraudDetails//fraud_details
	
	Boolean isInternational //is_international
	
	String version
	
	String udf1
	
	String udf2
	
	String udf3
	
	String udf4
	
	String udf5
	
	String terminalId//terminal_id
	
	String transactionType//transaction_type
	
	String hashMethod
	
	String bankTransactionId //bank_transaction_id
	
	String hash
	
	String pgTrackid //pg_trackid
	
	String pgId//pg_id
	
	String pgMid//pg_mid
	
	String pgAuthcode //pg_authcode
	
}
