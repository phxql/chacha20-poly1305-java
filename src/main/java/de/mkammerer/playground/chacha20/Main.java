package de.mkammerer.playground.chacha20;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import static de.mkammerer.playground.chacha20.Util.bytesToHex;

public class Main {
    private static final SecureRandom RANDOM = new SecureRandom();

    public static void main(String[] args) throws Exception {
        Cipher cipher = Cipher.getInstance("ChaCha20-Poly1305");

        byte[] key = generateKey();
        System.out.println("Key: " + bytesToHex(key));

        byte[] nonce = generateNonce();
        System.out.println("Nonce: " + bytesToHex(nonce));

        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "ChaCha20"), new IvParameterSpec(nonce));

        String plaintext = "Hello, Chacha20";
        System.out.println("Plaintext: " + plaintext);

        byte[] ciphertext = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        System.out.println("Ciphertext: " + bytesToHex(ciphertext));

        // Uncomment this to check if AEAD detects tampering
        // tamperCiphertext(ciphertext);

        cipher = Cipher.getInstance("ChaCha20-Poly1305");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "ChaCha20"), new IvParameterSpec(nonce));

        String decryptedPlaintext = new String(cipher.doFinal(ciphertext), StandardCharsets.UTF_8);
        System.out.println("Decrypted plaintext: " + decryptedPlaintext);
    }

    private static byte[] generateKey() {
        // Key is 256 bits in length (32 bytes)
        // See https://openjdk.java.net/jeps/329
        byte[] key = new byte[32];
        RANDOM.nextBytes(key);
        return key;
    }

    private static byte[] generateNonce() {
        // The nonce value must be 96 bits in length (12 bytes).
        // See https://openjdk.java.net/jeps/329
        byte[] nonce = new byte[12];
        RANDOM.nextBytes(nonce);
        return nonce;
    }

    private static void tamperCiphertext(byte[] ciphertext) {
        ciphertext[0] = (byte) (ciphertext[0] + 1);
    }
}
