/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:58 AM - 21/11/2024
 * User: kimin
 **/

package com.lamnguyen.helper;

import javax.swing.*;
import java.awt.*;

public class DialogProgressHelper {
    public static void runProcess(String title, String content, Process process) {
        // Hiển thị hộp thoại trong luồng riêng
        new Thread(() -> {
            var dialog = new JDialog((Frame) null, title, true);
            // Tùy chỉnh giao diện hộp thoại
            dialog.setLayout(new BorderLayout());
            dialog.setSize(300, 100);
            dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            dialog.setLocationRelativeTo(null);

            // Tạo thanh tiến trình
            JProgressBar progressBar = new JProgressBar();
            progressBar.setIndeterminate(true); // Chế độ tiến trình không xác định
            progressBar.setString(content);
            progressBar.setStringPainted(true);

            // Thêm thanh tiến trình vào hộp thoại
            dialog.add(progressBar, BorderLayout.CENTER);

            new Thread(() -> {
                SwingUtilities.invokeLater(() -> {
                    dialog.setVisible(true);
                });
            }).start();
            process.run(new DialogProcess(dialog));
        }).start();

    }

    public static void runProcess(Process process) {
        runProcess("Đang xử lý", "Đang xử lý, vui lòng chờ...", process);
    }

    public interface Process {
        void run(DialogProcess dialog);
    }

    public static class DialogProcess {
        private final JDialog dialog;

        public DialogProcess(JDialog dialog) {
            this.dialog = dialog;
        }

        public void dispose() {
            dialog.dispose();
        }
    }
}
