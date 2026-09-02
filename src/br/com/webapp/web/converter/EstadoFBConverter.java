package br.com.webapp.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.webapp.model.fb.estado.EstadoFB;
import br.com.webapp.model.fb.estado.EstadoFBRN;


@FacesConverter(forClass = EstadoFB.class)
public class EstadoFBConverter implements Converter {
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				EstadoFBRN estadoRN = new EstadoFBRN();
				return estadoRN.carregar(value);
			} catch (Exception e) {
				throw new ConverterException("Nao foi possivel encontrar a categoria de codigo " + value + "." + e.getMessage());
			}
		}
		return null;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			EstadoFB estado = (EstadoFB) value;
			return estado.getId();
		}
		return "";
	}
}