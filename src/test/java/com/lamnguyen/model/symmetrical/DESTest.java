/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:32 AM - 05/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.model.symmetrical;

import org.junit.jupiter.api.Test;

public class DESTest {
    @Test
    void allTest() throws Exception {
        SymmetricalTest.allTest(ISymmetrical.Algorithms.DES.name(), 128);
    }
}
