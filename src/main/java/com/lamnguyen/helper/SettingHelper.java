/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:36 PM - 14/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.helper;

import java.util.prefs.Preferences;

public class SettingHelper {
    private Preferences prefs;
    private static SettingHelper instance;
    final String PREF_WORK_SPACE = "work_space";

    private SettingHelper() {
        prefs = Preferences.userNodeForPackage(SettingHelper.class);
    }

    public static SettingHelper getInstance() {
        if (instance == null) instance = new SettingHelper();

        return instance;
    }

    public String getWorkSpace() {
        return prefs.get(PREF_WORK_SPACE, "");
    }

    public void setWorkSpace(String workSpace) {
        prefs.put(PREF_WORK_SPACE, workSpace);
    }
}
