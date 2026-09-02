package br.com.webapp.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.webapp.model.fb.tipovendedor.TipoVendedorFB;
import br.com.webapp.model.fb.tipovendedor.TipoVendedorFBRN;

@FacesConverter(forClass = TipoVendedorFB.class, value="tipoVendedorFBConverter")
public class TipoVendedorFBConverter implements Converter {
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				TipoVendedorFBRN tipoVendedorFBRN = new TipoVendedorFBRN();
				return tipoVendedorFBRN.carregar(Integer.parseInt(value));
			} catch (Exception e) {
				throw new ConverterException("Nao foi possivel encontrar a categoria de codigo " + value + "." + e.getMessage());
			}
		}
		return null;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && !"".equals(value)) {
			TipoVendedorFB tipovendedorFB = (TipoVendedorFB) value;
			return "" + tipovendedorFB.getId();
		}
		return "";
	}
}
