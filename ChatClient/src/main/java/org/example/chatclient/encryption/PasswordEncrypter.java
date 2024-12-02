package org.example.chatclient.encryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class PasswordEncrypter {
    private static SecretKey aesKey;

    public static String encryptKey(String key) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(key);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(decodedKey));

        aesKey = generateAesKey();

        byte[] encryptedAES = encryptAesKey(aesKey, publicKey);
        return Base64.getEncoder().encodeToString(encryptedAES);
    }


    private static SecretKey generateAesKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        return keyGen.generateKey();
    }

    private static byte[] encryptAesKey(SecretKey aesKey, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(aesKey.getEncoded());
    }

    public static String encryptPassword(String password) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] encryptedBytes = cipher.doFinal(password.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

}
