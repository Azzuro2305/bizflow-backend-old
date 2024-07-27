package org.project.final_backend.util;

import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
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
    public void sendEmail(Session session, String toEmail, String subject, String body){
        try{
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(EmailConstant.senderMail, "NoReply-JD"));

            msg.setReplyTo(InternetAddress.parse(EmailConstant.senderMail, false));

            msg.setSubject(subject, "UTF-8");
            msg.setContent(body, "text/html; charset=UTF-8");
            msg.setSentDate(new java.util.Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            Transport.send(msg);

//            MimeMessageHelper helper = new MimeMessageHelper(msg, "UTF-8");
//            helper.setFrom(EmailConstant.senderMail);
//            helper.setTo(toEmail);
//            msg.setText(subject);
//            helper.setSubject(subject);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String generateOtpEmailBody(String otp) {
        return "<html>" +
                "<body>" +
                "<h1>Your OTP Code</h1>" +
                "<p>Dear User,</p>" +
                "<p>Thank you for using our service. Your One-Time Password (OTP) is:</p>" +
                "<h2 style='color: blue;'>" + otp + "</h2>" +
                "<p>Please use this OTP to complete your verification process. This OTP is valid for 10 minutes.</p>" +
                "<p>If you did not request this code, please ignore this email.</p>" +
                "<br>" +
                "<p>Best regards,</p>" +
                "<p>Biz Connect</p>" +
                "</body>" +
                "</html>";
    }

    public String generateOtpResetPasswordEmailBody(String otp) {
        return "<html>" +
                "<body>" +
                "<h1>Your Reset Password OTP Code</h1>" +
                "<p>Dear User,</p>" +
                "<p>Thank you for using our service. Your One-Time Password (OTP) is:</p>" +
                "<h2 style='color: blue;'>" + otp + "</h2>" +
                "<p>This OTP is valid for 2 minutes.</p>" +
                "<p>If you did not request this code, please ignore this email.</p>" +
                "<br>" +
                "<p>Best regards,</p>" +
                "<p>Biz Connect</p>" +
                "</body>" +
                "</html>";
    }

}