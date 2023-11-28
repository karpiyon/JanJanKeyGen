package com.jdj.pluginkeygen;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Licensing {

    public static String GenerateLicenseKey(String id) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return FormatCode(GetMd5Sum(id));
    }

    private static String GetMd5Sum(String productIdentifier) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] bytesOfMessage = productIdentifier.getBytes("UTF-16LE");
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(bytesOfMessage);
        BigInteger i = new BigInteger(1, digest);
        return String.format("%1$032X", i);
    }

    public static String FormatCode(String productIdentifier) {
        int keyLength = productIdentifier.length();
        productIdentifier = productIdentifier.substring(0, keyLength).toUpperCase();
        char[] serialArray = productIdentifier.toCharArray();
        StringBuilder licenseKey = new StringBuilder();

        for (int i = 0; i < keyLength; i++) {
            int j = 0;
            for (j = i; j < 4 + i; j++) {
                licenseKey.append(serialArray[j]);
            }
            if (j == keyLength) {
                break;
            } else {
                i = j - 1;
                licenseKey.append("-");
            }
        }
        return licenseKey.toString();
    }
}
