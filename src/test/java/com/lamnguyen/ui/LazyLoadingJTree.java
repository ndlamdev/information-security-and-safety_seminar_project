package com.lamnguyen.ui;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.*;
import java.io.File;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.*;
import java.io.File;

public class LazyLoadingJTree {
    public static void main(String[] args) {
        // Tạo root node
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("C");
        DefaultTreeModel treeModel = new DefaultTreeModel(root, true);

        JTree tree = new JTree(treeModel);

        // Thêm thư mục gốc vào tree
        File rootDir = new File("C:/"); // Đổi thành đường dẫn gốc bạn muốn

        // Thêm TreeWillExpandListener
        tree.addTreeWillExpandListener(new TreeWillExpandListener() {
            @Override
            public void treeWillExpand(TreeExpansionEvent event) {
                // Lấy node được mở rộng
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) event.getPath().getLastPathComponent();
                if (node.getDepth() == 0) {
                    node.removeAllChildren(); // Xóa dummy node
                    loadFiles(node, rootDir.getPath());
                    treeModel.reload(node); // Cập nhật lại node
                    return;
                }
                node.removeAllChildren(); // Xóa dummy node
                loadFiles(node, rootDir.getPath() + File.separator + node);
                treeModel.reload(node); // Cập nhật lại node
            }

            @Override
            public void treeWillCollapse(TreeExpansionEvent event) {
                // Không cần xử lý sự kiện đóng node
            }
        });

        // Hiển thị GUI
        JFrame frame = new JFrame("Lazy Loading JTree");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new JScrollPane(tree));
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    private static void loadFiles(DefaultMutableTreeNode node, String path) {
        File dir = new File(path);
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files == null) return;
            for (File file : files)
                node.add(file.isFile() ? new DefaultMutableTreeNode(file.getName(), false) : new DefaultMutableTreeNode(file.getName(), true));
        }
    }
}
