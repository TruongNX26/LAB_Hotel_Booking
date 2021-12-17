package edu.fpt.hotel_booking.util;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class MyUtil {

    public static String encode(String originalString) {
        return Hashing.sha256()
                .hashString(originalString, StandardCharsets.UTF_8)
                .toString();
    }
}
