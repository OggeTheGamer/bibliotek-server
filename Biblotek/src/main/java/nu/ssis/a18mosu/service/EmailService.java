package nu.ssis.a18mosu.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import nu.ssis.a18mosu.model.Loan;

@Component
public class EmailService {

	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);

		mailSender.setUsername("movitz.sunar@gmail.com");
		mailSender.setPassword("");

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		return mailSender;
	}

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private TemplateEngine templateEngine;

	public void sendThanksMail(Loan loan) throws MessagingException {
	    Session session = Session.getDefaultInstance(new Properties()); //XXX vilka är dessa properties och session? it works
		MimeMessage m = new MimeMessage(session);
		m.setSubject("Trevlig läsning!");
		m.addRecipient(Message.RecipientType.TO, new InternetAddress(loan.getLoanTaker().getEmailAdress()));
		
		Context context = new Context();
		context.setVariable("loan", loan);
		String messageText =  templateEngine.process("emails/thanks.html", context);
		
		m.setText(messageText);
		m.setContent(messageText, "text/html");
		emailSender.send(m);
	}
	
	public void sendReturnMail(Loan loan) throws MessagingException {
	    Session session = Session.getDefaultInstance(new Properties());
		MimeMessage m = new MimeMessage(session);
		m.setSubject("Dags att lämna tillbaka din bok!");
		m.addRecipient(Message.RecipientType.TO, new InternetAddress(loan.getLoanTaker().getEmailAdress()));
		
		Context context = new Context();
		context.setVariable("loan", loan);
		String messageText =  templateEngine.process("emails/return.html", context);
		
		m.setText(messageText);
		m.setContent(messageText, "text/html");
		emailSender.send(m);
	}

}
