package org.project.final_backend.cache;

import java.util.HashMap;
import java.util.Map;

public class OTPCache {
    private static Map<String, OTP> otpMap = new HashMap<>();
    public static void saveOTP(OTP otp) {
        otpMap.put(otp.getMail(), otp);
    }

    public static OTP getOTP(String mail) {
        return otpMap.get(mail);
    }
}
