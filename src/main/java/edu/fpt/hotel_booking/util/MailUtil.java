package edu.fpt.hotel_booking.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil {

    private static final String SENDER = "truong261120@gmail.com";
    private static final String PASSWORD = "school26810";
    private static final Session SESSION;

    static {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        SESSION = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER, PASSWORD);
            }
        });
    }

    public static void sendMail(String recipient, String content) throws MessagingException {
        Message message = prepareMessage(SESSION, recipient, content);
        Transport.send(message);
    }

    private static Message prepareMessage(Session session, String recipient, String content) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SENDER));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        message.setSubject("Confirm hotel booking action");
        message.setText(content);
        return message;
    }
}
