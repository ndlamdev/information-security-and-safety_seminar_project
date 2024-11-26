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
    private static boolean[] running = new boolean[1];

    public static void runProcess(String title, String content, Process process) {
        if (running[0]) return;
        running[0] = true;
        // Hiển thị hộp thoại trong luồng riêng
        new Thread(() -> {
            var dialogProcess = new DialogProcess(title, content);

            new Thread(() -> {
                dialogProcess.dialog.setVisible(true);
            }).start();
            process.run(dialogProcess);
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

        public DialogProcess(String title, String content) {
            this.dialog = new JDialog((Frame) null, title, true);
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
        }

        public void dispose() {
            dialog.dispose();
            running[0] = false;
        }
    }
}
