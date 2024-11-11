/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:19 AM - 10/11/2024
 * User: lam-nguyen
 **/

package main.java.helper;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class FileChooseHelper {
   public static enum SelectFileMode{
       FILES_ONLY(0),
       DIRECTORIES_ONLY(1),
       FILES_AND_DIRECTORIES(2);

       private final int mode;
       SelectFileMode(int mode) {
        this.mode = mode;
       }

       public int getMode() {
           return mode;
       }
   }

    public static File selectFile(SelectFileMode selectFileMode) {
        JFileChooser fileChooser = new JFileChooser();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.setSize(screenSize.width - 200, screenSize.height - 100);
        fileChooser.setPreferredSize(screenSize);

        // Set the dialog to open in the file selection mode
        fileChooser.setFileSelectionMode(selectFileMode.getMode());

        // Open the dialog and store the result
        int result = fileChooser.showOpenDialog(null);

        // If the user selects a file
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(null, "Selected file: " + selectedFile.getAbsolutePath());
            return selectedFile;
        }

        return null;
    }
}
