package br.com.webapp.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.webapp.model.fb.fretetipo.FreteTipoFB;
import br.com.webapp.model.fb.fretetipo.FreteTipoFBRN;


@FacesConverter(forClass = FreteTipoFB.class)
public class FreteTipoFBConverter  implements Converter {
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				FreteTipoFBRN freteTipoRN = new FreteTipoFBRN();
				return freteTipoRN.carregar(Integer.parseInt(value));
			} catch (Exception e) {
				throw new ConverterException("Nao foi possivel encontrar a categoria de codigo " + value + "." + e.getMessage());
			}
		}
		return null;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			FreteTipoFB freteTipo = (FreteTipoFB) value;
			return "" + freteTipo.getId();
		}
		return "";
	}

}
