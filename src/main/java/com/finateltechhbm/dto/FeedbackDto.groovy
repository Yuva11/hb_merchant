package com.finateltechhbm.dto

import java.io.Serializable;

class FeedbackDto implements Serializable{

	Long id;
	
	Long customerId;
	
	String date;
	
	Date serverdate;
	
	String feedbackcomment; 
	
}
