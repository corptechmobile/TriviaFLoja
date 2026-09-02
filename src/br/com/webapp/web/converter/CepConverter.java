package br.com.webapp.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("cepConverter")
public class CepConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
        /*
         * Irá converter CPF formatado para um sem pontos e traço.
         * Ex.: 355.245.198-87 torna-se 35524519887.
         */
         String cep = value;
         if (value!= null && !value.equals(""))
              cep = value.replaceAll("\\-", "");

         return cep;
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
        /*
         * Irá converter CPF não formatado para um com pontos e traço.
         * Ex.: 35524519887 torna-se 355.245.198-87.
         */
         String cep = (String) value;
         if (cep != null && cep.length() == 8)
              cep = cep.substring(0, 5) + "-" + cep.substring(5, 8);

         return cep;
    }

}
