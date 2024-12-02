package org.example.passwordencryption;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Base64;

public class Encryptor {

    public static SecretKey decryptAesKey(byte[] encryptedKey, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedKey = cipher.doFinal(encryptedKey);
        return new SecretKeySpec(decryptedKey, 0, decryptedKey.length, "AES");
    }

    public static String decryptPassword(String encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes);
    }
    public static SecretKey decrypt(String key, KeyPair keyPairz)throws Exception{
        byte[] encryptedKey = Base64.getDecoder().decode(key);
        SecretKey decryptedKey = Encryptor.decryptAesKey(encryptedKey, keyPairz.getPrivate());
        return decryptedKey;
    }
}
