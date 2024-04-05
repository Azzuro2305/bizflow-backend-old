package org.project.final_backend.service.impl;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import lombok.AllArgsConstructor;
import org.project.final_backend.cache.OTP;
import org.project.final_backend.cache.OTPCache;
import org.project.final_backend.constant.EmailConstant;
import org.project.final_backend.domain.request.otp.NewOTPRequest;
import org.project.final_backend.domain.request.password.ResetPasswordOTPRequest;
import org.project.final_backend.entity.Users;
import org.project.final_backend.repo.UserRepo;
import org.project.final_backend.service.AuthService;
import org.project.final_backend.util.EmailUtil;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepo userRepo;
    private EmailUtil emailUtil;
    @Override
    public void generateOTP(NewOTPRequest request) {
        final Optional<Users> user = userRepo.findUsersByMail(request.getMail());
        if(user.isPresent()) {
            Random random = new Random();
            final int min = 100000;
            final int max = 1000000;
            final int r = random.nextInt(min, max);
            System.out.println("Generating Secret Key");
            System.out.println(r);
            send(request.getMail(), String.valueOf(r));

            OTP otp = new OTP(request.getMail(), Integer.toString(r), LocalDateTime.now().plusMinutes(1));
            OTPCache.saveOTP(otp);
        } else {
            throw  new RuntimeException("User not found.");
        }
    }

    @Override
    public boolean validateOTP(ResetPasswordOTPRequest request) {
        boolean isValidate = false;
        final OTP otp = OTPCache.getOTP(request.getMail());
        // validate otp correct
        if (otp != null) {
            if(otp.getOtp().equals(request.getOtp())) {
                if(LocalDateTime.now().isBefore(otp.getExpiredTIme())) {
                    // validate expired time
                    isValidate = true;
                }
            }
        }
        return isValidate;
    }

    private void send(String toEmail, String subject){
        final String fromEmail = EmailConstant.senderMail; //requires valid email
        final String password = EmailConstant.senderPassword;

        Properties props = new Properties();
        props.put("mail.smtp.host", EmailConstant.SMTP_HOST); //SMTP Host
        props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        props.put("mail.smtp.port", "465"); //SMTP Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
        props.put("mail.debug", "true");

        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };

        Session session = Session.getInstance(props, auth);
        emailUtil.sendEmail(session, toEmail, subject);
    }
}
