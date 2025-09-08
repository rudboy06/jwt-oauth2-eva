package com.example.eval.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class CryptoService {

    @Value("${crypto.default-key-base64}")
    private String defaultKeyBase64;

    @Value("${crypto.default-iv-base64}")
    private String defaultIvBase64;

    public String encrypt(String plainText, String keyBase64, String ivBase64) throws Exception {
        String useKey = (keyBase64 == null || keyBase64.isEmpty()) ? defaultKeyBase64 : keyBase64;
        String useIv  = (ivBase64 == null || ivBase64.isEmpty()) ? defaultIvBase64 : ivBase64;

        byte[] keyBytes = Base64.getDecoder().decode(useKey);
        byte[] ivBytes  = Base64.getDecoder().decode(useIv);

        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(encrypted);
    }
}
