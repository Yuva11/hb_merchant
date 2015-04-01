package com.finateltechhbm.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class EmailRecipient implements Serializable{

	private static final long serialVersionUID = 1L;
	@Getter @Setter private Long id;
	@Getter @Setter private String emailTo;
	@Getter @Setter private String status="S"; //S-Scheduled,X-Sent,E-Error
	
	@Getter @Setter private EmailTask emailTask;
	
	public String getEmailStatusString(){
		if(this.getStatus().equals("X"))
			return "Send";
		if(this.getStatus().equals("S"))
			return "Scheduled";
		if(this.getStatus().equals("E"))
			return "Error";
	return "";
		}
	
}
