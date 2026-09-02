package br.com.webapp.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.webapp.model.fb.cliente.ClienteFB;
import br.com.webapp.model.fb.cliente.ClienteFBRN;

@FacesConverter(forClass = ClienteFB.class, value="clienteFBConverter")
public class ClienteFBConverter implements Converter {
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				ClienteFBRN clienteFBRN = new ClienteFBRN();
				return clienteFBRN.carregar(Integer.parseInt(value));
			} catch (Exception e) {
				throw new ConverterException("Nao foi possivel encontrar a categoria de codigo " + value + "." + e.getMessage());
			}
		}
		return null;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			ClienteFB clienteFB = (ClienteFB) value;
			return "" + clienteFB.getId();
		}
		return "";
	}
}

