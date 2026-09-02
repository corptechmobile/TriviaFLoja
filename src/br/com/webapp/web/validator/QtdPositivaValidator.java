package br.com.webapp.web.validator;

import java.math.BigDecimal;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("qtdPositivaValidator")
public class QtdPositivaValidator implements Validator {
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
	    try {
	        if (new BigDecimal(value.toString()).signum() < 1) {
	            FacesMessage msg = new FacesMessage("Erro!",  "Quantidade inválida.");
	            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	            throw new ValidatorException(msg); 
	        } 
	    } catch (NumberFormatException ex) {
	        FacesMessage msg = new FacesMessage("Erro!",  "Quantidade inválida.");
	        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	        throw new ValidatorException(msg); 
	    }
	}
}
