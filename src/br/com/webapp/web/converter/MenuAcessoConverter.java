package br.com.webapp.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.webapp.model.menu.MenuAcesso;
import br.com.webapp.model.menu.MenuAcessoRN;

@FacesConverter(forClass = MenuAcesso.class)
public class MenuAcessoConverter implements Converter {
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				MenuAcessoRN menuAcessoRN = new MenuAcessoRN();
				return menuAcessoRN.carregar(Integer.parseInt(value));
			} catch (Exception e) {
				throw new ConverterException("Nao foi possivel encontrar a categoria de codigo " + value + "." + e.getMessage());
			}
		}
		return null;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			MenuAcesso menuAcesso = (MenuAcesso) value;
			return "" + menuAcesso.getId();
		}
		return "";
	}
}
