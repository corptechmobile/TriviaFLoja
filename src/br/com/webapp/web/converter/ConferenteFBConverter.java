package br.com.webapp.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.webapp.model.fb.conferente.ConferenteFB;
import br.com.webapp.model.fb.conferente.ConferenteFBRN;

@FacesConverter(forClass = ConferenteFB.class, value="conferenteFBConverter")
public class ConferenteFBConverter implements Converter {
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			Integer codigo = Integer.valueOf(value);
			try {
				ConferenteFBRN conferenteFBRN = new ConferenteFBRN();
				return conferenteFBRN.carregar(codigo);
			} catch (Exception e) {
				throw new ConverterException("Nao foi possivel encontrar o confente de codigo " + value + "." + e.getMessage());
			}
		}
		return null;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && !"".equals(value)) {
			ConferenteFB conferenteFB = (ConferenteFB) value;
			return "" + conferenteFB.getId();
		}
		return "";
		
	}
}
