package com.ticbus.backend.util;

public class PhoneUtils {
    private static PhoneUtils instance;

    static {
        try {
            instance = new PhoneUtils();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating singleton instance");
        }
    }

    private PhoneUtils() {
    }
    public static PhoneUtils getInstance() {
        return instance;
    }
    public boolean isValidPhone(String phone) {
        return phone.matches(
            "^\\s*(?:\\+?(\\d{1,3}))?([-. (]*(\\d{3})[-. )]*)?((\\d{3})[-. ]*(\\d{2,4})(?:[-.x ]*(\\d+))?)\\s*$");
    }
}
