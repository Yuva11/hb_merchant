package com.finateltechhbm.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class EmailTask implements Serializable{

	private static final long serialVersionUID = 1L;
	@Getter @Setter private Long id;
	
	@Getter @Setter private Date ts=new Date();
	@Getter @Setter private Date processedTime;
	@Getter @Setter private String module,fromEmailId,subject,message,password;
	@Getter @Setter private Integer priority; //IMMEDIATE-0,HIGH-1,NORMAL-2,LOW-3,CRITICAL-4
	@Getter @Setter private String 	ccList;
	
	
	
	@Getter @Setter private Boolean sendIndividual=false;
	@Getter @Setter private List<EmailRecipient> recipientList;
	
	@Getter @Setter private String emailStatus="S"; //S-Scheduled,X-Sent,E-Error
	
	@Getter @Setter private Long campaignLaunchId=null;
	
	public EmailTask(){
		recipientList=new ArrayList<EmailRecipient>();
	}
	
	public void touch(){
		recipientList.size();
	}
	
	public enum Priority{
		IMMEDIATE(0),HIGH(1),NORMAL(2),LOW(3),CRITICAL(4);
		@Override public String toString() {
		   //only capitalize the first letter
			String s = super.toString();
			return s.substring(0, 1) + s.substring(1).toLowerCase();
		}
		 private int value;
		 private Priority(int c) {
		    value = c;
		 }
	}
	
	
	public String getFormatedCreatedDate() {
		if (ts != null) {
			SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			return s.format(this.ts);
		} else
			return null;

	}

	public Boolean getCheck(){
		Boolean flag=false;
		
		if(this.fromEmailId!=null && this.fromEmailId.trim().length()!=0 &&
				this.password!=null && this.password.trim().length()!=0)
			flag=true;
		
		return flag;
	}
	
}
