/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:11 AM - 01/11/2024
 * User: lam-nguyen
 **/

package main.java.config;

import main.java.security.traditionalCipher.ITraditionalCipher;

import java.util.*;

public class CharSetConfig {
    public static final String ENGLISH_CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456879"; //ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456879
    public static final String VIET_NAMES_CHAR =
            "aăâbcdđeêghiklmnoôơpqrstuưvxyáàảãạắằẳẵặấầẩẫậéèẻẽẹếềểễệíìỉĩịóòỏõọốồổỗộớờởỡợúùủũụứừửữựýỳỷỹỵAĂÂBCDĐEÊGHIKLMNOÔƠPQRSTUƯVXYÁÀẢÃẠẮẰẲẴẶẤẦẨẪẬÉÈẺẼẸẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌỐỒỔỖỘỚỜỞỠỢÚÙỦŨỤỨỪỬỮỰÝỲỶỸỴ0123456789";

    public static Map<Character, Integer> getMapChar(ITraditionalCipher.SecureLanguage lang, int shift) {
        Map<Character, Integer> charMap = new HashMap<>();
        switch (lang) {
            case EN:
                for (int index = 0; index < ENGLISH_CHAR.length(); index++) {
                    char c = ENGLISH_CHAR.charAt(index);
                    charMap.put(c, index + shift);
                }
                break;
            case VN:
                for (int index = 0; index < VIET_NAMES_CHAR.length(); index++) {
                    char c = VIET_NAMES_CHAR.charAt(index);
                    charMap.put(c, index + shift);
                }
                break;
        }
        return charMap;
    }

    public static Map<Character, Integer> getMapChar(ITraditionalCipher.SecureLanguage lang) {
        return getMapChar(lang, 0);
    }

    public static Character getCharacter(int code, Map<Character, Integer> mapChar) {
        return mapChar.entrySet().stream().filter(entry -> entry.getValue() == code).map(Map.Entry::getKey).findFirst().orElse(null);
    }

    public static String decodeArrayCharEncodeToString(int[] arrayCharEncode, Map<Character, Integer> mapChar, Map<Integer, Character> mapCharNotEncrypt, int length) {
        StringBuilder stringBuilder = new StringBuilder();
        var indexCharEncode = 0;
        for (int index = 0; index < length; index++) {
            Character c = mapCharNotEncrypt.get(index);
            if (c != null) {
                stringBuilder.append(c);
                continue;
            }
            int encode = arrayCharEncode[indexCharEncode++];
            c = getCharacter(encode, mapChar);
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static int[] encodeStringToArrayCharEncode(String data, Map<Character, Integer> mapChar, Map<Integer, Character> mapCharNotEncrypt) {
        int totalChar = data.length();
        char[] arrayChar = data.toCharArray();
        List<Integer> listCharEncode = new ArrayList<>();
        for (int index = 0; index < totalChar; index++) {
            char c = arrayChar[index];
            int encode = mapChar.getOrDefault(c, -1);
            if (encode == -1) {
                mapCharNotEncrypt.put(index, arrayChar[index]);
                continue;
            }

            listCharEncode.add(encode);
        }
        return listCharEncode.stream().mapToInt(i -> i).toArray();
    }
}

