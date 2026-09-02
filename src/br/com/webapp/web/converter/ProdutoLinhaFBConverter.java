package br.com.webapp.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.webapp.model.fb.produtolinha.ProdutoLinhaFB;
import br.com.webapp.model.fb.produtolinha.ProdutoLinhaFBRN;


@FacesConverter(forClass = ProdutoLinhaFB.class, value="produtoLinhaFBConverter")
public class ProdutoLinhaFBConverter implements Converter {
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			Integer codigo = Integer.valueOf(value);
			try {
				ProdutoLinhaFBRN prodLinhaRN = new ProdutoLinhaFBRN();
				return prodLinhaRN.carregar(codigo);
			} catch (Exception e) {
				throw new ConverterException("Nao foi possivel encontrar a categoria de codigo " + value + "." + e.getMessage());
			}
		}
		return null;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			ProdutoLinhaFB prodLinha = (ProdutoLinhaFB) value;
			return "" + prodLinha.getId();
		}
		return "";
	}
}
