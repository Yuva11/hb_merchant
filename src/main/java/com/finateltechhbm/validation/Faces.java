package com.finateltechhbm.validation;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Faces {
	final static Logger log = LoggerFactory.getLogger(Faces.class);
	
	public static void addInfo(String str) {
		try{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, str, str));
		}catch(Exception ex){}
		log.error(str);
	}
	public static void addWarn(String str) {
		try{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, str, str));
		}catch(Exception ex){}
		log.error(str);
	}
	
	public static void addError(String str) {
		try{
			FacesContext context = FacesContext.getCurrentInstance();
			ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, str, str));
		}catch(Exception ex){}
		log.error(str);
	}
	
	public static void warn(String errorCode,Object  params){
		addMessage(FacesMessage.SEVERITY_WARN,errorCode,params);
	}

	public static void info(String errorCode,Object  params){
		addMessage(FacesMessage.SEVERITY_INFO,errorCode,params);
	}
	public static void  error(Object errorCode,Object  params){
		System.out.println("Error Code : "+errorCode+" ; params : "+params);
		addMessage(FacesMessage.SEVERITY_ERROR ,errorCode,params);
	}
	
	private static void addMessage(Severity sev,Object errorCode, Object params){
		System.out.println("Error Code : "+errorCode+" ; params : "+params);
		FacesContext context = FacesContext.getCurrentInstance();
        String template;
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
        try{
            template = bundle.getString((String) errorCode);
        }catch(MissingResourceException e){
            template = bundle.getString("internal.error");
            if(params==null){
              params = "999";
            }
        }
        String message=MessageFormat.format(template,params);
        System.out.println("Message : "+message);
        context.addMessage(null, new FacesMessage(sev,message,null));
        log.info(message);
    }
	
}
