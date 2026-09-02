package br.com.webapp.web.converter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("currencyWithoutDecimal")
public class CurrencyWithoutDecimal implements Converter {
	
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
	            Currency c = Currency.getInstance(l);
	            
	            NumberFormat format = NumberFormat.getNumberInstance(l);
	            format.setMinimumFractionDigits(0);
	            format.setMaximumFractionDigits(0);
	            return c.getSymbol(l) + " " + format.format(new BigDecimal(value.toString()));

	        } catch (Exception exception) {
	            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Number"));
	        }
	    }
	}
	
}
