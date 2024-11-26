package com.lamnguyen.model.symmetrical;

import org.junit.jupiter.api.Test;

public class RC2Test {
    @Test
    void allTest() throws Exception {
        SymmetricalTest.allTest(ISymmetrical.Algorithms.RC2.name(), 56);
    }
}
