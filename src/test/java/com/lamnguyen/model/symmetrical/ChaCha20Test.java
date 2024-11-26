package com.lamnguyen.model.symmetrical;

import org.junit.jupiter.api.Test;

public class ChaCha20Test {
    @Test
    void allTest() throws Exception {
        SymmetricalTest.allTest(ISymmetrical.Algorithms.ChaCha20.name(), 256);
    }
}
