package br.com.webapp.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("anoMesConverter")
public class AnoMesConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
         String anoMes = value;
         if (value!= null && !value.equals(""))
        	 anoMes = value.replaceAll("/", "");

         return anoMes;
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
        /*
         * Irá converter CPF não formatado para um com pontos e traço.
         * Ex.: 35524519887 torna-se 355.245.198-87.
         */
         String anoMes = (String) value;
         if (anoMes != null && anoMes.length() == 7)
        	 anoMes = anoMes.substring(0, 4) + "/" + anoMes.substring(6, 8);

         return anoMes;
    }

}
