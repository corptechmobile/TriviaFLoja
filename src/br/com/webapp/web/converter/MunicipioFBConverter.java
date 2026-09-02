package br.com.webapp.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.webapp.model.fb.municipio.MunicipioFB;
import br.com.webapp.model.fb.municipio.MunicipioFBRN;

@FacesConverter(forClass = MunicipioFB.class)
public class MunicipioFBConverter implements Converter {
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			Integer codigo = Integer.valueOf(value);
			try {
				MunicipioFBRN municipioRN = new MunicipioFBRN();
				return municipioRN.carregar(codigo);
			} catch (Exception e) {
				throw new ConverterException("Nao foi possivel encontrar a categoria de codigo " + value + "." + e.getMessage());
			}
		}
		return null;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			MunicipioFB municipio = (MunicipioFB) value;
			return "" + municipio.getId();
		}
		return "";
	}
}
