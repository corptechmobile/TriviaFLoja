package br.com.webapp.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

public class NumberPhoneConverter implements Converter {
    
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
         String numberPhone = value;
         if (value!= null && !value.equals(""))
              numberPhone = value.replaceAll("\\.", "").replaceAll("\\-", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("/", "");

         return numberPhone;
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
         String numberPhone = (String) value;
         if (numberPhone != null && numberPhone.length() == 10)
             numberPhone = "("+numberPhone.substring(0, 2) + ")" + numberPhone.substring(2, 6) + "." + numberPhone.substring(6, 10);
         else if (numberPhone != null && numberPhone.length() == 11)
             numberPhone = "("+numberPhone.substring(0, 2) + ")" + numberPhone.substring(2, 7) + "." + numberPhone.substring(7, 11);

         return numberPhone;
    }
}