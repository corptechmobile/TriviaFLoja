package br.com.webapp.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.webapp.model.fb.cobrtipo.CobrTipoFB;
import br.com.webapp.model.fb.cobrtipo.CobrTipoFBRN;

@FacesConverter(forClass = CobrTipoFB.class)
public class CobrTipoFBConverter implements Converter {
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			Integer codigo = Integer.valueOf(value);
			try {
				CobrTipoFBRN enderecoTipoRN = new CobrTipoFBRN();
				return enderecoTipoRN.carregar(codigo);
			} catch (Exception e) {
				throw new ConverterException("Nao foi possivel encontrar a categoria de codigo " + value + "." + e.getMessage());
			}
		}
		return null;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			CobrTipoFB enderecoTipo = (CobrTipoFB) value;
			return "" + enderecoTipo.getId();
		}
		return "";
	}
}
