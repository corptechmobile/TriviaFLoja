package br.com.webapp.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("timeMinuteHour")
public class TimeMinuteHour implements Converter {

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
		if (value == null) {
	        return null;
	    } else {
	        if (value.toString().trim().equals("")) {
	            return null;
	        }
	        
	        String result = "";
	        int minute = Integer.parseInt(value.toString());
	        int hour = 0;
	        if(minute < 60){
	        	
	        	if(minute > 0){
	        		result = minute + "m";
	        	}else{
	        		result = "-";
	        	}
	        
	        }else if(minute == 60){
	        
	        	result =  1 + "h"; 
	        
	        }else{
	        	
	        	hour = (minute/60);
	        	minute = minute - (hour * 60);
	        	if(minute > 0){
	        		result =  hour + "h " + minute + "m"; 
	        	}else{
	        		result =  hour + "h"; 
	        	}
	        	
	        }
	        
	        return result;
	    }
	}

}
