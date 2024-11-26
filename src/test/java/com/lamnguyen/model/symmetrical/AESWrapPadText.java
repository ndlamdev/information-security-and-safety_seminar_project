package com.lamnguyen.model.symmetrical;

import org.junit.jupiter.api.Test;

public class AESWrapPadText {
    @Test
    void allTest() throws Exception {
        SymmetricalTest.allTest(ISymmetrical.Algorithms.AESWrapPad.name(), 128);
    }
}
