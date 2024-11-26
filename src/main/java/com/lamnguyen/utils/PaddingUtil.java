/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:55 AM - 25/11/2024
 * User: kimin
 **/

package com.lamnguyen.utils;

import java.util.Arrays;

public class PaddingUtil {
    public static byte[] addPadding(int blockSize, byte[] data) {
        int paddingSize = blockSize - (data.length % blockSize);
        byte[] paddedData = new byte[data.length + paddingSize];
        System.arraycopy(data, 0, paddedData, 0, data.length); // Sao chép dữ liệu gốc
        // Mặc định các byte thêm vào đã là 0
        return paddedData;
    }

    public static byte[] removePadding(byte[] data) {
        int i = data.length - 1;
        while (i >= 0 && data[i] == 0) i--; // Tìm byte cuối cùng không phải là 0
        return Arrays.copyOf(data, i + 1);  // Cắt dữ liệu đến vị trí byte cuối cùng
    }
}
