package org.project.final_backend.cache;

import java.util.HashMap;
import java.util.Map;

public class OTPCache {
    private static final Map<String, OTP> otpMap = new HashMap<>();
    public static void saveOTP(OTP otp) {
        otpMap.put(otp.getEmail(), otp);
    }

    public static OTP getOTP(String email) {
        return otpMap.get(email);
    }
}
