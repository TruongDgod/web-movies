package com.bepay.application.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;

public class AESUtils {
    private static final byte[] iv = new byte[]{77, 64, 110, 72, 102, 103, -23, 67, 54, -128, 74, 57, 48, 84, 86, 51};
    private static final String ALGORITHM = "AES";
    private static Cipher a;
    private static Cipher b;
    private static final byte[] salt = new byte[]{100, 97, 50, 102, 55, 53, 54, 52, 56, 57, 49, 98, 99, 48, 101, 51};
    //private static final String key = "Mf9ifts@2021";

    public static String encrypt(String data, String key) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        return a(encrypt(data.getBytes(), key));
    }

    private static byte[] encrypt(byte[] data, String key) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec var1 = createKey(key);
        a.init(1, var1);
        return a.doFinal(data);
    }

    private static String a(byte[] var0) {
        StringBuilder var1 = new StringBuilder(2 * var0.length);

        for (int var2 = 0; var2 < var0.length; ++var2) {
            int var3 = var0[var2] & 255;
            var1.append((char) salt[var3 >> 4]);
            var1.append((char) salt[var3 & 15]);
        }

        return var1.toString();
    }

    private static SecretKeySpec createKey(String key) {

        byte[] var2 = AESUtils.iv.clone();
        if (!"".equals(key)) {
            int var3 = 0;
            char[] var4 = key.toCharArray();
            int var5 = var4.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                char var7 = var4[var6];
                var2[var3] = (byte) (AESUtils.iv[var3] + var7);
                ++var3;
                if (var3 == 16) {
                    var3 = 0;
                }
            }
        }

        return new SecretKeySpec(var2, AESUtils.ALGORITHM);
    }

    public static String decrypt(String data, String key) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        return new String(b(a(data), key));
    }

    private static byte[] a(String var0) {
        if (var0.length() % 2 != 0) {
            var0 = "0".concat(var0);
        }

        int var1 = var0.length() / 2;
        byte[] var2 = new byte[var1];

        for (int var3 = 0; var3 < var1; ++var3) {
            var2[var3] = (byte) (a(var0.charAt(var3 * 2)) << 4);
            var2[var3] = (byte) (var2[var3] | a(var0.charAt(var3 * 2 + 1)) & 15);
        }

        return var2;
    }

    private static int a(char var0) {
        boolean var1 = false;

        int var2;
        for (var2 = 0; var2 < salt.length && salt[var2] != (byte) var0; ++var2) {
        }

        return var2;
    }

    private static byte[] b(byte[] data, String key) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec var1 = createKey(key);
        b.init(2, var1);
        return b.doFinal(data);
    }

    static {
        try {
            a = Cipher.getInstance(ALGORITHM);
            b = Cipher.getInstance(ALGORITHM);
        } catch (Exception var1) {
            var1.printStackTrace();
        }
    }

    public static void main(String[] args) throws GeneralSecurityException {
        final String key = "Mf9ifts@2021";
        String data = "EMONEY PAYMENT SOLUATION PLC";
        String enc = encrypt(data, key);
        String dec = decrypt(enc, key);
        System.out.println("enc: " + enc);
        System.out.println("dec: " + dec);
    }
}
