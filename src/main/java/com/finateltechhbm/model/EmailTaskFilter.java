package com.finateltechhbm.model;

import java.util.Date;


public class EmailTaskFilter {
	
	private String module,status; //S-Scheduled,X-Sent,E-Error
	
	private  Date startDate,endDate;
	//private Page page;
	
	public EmailTaskFilter(){}
	
	public enum Status{
		SCHEDULED("S"),SENT("X"),ERROR("E");
		@Override public String toString() {
			   //only capitalize the first letter
				String s = super.toString();
				return s.substring(0, 1) + s.substring(1).toLowerCase();
			}
			 private String value;
			 private Status(String c) {
			    value = c;
			 }
	}
	
	public EmailTaskFilter(String module,Status status,Date startDate,Date endDate){//,Page page){
		this.setModule(module);
		this.setStatus(status.value);
		this.startDate=startDate;
		this.endDate=endDate;
		//this.page=page;
	}
	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
