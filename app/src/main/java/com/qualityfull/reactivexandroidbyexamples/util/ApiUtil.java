package com.qualityfull.reactivexandroidbyexamples.util;

import android.text.TextUtils;

import com.auth0.android.jwt.JWT;
import com.qualityfull.reactivexandroidbyexamples.core.Constants;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class ApiUtil {

    public static String convertPassMd5(String pass) {
        String password = null;
        MessageDigest mdEnc;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(pass.getBytes(), 0, pass.length());
            pass = new BigInteger(1, mdEnc.digest()).toString(16);
            while (pass.length() < 32) {
                pass = "0" + pass;
            }
            password = pass;
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return password;
    }

    public static boolean isWithQuery(String query) {
        return query != null && !TextUtils.isEmpty(query);
    }

    public static boolean isTokenValid(String token){
        return token != null && !token.isEmpty() && !(new JWT(token).isExpired(10));
    }
}
