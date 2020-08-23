package id.privy.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {

    public static String textEncrypt(String text) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(text.getBytes(StandardCharsets.UTF_8));
        byte[] bytes = digest.digest();
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < bytes.length; x++) {
            sb.append(Integer.toString((bytes[x] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
