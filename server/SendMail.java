
package server;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
/**
 * Implementa o envio de um email.
 * @author lpro1612
 */

public class SendMail {
    /**
     * Envia um email para um determinado email com uma determinada mensagem.
     * @param To Destinatário do email a enviar.
     * @param msg Mensagem que se pretende enviar.
     * @return Uma string com "Email sent!" em caso de sucesso; "Error: " em caso de erro.
     */
    public String send(String To, String msg){
        Properties prop=new Properties();
        
        prop.put("mail.smtp.host","smtp.gmail.com");
        prop.put("mail.smtp.port","587");
        prop.put("mail.smtp.auth","true");
        prop.put("mail.smtp.starttls.enable","true");
        
        Session session = Session.getDefaultInstance(prop, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
               return new PasswordAuthentication("baship.service@gmail.com","J!2391hel");
            }
        });
        
        try{
            Message message=new MimeMessage(session);
            
            message.setFrom(new InternetAddress("baship.service@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(To));
            message.setSubject("Password Recovery");
            message.setText(msg);
            
            Transport.send(message);
            
            return "Email sent!";
        }catch(MessagingException me){
            
            return "Error: "+me.getMessage();
        }
       
    }
    
}
