package com.example.webservice.data;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Hashash {
    public static String getSHA512(String input){

        String toReturn = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(input.getBytes("utf8"));
            toReturn = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return toReturn;
    }
    public static String hundredTime(String salt, String pw){
        String sha512 = getSHA512(salt + pw);
        for (int i=0; i<100; i++){
            sha512 = getSHA512(sha512);
        }
        return  sha512;
    }
}
