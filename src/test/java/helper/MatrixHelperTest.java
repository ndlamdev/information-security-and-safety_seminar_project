/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:46 AM - 02/11/2024
 * User: lam-nguyen
 **/

package test.java.helper;

import main.java.server.helper.MatrixHelper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class MatrixHelperTest {
    @Test
    public void mulTest() throws Exception {
        var a = new int[]{3, 7};
        var key = new int[][]{{11, 8}, {3, 7}};
        System.out.println(Arrays.toString(MatrixHelper.mulArrayWithMatrixAndModulo(a, key, 26)));
    }

    @Test
    public void downDimensionByCellTest() {
        var key = new int[][]{{11, 8}, {3, 7}};
        System.out.println(Arrays.deepToString(MatrixHelper.downDimensionByCell(0, 0, key)));
    }

    @Test
    public void inverseMatrixTest() throws Exception {
        var key = new int[][]{{6, 24, 1}, {13, 16, 10}, {20, 17, 15}};
        System.out.println(Arrays.deepToString(MatrixHelper.inverseMatrix(key, 26)));
    }
}
