package org.project.final_backend.service.impl;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.final_backend.cache.OTP;
import org.project.final_backend.cache.OTPCache;
import org.project.final_backend.constant.EmailConstant;
import org.project.final_backend.domain.request.otp.NewOTPRequest;
import org.project.final_backend.domain.request.password.ResetPasswordOTPRequest;
import org.project.final_backend.domain.request.password.ResetPasswordRequest;
import org.project.final_backend.domain.request.password.VerifyMailRequest;
import org.project.final_backend.domain.request.user.NewUserRequest;
import org.project.final_backend.domain.request.user.ValidateUserRequest;
import org.project.final_backend.dto.model.UserInfo;
import org.project.final_backend.entity.Users;
import org.project.final_backend.exception.Error400Exception;
import org.project.final_backend.exception.InvalidPasswordException;
import org.project.final_backend.exception.UserFoundException;
import org.project.final_backend.exception.UserNotFoundException;
import org.project.final_backend.repo.UserRepo;
import org.project.final_backend.service.AuthService;
import org.project.final_backend.util.EmailUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private EmailUtil emailUtil;

    private final ModelMapper modelMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public boolean isOTPValid(ResetPasswordOTPRequest request) {
        final OTP otp = OTPCache.getOTP(request.getEmail());

        return otp != null && otp.getOtp().equals(request.getOtp()) && LocalDateTime.now().isBefore(otp.getExpiredTIme());
    }

    @Override
    public boolean registerUser(NewUserRequest request) {
        if (userRepo.findUsersByEmail(request.getEmail()).isPresent()) {
            throw new UserFoundException("User already exists!");
        } else if (request.getPassword().length() < 8) {
            throw new Error400Exception("Password length must be 8!");
        } else {
            Users user = Users.builder()
//                    .firstName(request.getFirstName())
//                    .lastName(request.getLastName())
                    .userName(request.getUserName())
                    .email(request.getEmail())
                    .bannerImg("https://images.pexels.com/photos/573130/pexels-photo-573130.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2")
                    .profileImg("https://static.vecteezy.com/system/resources/previews/008/442/086/non_2x/illustration-of-human-icon-user-symbol-icon-modern-design-on-blank-background-free-vector.jpg")
                    .password(bCryptPasswordEncoder.encode(request.getPassword()))
                    .createdDate(new Date())
                    .role("User")
                    .build();
            userRepo.save(user);

            return true;
        }
    }

    @Override
    public boolean verifyMail(VerifyMailRequest request) {
        userRepo.findUsersByEmail(request.getEmail()).orElseThrow(
                () -> new UserNotFoundException("User not found!")
        );
       return true;
    }

    @Override
    public boolean verifyOTPCode(ResetPasswordOTPRequest request) {
        userRepo.findUsersByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        validateOTPOrThrow(request);
        return true;
    }

    @Override
    public boolean resetPassword(ResetPasswordRequest request) {
        final Users user = userRepo.findUsersByEmail(request.getEmail()).orElseThrow(
                () -> new UserNotFoundException("User not found!")
        );
        
        if (!bCryptPasswordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid password!");
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(request.getNewPassword()));
            user.setUpdatedDate(new Date());
            userRepo.save(user);
            return true;
        }
    }

    @Override
    public boolean resetPasswordWithOTP(ResetPasswordOTPRequest request) {
        validateOTPOrThrow(request);

        Users user = userRepo.findUsersByEmail(request.getEmail()).orElseThrow(() -> new UserNotFoundException("User not found!"));

        if(user != null){
            user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
            user.setUpdatedDate(new Date());
            userRepo.save(user);

            return  true;
        }
        return false;
    }

    @Override
    public void generateOTP(NewOTPRequest request) {
        if(userRepo.findUsersByEmail(request.getEmail()).isPresent()) {
            Random random = new Random();
            final int min = 100000;
            final int max = 1000000;
            final int r = random.nextInt(min, max);

            send(request.getEmail(), emailUtil.generateOtpResetPasswordEmailBody(String.valueOf(r)));

            OTP otp = new OTP(request.getEmail(), Integer.toString(r), LocalDateTime.now().plusMinutes(10));
            OTPCache.saveOTP(otp);
        } else {
            throw  new RuntimeException("User not found.");
        }
    }

    @Override
    public void validateOTPOrThrow(ResetPasswordOTPRequest request) {
        if (!isOTPValid(request)) {
            throw new InvalidPasswordException("Invalid OTP!");
        }
    }

    @Override
    public UserInfo validateUser(ValidateUserRequest request) {
        final Users user = userRepo
                .findUsersByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User have not registered yet!"));
        if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid password!");
        }
        return modelMapper.map(user, UserInfo.class);
    }



    private void send(String toEmail, String body){
        final String fromEmail = EmailConstant.senderMail; //requires valid email
        final String password = EmailConstant.senderPassword;

        Properties props = getProperties();

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };

        Session session = Session.getInstance(props, auth);
        emailUtil.sendEmail(session, toEmail, "Password Reset OTP Code", body);
    }

    private static Properties getProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.host", EmailConstant.SMTP_HOST); //SMTP Host
        props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        props.put("mail.smtp.port", "465"); //SMTP Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
        props.put("mail.debug", "true");
        return props;
    }

}
