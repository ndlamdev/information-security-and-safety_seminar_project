/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 8:49 AM - 12/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.ui.component.tree;

import com.lamnguyen.helper.FileTransferable;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreeFileComponent extends JScrollPane {
    private String path;
    private JTree tree;

    public TreeFileComponent(String path) {
        this.path = path;
        this.init();
        setPath(path);
    }

    public TreeFileComponent() {
        this.init();
    }

    private void init() {
        tree = new JTree() {{
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            setVisible(path != null);
        }};
        tree.setCellRenderer(new CustomTreeCellRenderer());
        tree.setDragEnabled(true);
        tree.setTransferHandler(new FileTransferHandler(tree, path));
        this.setViewportView(tree);
    }

    public void setPath(String path) {
        this.path = path;
        var file = new File(path);
        if (!file.exists()) return;
        tree.setModel(new DefaultTreeModel(setPathHelper(file)));
        tree.setVisible(true);
    }

    private DefaultMutableTreeNode setPathHelper(File file) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(file.getName());
        var listFile = file.listFiles(File::isFile);
        if (listFile != null)
            Arrays.stream(listFile).forEach(it -> root.add(new DefaultMutableTreeNode(it.getName())));
        var listDir = file.listFiles(File::isDirectory);

        if (listDir != null)
            Arrays.stream(listDir).forEach(it -> root.add(setPathHelper(it)));
        return root;
    }

    static class FileTransferHandler extends TransferHandler {
        private final JTree tree;
        private final String path;

        public FileTransferHandler(JTree tree, String path) {
            this.tree = tree;
            this.path = path;
        }

        @Override
        public int getSourceActions(JComponent c) {
            return COPY;
        }

        @Override
        protected Transferable createTransferable(JComponent c) {
            TreePath treePath = tree.getSelectionPath();
            if (treePath == null) return null;

            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();
            String fileName = String.join("/", Arrays.stream(selectedNode.getPath()).map(it -> it.toString()).toList());
            var indexOf = fileName.indexOf("/");
            if (indexOf != -1)
                fileName = fileName.substring(indexOf, fileName.length());
            fileName = path + fileName;

            // Giả lập file, tạo danh sách file từ tên node
            File file = new File(fileName);
            if (file.isDirectory()) return null;

            List<File> fileList = new ArrayList<>();
            fileList.add(file);

            return new FileTransferable(fileList);
        }
    }

}
