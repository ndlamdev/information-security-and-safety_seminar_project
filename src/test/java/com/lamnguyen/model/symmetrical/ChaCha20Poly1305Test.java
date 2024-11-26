package com.lamnguyen.model.symmetrical;

import org.junit.jupiter.api.Test;

public class ChaCha20Poly1305Test {
    @Test
    void allTest() throws Exception {
        SymmetricalTest.allTest(ISymmetrical.Algorithms.ChaCha20Poly1305.name(), 256);
    }
}
