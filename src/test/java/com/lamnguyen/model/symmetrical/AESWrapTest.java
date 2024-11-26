package com.lamnguyen.model.symmetrical;

import org.junit.jupiter.api.Test;

public class AESWrapTest {
    @Test
    void allTest() throws Exception {
        SymmetricalTest.allTest(ISymmetrical.Algorithms.AESWrap.name(), 128);
    }
}
