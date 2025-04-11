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
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TreeFileComponent extends JScrollPane {
    private String path;
    private JTree tree;
    private DefaultTreeModel treeModel;
    private File rootDir;

    public TreeFileComponent(String path) {
        this.init();
        setPath(path);
    }

    public TreeFileComponent() {
        this.init();
        this.event();
    }

    private void init() {
        tree = new JTree() {{
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            setVisible(path != null);
        }};
        tree.setCellRenderer(new CustomTreeCellRenderer());
        tree.setDragEnabled(true);
        this.setViewportView(tree);
    }

    private void event() {
        tree.addTreeWillExpandListener(new TreeWillExpandListener() {
            @Override
            public void treeWillExpand(TreeExpansionEvent event) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) event.getPath().getLastPathComponent();
                if (node.getParent() == null) return;
                node.removeAllChildren(); // Xóa dummy node
                loadFiles(node, rootDir.getPath() + File.separator + node);
                treeModel.reload(node); // Cập nhật lại node
            }

            @Override
            public void treeWillCollapse(TreeExpansionEvent event) {
                // Không cần xử lý sự kiện đóng node
            }
        });
    }

    public void setPath(String path) {
        this.path = path;
        rootDir = new File(path);
        if (!rootDir.exists()) return;
        load();
        tree.setTransferHandler(new FileTransferHandler(tree, path));
        tree.setVisible(true);
    }

    private void load() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootDir.getName());
        treeModel = new DefaultTreeModel(root, true);
        root.removeAllChildren();
        loadFiles(root, rootDir.getPath());
        treeModel.reload(root);
        tree.setModel(treeModel);
    }

    private static void loadFiles(DefaultMutableTreeNode node, String path) {
        File dir = new File(path);
        if (dir.isDirectory()) {
            File[] files = dir.listFiles(File::isFile);
            if (files == null) return;
            Arrays.sort(files, Comparator.comparing(File::getName));
            for (File file : files)
                node.add(new DefaultMutableTreeNode(file.getName(), false));

            files = dir.listFiles(File::isDirectory);
            if (files == null) return;
            Arrays.sort(files, Comparator.comparing(File::getName));
            for (File file : files)
                node.add(new DefaultMutableTreeNode(file.getName(), true));
        }
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
            String fileName = String.join(File.separator, Arrays.stream(selectedNode.getPath()).map(it -> it.toString()).toList());
            var indexOf = fileName.indexOf(File.separator);
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
