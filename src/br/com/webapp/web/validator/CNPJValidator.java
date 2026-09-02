package br.com.webapp.web.validator;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class CNPJValidator implements Validator {

	 @Override
     public void validate(FacesContext arg0, UIComponent arg1, Object valorTela) throws ValidatorException {
          if (!validaCNPJ(String.valueOf(valorTela))) {
               FacesMessage message = new FacesMessage();
               message.setSeverity(FacesMessage.SEVERITY_ERROR);
               message.setSummary(ResourceBundle.getBundle(FacesContext.getCurrentInstance().getApplication().getMessageBundle()).getString("validator.cnpj_invalido"));
               throw new ValidatorException(message);
          }
     }
	
	/**
     * Valida CNPJ do usuário.
     *
     * @param cnpj String valor com 14 dígitos
    */
     public static boolean validaCNPJOld(String cnpj) {
    	 
          if(cnpj == null || cnpj.length() != 14)
               return false;
 
          try {
               Long.parseLong(cnpj);
          } catch (NumberFormatException e) { // CNPJ não possui somente números
               return false;
          }
 
          int soma = 0;
          String cnpj_calc = cnpj.substring(0, 12);
 
          char chr_cnpj[] = cnpj.toCharArray();
          for(int i = 0; i < 4; i++)
               if(chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9)
                    soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
 
         for(int i = 0; i < 8; i++)
              if(chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9)
                    soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
 
         int dig = 11 - soma % 11;
         cnpj_calc = (new StringBuilder(String.valueOf(cnpj_calc))).append(dig != 10 && dig != 11 ? Integer.toString(dig) : "0").toString();
         soma = 0;
         for(int i = 0; i < 5; i++)
              if(chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9)
                   soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
 
         for(int i = 0; i < 8; i++)
              if(chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9)
                   soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
 
         dig = 11 - soma % 11;
         cnpj_calc = (new StringBuilder(String.valueOf(cnpj_calc))).append(dig != 10 && dig != 11 ? Integer.toString(dig) : "0").toString();
 
         return cnpj.equals(cnpj_calc);
     }
     
     public static boolean validaCNPJ(String cnpj) {
    	    if (cnpj == null || cnpj.length() != 14)
    	        return false;

    	    // Garante que letras minúsculas sejam tratadas como maiúsculas
    	    cnpj = cnpj.toUpperCase();

    	    // Removemos o Long.parseLong que quebrava com letras

    	    int soma = 0;
    	    String cnpj_calc = cnpj.substring(0, 12);
    	    char chr_cnpj[] = cnpj.toCharArray();

    	    // --- PRIMEIRO DÍGITO VERIFICADOR ---
    	    for (int i = 0; i < 4; i++) {
    	        int valorCaractere = chr_cnpj[i] - 48;
    	        // Aceita números (0-9) e letras maiúsculas (A-Z no ASCII transformado vão de 17 a 42)
    	        if ((valorCaractere >= 0 && valorCaractere <= 9) || (valorCaractere >= 17 && valorCaractere <= 42)) {
    	            soma += valorCaractere * (6 - (i + 1));
    	        } else {
    	            return false; // Caractere inválido detectado (ex: símbolos)
    	        }
    	    }

    	    for (int i = 0; i < 8; i++) {
    	        int valorCaractere = chr_cnpj[i + 4] - 48;
    	        if ((valorCaractere >= 0 && valorCaractere <= 9) || (valorCaractere >= 17 && valorCaractere <= 42)) {
    	            soma += valorCaractere * (10 - (i + 1));
    	        } else {
    	            return false;
    	        }
    	    }

    	    int dig = 11 - soma % 11;
    	    cnpj_calc = (new StringBuilder(String.valueOf(cnpj_calc))).append(dig != 10 && dig != 11 ? Integer.toString(dig) : "0").toString();
    	    
    	    // --- SEGUNDO DÍGITO VERIFICADOR ---
    	    soma = 0;
    	    for (int i = 0; i < 5; i++) {
    	        int valorCaractere = chr_cnpj[i] - 48;
    	        if ((valorCaractere >= 0 && valorCaractere <= 9) || (valorCaractere >= 17 && valorCaractere <= 42)) {
    	            soma += valorCaractere * (7 - (i + 1));
    	        } else {
    	            return false;
    	        }
    	    }

    	    for (int i = 0; i < 8; i++) {
    	        int valorCaractere = chr_cnpj[i + 5] - 48;
    	        if ((valorCaractere >= 0 && valorCaractere <= 9) || (valorCaractere >= 17 && valorCaractere <= 42)) {
    	            soma += valorCaractere * (10 - (i + 1));
    	        } else {
    	            return false;
    	        }
    	    }

    	    dig = 11 - soma % 11;
    	    cnpj_calc = (new StringBuilder(String.valueOf(cnpj_calc))).append(dig != 10 && dig != 11 ? Integer.toString(dig) : "0").toString();

    	    return cnpj.equals(cnpj_calc);
    	}

}
