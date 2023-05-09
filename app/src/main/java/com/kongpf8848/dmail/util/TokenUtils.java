package com.kongpf8848.dmail.util;

import android.util.Base64;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class TokenUtils {

    public static String getEmailFromIdToken(String idToken) {
        try {
            String[] split = idToken.split("\\.");
            String jwtBody = split[1];
            byte[] decodedBodyBytes = Base64.decode(jwtBody, Base64.URL_SAFE);
            String body = new String(decodedBodyBytes, StandardCharsets.UTF_8);
            JSONObject jsonObject=new JSONObject(body);
            return jsonObject.optString("email");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
