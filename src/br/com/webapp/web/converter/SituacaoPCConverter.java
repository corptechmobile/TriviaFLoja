package br.com.webapp.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.webapp.model.fb.coletorpc.SituacaoPC;
import br.com.webapp.model.fb.coletorpc.SituacaoPCRN;



@FacesConverter(forClass = SituacaoPC.class, value="situacaoPCConverter")
public class SituacaoPCConverter implements Converter {
	
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				return SituacaoPCRN.carregar(value);
			} catch (Exception e) {
				throw new ConverterException("Nao foi possivel encontrar a categoria de codigo " + value + "." + e.getMessage());
			}
		}
		return null;
	}
	
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			SituacaoPC situacaoPC = (SituacaoPC) value;
			return "" + situacaoPC.getId();
		}
		return "";
	}
}
