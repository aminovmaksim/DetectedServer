package com.devian.detected.security;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class AES256 {

    private static final String key = "qMg88AeRKteP4H2NTzU9s3tTWafmcBZGs3CTE7rRUqJDBVQqFqrQ4aYLmx7YZfNN";

    public static String encrypt(String toEncrypt) {
        try {
            byte[] clean = toEncrypt.getBytes();

            // Generating IV.
            int ivSize = 16;
            byte[] iv = new byte[ivSize];
            SecureRandom random = new SecureRandom();
            random.nextBytes(iv);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            // Hashing key.
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(key.getBytes(StandardCharsets.UTF_8));
            byte[] keyBytes = new byte[16];
            System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

            // Encrypt.
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(clean);

            // Combine IV and encrypted part.
            byte[] encryptedIVAndText = new byte[ivSize + encrypted.length];
            System.arraycopy(iv, 0, encryptedIVAndText, 0, ivSize);
            System.arraycopy(encrypted, 0, encryptedIVAndText, ivSize, encrypted.length);

            return Base64.getEncoder().encodeToString(encryptedIVAndText);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String toDecrypt) {
        if (toDecrypt.equals(""))
            return "";
        try {
            byte[] encryptedIvTextBytes = Base64.getDecoder().decode(toDecrypt);

            int ivSize = 16;
            int keySize = 16;

            // Extract IV.
            byte[] iv = new byte[ivSize];
            System.arraycopy(encryptedIvTextBytes, 0, iv, 0, iv.length);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            // Extract encrypted part.
            int encryptedSize = encryptedIvTextBytes.length - ivSize;
            byte[] encryptedBytes = new byte[encryptedSize];
            System.arraycopy(encryptedIvTextBytes, ivSize, encryptedBytes, 0, encryptedSize);

            // Hash key.
            byte[] keyBytes = new byte[keySize];
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(key.getBytes());
            System.arraycopy(md.digest(), 0, keyBytes, 0, keyBytes.length);
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

            // Decrypt.
            Cipher cipherDecrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] decrypted = cipherDecrypt.doFinal(encryptedBytes);

            return new String(decrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}