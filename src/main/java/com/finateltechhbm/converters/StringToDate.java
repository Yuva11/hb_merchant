
package com.finateltechhbm.converters;


import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value ="SDC")
public class StringToDate implements Converter {
    public StringToDate() {
    	//
    }
 
    @Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String param){
        try {
            Date dateObject = new SimpleDateFormat("hh:mm:ss a").parse(param);
           return dateObject;
            }
       catch (Exception exception) {
            throw new ConverterException(exception);
            }
    }
 
    @Override
	public String getAsString(FacesContext facesContext,  UIComponent uiComponent,   Object obj) {
      try {
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    	SimpleDateFormat formatt = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    	String dateInString = (String)obj; 
    	Date date = formatter.parse(dateInString);  
        return ""+formatt.format(date);
      } 
      catch (Exception exception) {
          throw new ConverterException(exception);
      }
    }
}
