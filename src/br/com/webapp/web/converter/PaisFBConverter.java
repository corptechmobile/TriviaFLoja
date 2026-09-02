package br.com.webapp.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.webapp.model.fb.pais.PaisFB;
import br.com.webapp.model.fb.pais.PaisFBRN;


@FacesConverter(forClass = PaisFB.class)
public class PaisFBConverter implements Converter {
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				PaisFBRN paisRN = new PaisFBRN();
				return paisRN.carregar(value);
			} catch (Exception e) {
				throw new ConverterException("Nao foi possivel encontrar a categoria de codigo " + value + "." + e.getMessage());
			}
		}
		return null;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			PaisFB pais = (PaisFB) value;
			return "" + pais.getId();
		}
		return "";
	}
}
