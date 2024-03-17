package org.project.final_backend.util;

import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.project.final_backend.constant.EmailConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.MimeMessageHelper;

@Configuration
@AllArgsConstructor
public class EmailUtil {

    /**

     Utility method to send simple HTML email
     @param session
     @param toEmail
     @param subject
     */
    public void sendEmail(Session session, String toEmail, String subject){
        try{
            MimeMessage msg = new MimeMessage(session);

            MimeMessageHelper helper = new MimeMessageHelper(msg, "UTF-8");
            helper.setFrom(EmailConstant.senderMail);
            helper.setTo(toEmail);
            msg.setText(subject);
            helper.setSubject(subject);

            Transport.send(msg);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}