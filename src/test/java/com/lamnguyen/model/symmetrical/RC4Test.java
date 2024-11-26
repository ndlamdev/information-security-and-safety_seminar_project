package com.lamnguyen.model.symmetrical;

import org.junit.jupiter.api.Test;

public class RC4Test {
    @Test
    void allTest() throws Exception {
        SymmetricalTest.allTest(ISymmetrical.Algorithms.RC4.name(), 256);
    }
}
