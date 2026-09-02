package br.com.webapp.web.converter;

import javax.faces.component.UIComponent;


import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.webapp.model.fb.grupofinanceiro.GrupoFinanceiroFB;
import br.com.webapp.model.fb.grupofinanceiro.GrupoFinanceiroFBRN;

@FacesConverter(forClass = GrupoFinanceiroFB.class, value="grupoFinanceiroFBConverter")
public class GrupoFinanceiroFBConverter implements Converter {
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				GrupoFinanceiroFBRN grupoFinanceiroFBRN = new GrupoFinanceiroFBRN();
				return grupoFinanceiroFBRN.carregar(value);
			} catch (Exception e) {
				throw new ConverterException("Nao foi possivel encontrar a categoria de codigo " + value + "." + e.getMessage());
			}
		}
		return null;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			GrupoFinanceiroFB grupoFinanceiroFB = (GrupoFinanceiroFB) value;
			return "" + grupoFinanceiroFB.getId();
		}
		return "";
	}
	

}
