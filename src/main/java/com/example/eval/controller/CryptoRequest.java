package com.example.eval.controller;

// DTO para petición de cifrado
public class CryptoRequest {
    private String text;
    private String keyBase64;
    private String ivBase64;

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getKeyBase64() { return keyBase64; }
    public void setKeyBase64(String keyBase64) { this.keyBase64 = keyBase64; }

    public String getIvBase64() { return ivBase64; }
    public void setIvBase64(String ivBase64) { this.ivBase64 = ivBase64; }
}
