package com.lamnguyen.model.symmetrical;

import lombok.Getter;

import javax.crypto.spec.IvParameterSpec;
import java.security.spec.AlgorithmParameterSpec;

@Getter
public class ChaCha20Poly1305Spec implements AlgorithmParameterSpec {
    private String AAD;
    private IvParameterSpec iv;

    public ChaCha20Poly1305Spec(String AAD, IvParameterSpec iv) {
        this.AAD = AAD;
        this.iv = iv;
    }

    public ChaCha20Poly1305Spec(String AAD, byte[] ivBytes) {
        this.AAD = AAD;
        this.iv = new IvParameterSpec(ivBytes);
    }
}