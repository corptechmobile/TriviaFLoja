package br.com.webapp.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.webapp.model.fb.eventofinanceiro.EventoFinanceiroFB;
import br.com.webapp.model.fb.eventofinanceiro.EventoFinanceiroFBRN;

@FacesConverter(forClass = EventoFinanceiroFB.class, value="eventoFinanceiroFBConverter")
public class EventoFinanceiroFBConverter implements Converter {

		public Object getAsObject(FacesContext context, UIComponent component, String value) {
			if (value != null && value.trim().length() > 0) {
				Integer codigo = Integer.valueOf(value);
				try {
					EventoFinanceiroFBRN eventoFinanceiroFBRN = new EventoFinanceiroFBRN();
					return eventoFinanceiroFBRN.carregar(codigo);
				} catch (Exception e) {
					throw new ConverterException("Nao foi possivel encontrar a categoria de codigo " + value + "." + e.getMessage());
				}
			}
			return null;
		}

		public String getAsString(FacesContext context, UIComponent component, Object value) {
			if (value != null) {
				EventoFinanceiroFB eventoFinanceiroFB = (EventoFinanceiroFB) value;
				return "" + eventoFinanceiroFB.getId();
			}
			return "";
		}
}
