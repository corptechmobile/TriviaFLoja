package br.com.webapp.web.converter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("currencyFormat")
public class CurrencyFormat implements Converter {

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
	        try {
	            Locale l=new Locale("pt", "br");
	            NumberFormat format = NumberFormat.getNumberInstance(l);
	            format.setMinimumFractionDigits(2);
	            format.setMaximumFractionDigits(2);
	            return format.format(new BigDecimal(new BigDecimal(value.toString()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
	        	
	        } catch (Exception exception) {
	            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Number"));
	        }
	    }
	}

}
