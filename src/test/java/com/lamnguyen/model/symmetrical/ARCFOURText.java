package com.lamnguyen.model.symmetrical;

import org.junit.jupiter.api.Test;

public class ARCFOURText {
    @Test
    void allTest() throws Exception {
        SymmetricalTest.allTest(ISymmetrical.Algorithms.ARCFOUR.name(), 128);
    }
}
