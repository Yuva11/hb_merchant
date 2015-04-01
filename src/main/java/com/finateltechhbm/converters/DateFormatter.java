package com.finateltechhbm.converters;


import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value ="DC")
public class DateFormatter implements Converter {
    public DateFormatter() {
    	//
    }
 
    @Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String param){
        try {
            Date dateObject = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a").parse(param);
           return dateObject;
            }
       catch (Exception exception) {
            throw new ConverterException(exception);
            }
    }
 
    @Override
	public String getAsString(FacesContext facesContext, 
                              UIComponent uiComponent, 
                              Object obj) {
      try {
    	  Date dateObject=(Date)obj;
    	  SimpleDateFormat rdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
    	  StringBuilder dateString = new StringBuilder( rdf.format( dateObject) );
    	  
            return ""+dateString;
      
         } 
      catch (Exception exception) {
          throw new ConverterException(exception);
          }
    }
}
