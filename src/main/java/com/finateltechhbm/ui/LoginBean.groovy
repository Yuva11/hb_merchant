package com.finateltechhbm.ui

import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.finateltechhbm.service.RestService;
import com.finateltechhbm.validation.Faces;

import groovy.util.logging.Slf4j;
@Service
@Scope("request")
@Slf4j
class LoginBean {
	String emailid 
	String passWord
	@Autowired
	RestService restService
	@PostConstruct
	public void init(){
		//Invalidating Session in Login page load
		def session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if(session!=null)
			session.invalidate();
	}
	String loginPasswordReset(){
		boolean emailFlag = false
		log.info(" emailId : "+emailid);
		if(emailid == null || emailid == ""){
			Faces.addInfo("Please enter emailid")
			return "/login.xhtml";
		}
		emailFlag = restService.resetPassword(emailid)
		if(emailFlag){
			Faces.addInfo("Reset password request has been Submitted, Check your Email")
		} else {
			Faces.addInfo("Reset password request has been Submitted, Check your Email")
		}
		emailid = "";
		return "/login.xhtml";
	}
}
