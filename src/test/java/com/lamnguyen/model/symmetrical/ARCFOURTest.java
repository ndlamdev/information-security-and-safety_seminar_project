package com.lamnguyen.model.symmetrical;

import org.junit.jupiter.api.Test;

public class ARCFOURTest {
    @Test
    void allTest() throws Exception {
        SymmetricalTest.allTest(ISymmetrical.Algorithms.ARCFOUR.name(), 128);
    }
}
