/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:35 AM - 08/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.ui.config;

import lombok.Getter;

import java.awt.*;
import java.io.File;
import java.io.IOException;

@Getter
public class FontConfig {
    private Font fontImperialScript;

    private static FontConfig instance;

    private FontConfig() {
        try {
            this.init();
        } catch (IOException | FontFormatException e) {
            e.printStackTrace(System.out);
        }
    }

    public static FontConfig getInstance() {
        if (instance == null) instance = new FontConfig();
        return instance;
    }

    public void init() throws IOException, FontFormatException {
        fontImperialScript = Font.createFont(Font.TRUETYPE_FONT, FontConfig.class.getClassLoader().getResourceAsStream("ImperialScript_Regular.ttf"));
    }

}
