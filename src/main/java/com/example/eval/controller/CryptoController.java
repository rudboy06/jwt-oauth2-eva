package com.example.eval.controller;

import com.example.eval.service.CryptoService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/crypto")
public class CryptoController {

    private final CryptoService cryptoService;

    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    // POST /api/crypto/encrypt
    @PostMapping("/encrypt")
    public Map<String, String> encrypt(@RequestBody CryptoRequest request) throws Exception {
        String cipherB64 = cryptoService.encrypt(request.getText(), request.getKeyBase64(), request.getIvBase64());
        Map<String, String> resp = new HashMap<>();
        resp.put("cipherBase64", cipherB64);
        return resp;
    }
}
