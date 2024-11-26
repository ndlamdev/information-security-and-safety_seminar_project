/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:06 AM - 08/11/2024
 * User: lam-nguyen
 **/

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.util.Arrays;

public class AESExampleNoPadding {
    public static void main(String[] args) throws Exception {
        // Tạo khóa AES và IV
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128); // Khóa 128-bit
        SecretKey key = keyGen.generateKey();
        IvParameterSpec ivSpec = new IvParameterSpec(new byte[16]); // IV 16 byte

        // Dữ liệu đầu vào
        byte[] inputData = "Hello, AES!".getBytes(); // Dữ liệu không đủ bội số của 16
        System.out.println("Original Data: " + new String(inputData));

        // Thêm padding để dữ liệu là bội số của 16 byte
        byte[] paddedInput = addPadding(inputData);
        System.out.println("Padded Data: " + Arrays.toString(paddedInput));

        // Mã hóa với AES/CBC/NoPadding
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] encryptedData = cipher.doFinal(paddedInput);
        System.out.println("Encrypted Data: " + Arrays.toString(encryptedData));

        // Giải mã với AES/CBC/NoPadding
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        byte[] decryptedData = cipher.doFinal(encryptedData);

        // Loại bỏ padding để khôi phục dữ liệu gốc
        byte[] originalData = removePadding(decryptedData);
        System.out.println("Decrypted Data: " + new String(originalData));
    }

    // Hàm thêm padding (Padding kiểu Zero Padding)
    private static byte[] addPadding(byte[] data) {
        int blockSize = 16; // Kích thước block của AES
        int paddingSize = blockSize - (data.length % blockSize);
        byte[] paddedData = new byte[data.length + paddingSize];
        System.arraycopy(data, 0, paddedData, 0, data.length); // Sao chép dữ liệu gốc
        // Mặc định các byte thêm vào đã là 0
        return paddedData;
    }

    // Hàm loại bỏ padding (Chỉ dành cho Zero Padding)
    private static byte[] removePadding(byte[] data) {
        int i = data.length - 1;
        while (i >= 0 && data[i] == 0) i--; // Tìm byte cuối cùng không phải là 0
        return Arrays.copyOf(data, i + 1);  // Cắt dữ liệu đến vị trí byte cuối cùng
    }
}
