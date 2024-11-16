/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:35 AM - 08/11/2024
 * User: lam-nguyen
 **/

package main.java.ui.config;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class FontConfig {
    private Font fontImperialScript;

    private static FontConfig instance;

    private FontConfig() {
        try {
            this.init();
        } catch (IOException | FontFormatException e) {
            System.out.println(e);
        }
    }

    public static FontConfig getInstance() {
        if (instance == null) instance = new FontConfig();
        return instance;
    }

    public void init() throws IOException, FontFormatException {
        URL fontImperialScriptURL = getClass().getClassLoader().getResource("font/Imperial_Script/ImperialScript-Regular.ttf");
        fontImperialScript = Font.createFont(Font.TRUETYPE_FONT, new File(fontImperialScriptURL.getFile()));
    }

    public Font getFontImperialScript() {
        return fontImperialScript;
    }
}
