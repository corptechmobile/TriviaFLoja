	package br.com.webapp.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.webapp.model.fb.movfisctipo.MovFiscTipoFB;
import br.com.webapp.model.fb.movfisctipo.MovFiscTipoFBRN;

@FacesConverter(forClass = MovFiscTipoFB.class)
public class MovFiscTipoFBConverter implements Converter {
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			Integer codigo = Integer.valueOf(value);
			try {
				MovFiscTipoFBRN enderecoTipoRN = new MovFiscTipoFBRN();
				return enderecoTipoRN.carregar(codigo);
			} catch (Exception e) {
				throw new ConverterException("Nao foi possivel encontrar a categoria de codigo " + value + "." + e.getMessage());
			}
		}
		return null;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			MovFiscTipoFB enderecoTipo = (MovFiscTipoFB) value;
			return "" + enderecoTipo.getId();
		}
		return "";
	}
}
