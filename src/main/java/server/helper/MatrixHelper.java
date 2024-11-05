/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:10 AM - 01/11/2024
 * User: lam-nguyen
 **/

package main.java.server.helper;

import main.java.server.security.traditionalCipher.ITraditionalCipher;

import java.util.Arrays;

public class MatrixHelper {
    public static int detMatrix(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        if (row == 2) return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];

        int det = 0;
        for (int indexCol = 0; indexCol < col; indexCol++)
            det += matrix[0][indexCol] * detMatrix(downDimensionByCell(0, indexCol, matrix)) * (int) Math.pow(-1.0, indexCol);
        return det;
    }

    public static int[][] adjugateMatrix(int[][] matrix, int modulo) {
        var result = new int[matrix.length][matrix[0].length];
        for (var indexRow = 0; indexRow < matrix.length; indexRow++) {
            for (var indexCol = 0; indexCol < matrix[0].length; indexCol++) {
                var temp = matrix.length == 2
                        ? downDimensionByCell(indexRow, indexCol, matrix)[0][0] * (int) Math.pow(-1, indexCol + indexRow)
                        : adjugateMatrixHelper(indexRow, indexCol, matrix);
                result[indexRow][indexCol] = Math.floorMod(temp, modulo);
            }
        }
        return result;
    }

    public static int[][] inverseMatrix(int[][] matrix, int modulo) throws Exception {
        int[][] tMatrix = tMatrix(matrix);
        int x = 1;
        int det = detMatrix(matrix);
        if (ITraditionalCipher.gcd(MatrixHelper.detMatrix(matrix), modulo) != 1)
            throw new Exception("Not exist inverse matrix");
        while (Math.floorMod(det * x, modulo) != 1) x++;

        return Arrays.stream(mulNumberWithMatrix(x, adjugateMatrix(tMatrix, modulo))).map(row -> Arrays.stream(row).map(value -> Math.floorMod(value, modulo)).toArray()).toArray(int[][]::new);
    }

    public static int[][] tMatrix(int[][] matrix) {
        int[][] result = Arrays.stream(matrix).map(int[]::clone).toArray(int[][]::new);
        for (int row = 0; row < matrix.length; row++) {
            for (int col = row + 1; col < matrix[row].length; col++) {
                result[row][col] = matrix[col][row];
                result[col][row] = matrix[row][col];
            }
        }
        return result;
    }

    public static int adjugateMatrixHelper(int row, int col, int[][] matrix) {
        if (matrix.length == 2)
            return detMatrix(matrix) * (int) Math.pow(-1, row + col);
        return adjugateMatrixHelper(row, col, downDimensionByCell(row, col, matrix));
    }

    public static int[][] downDimensionByCell(int row, int col, int[][] matrix) {
        int[][] result = new int[matrix.length - 1][matrix[0].length - 1];

        for (int indexRow = 0; indexRow < matrix.length; indexRow++) {
            if (indexRow == row) continue;

            int[] rowData = new int[matrix[0].length - 1];
            System.arraycopy(matrix[indexRow], 0, rowData, 0, col);
            System.arraycopy(matrix[indexRow], col + 1, rowData, col, matrix[0].length - 1 - col);
            result[indexRow < row ? indexRow : indexRow - 1] = rowData;
        }
        return result;
    }

    public static int[] mulArrayWithMatrixAndModulo(int[] array, int[][] matrix, int modulo) throws Exception {
        if (matrix.length != array.length) throw new Exception("Error length array not equals total row in matrix");
        int[] result = new int[matrix.length];
        for (int col = 0; col < array.length; col++) {
            int temp = 0;

            for (int row = 0; row < array.length; row++)
                temp += (array[row] * matrix[row][col]);

            result[col] = Math.floorMod(temp, modulo);
        }

        return result;
    }

    public static int[][] mulNumberWithMatrix(int value, int[][] array) {
        return Arrays.stream(array).map(row -> Arrays.stream(row).map(it -> it * value).toArray()).toArray(int[][]::new);
    }
}

