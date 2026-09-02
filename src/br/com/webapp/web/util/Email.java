package br.com.webapp.web.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {  

	public static void ConfiguraEmail(String emissor, String recebedor, String assunto, String corpo) throws Exception {  

		// seta o servidor de email  
		Properties props = new Properties();  
		props.put("mail.smtp.host", "mail.corptech.com.br");  
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "25");

		// cria uma sessao com o servidor de emaisl  
		Session mailSession = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("triviacloud+corptech.com.br", "TechCorp0710");
			}
		});  

		//Mostra detalhes do envio da mensagem, quando (true)  
		mailSession.setDebug(true);   
		Message msg = new MimeMessage(mailSession);

		//Subject = ASSUNTO
		msg.setSubject(assunto);

		// FROM = de esta enviando //  
		InternetAddress from = new InternetAddress(emissor);
		msg.setFrom(from);

		//PARA QUEM recebe //  
		InternetAddress[] address = {new InternetAddress(recebedor)};
		msg.setRecipients(Message.RecipientType.TO, address);

		// conteudo  
		msg.setHeader("Content-Type", "text/html; charset=\"iso-8859-1\"");
		msg.setContent(corpo, "text/html; charset=iso-8859-1");
		msg.setHeader("Content-Transfer-Encoding", "quoted-printable");

		//Executa finalmente o envio!   
		Transport.send(msg);
	}  
} 