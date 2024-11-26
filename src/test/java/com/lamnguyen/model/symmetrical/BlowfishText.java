package com.lamnguyen.model.symmetrical;

import org.junit.jupiter.api.Test;

public class BlowfishText {
    @Test
    void allTest() throws Exception {
        SymmetricalTest.allTest(ISymmetrical.Algorithms.Blowfish.name(), 128);
    }
}
