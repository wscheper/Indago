package connectivity;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import models.User;

/**
 *
 * @author wesley
 * 
 * Sending emails through the javax mail api
 */
public class Mail {
    
    private final String FROM = "FROM";
    private final String HOST = "HOST";
    private final String PORT = "PORT";
    private final String USERNAME = "USERNAME";
    private final String PASSWORD = "PASSWORD";
    private User user;
    
    private Properties properties;
    private Session session;
    
    public Mail(User user){
        this.user = user;
        
        properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", HOST);
        properties.put("mail.smtp.port", PORT);
        session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }
                });
    }
    
    public void sendMail(String subject, String body){
        try {
            // compose message
            MimeMessage message = new MimeMessage(session);
            
            message.setFrom(new InternetAddress(FROM));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmailAddress()));
            message.setSubject(subject);
            message.setText(body); // body of the mail message
            
            // send message
            Transport.send(message);
        } catch (MessagingException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}