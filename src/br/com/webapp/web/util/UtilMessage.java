package br.com.webapp.web.util;

import java.util.ResourceBundle;

public class UtilMessage {
	
	private static ResourceBundle mbundle = ResourceBundle.getBundle("br.com.webapp.web.Messages");
	public static RNException exceptionMensagem(String messages, Throwable throwable){
		try {
			return new RNException(mbundle.getString(messages), throwable);
		} catch (Exception e) {
			return new RNException(mbundle.getString("msg.resources.erro_mensagem_nao_encontrada"));
		}
		
	}
	
	public static String mensagem(String messages){
		try {
			return mbundle.getString(messages);
		} catch (Exception e) {
			return mbundle.getString("msg.resources.erro_mensagem_nao_encontrada");
		}
		
	}

}