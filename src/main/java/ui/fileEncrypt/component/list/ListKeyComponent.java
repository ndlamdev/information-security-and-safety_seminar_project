/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:38 AM - 12/11/2024
 * User: lam-nguyen
 **/

package main.java.ui.fileEncrypt.component.list;

import main.java.helper.FileTransferable;
import main.java.helper.IconResizeHelper;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.*;
import java.util.List;

public class ListKeyComponent extends JPanel implements Observer {
    private List<String> keys;
    private boolean resize = true;
    private ImageIcon icon;
    private JPanel panelListKey;
    private JScrollPane scroll;
    private JLabel label;
    private FileTransferHandler handler;
    private String path;


    public ListKeyComponent() {
        this.init();
    }

    public ListKeyComponent(String path) {
        this.init();
        setPath(path);
    }

    public void setListKey(List<String> keys) {
        this.keys = keys;
        panelListKey.removeAll();
        keys.forEach(it -> panelListKey.add(initKeyComponent(it)));
        repaintKeys();
    }

    private JButton initKeyComponent(String pathFile) {
        var indexOf = pathFile.indexOf("/");
        var name = pathFile;
        if (indexOf != -1)
            name = name.substring(indexOf + 1, name.length());
        return new JButton(name) {
            {
                setTransferHandler(handler);
                setName(pathFile);
                setPreferredSize(new Dimension(300, 50));
                setIcon(icon);
                setFont(new Font("", Font.PLAIN, 20));
                setIconTextGap(10);
                setHorizontalAlignment(SwingConstants.LEFT);
//                addActionListener(event -> {
//                    TransferHandler handler = button.getTransferHandler();
//                    handler.exportAsDrag((JButton)event.getSource(), event, TransferHandler.COPY);
//                });

            }
        };
    }

    private void init() {
        handler = new FileTransferHandler(null, path);

        setBackground(Color.white);
        icon = IconResizeHelper.getInstance().initImageIcon("icon/key.png", 20, 20);

        label = new JLabel("Danh sách khóa") {{
            setFont(new Font("", Font.BOLD, 25));
        }};
        this.add(label);

        panelListKey = new JPanel() {{
            setBackground(Color.white);
        }};
        scroll = new JScrollPane(panelListKey) {{
            setOpaque(false);
        }};
        this.add(scroll);
    }

    @Override
    public void update(Observable observable, Object o) {
        scroll.setPreferredSize(new Dimension(getParent().getWidth(), getHeight() - 50));
        if (!resize) return;
        resize = false;
        repaintKeys();
    }

    private void repaintKeys() {
        panelListKey.setPreferredSize(new Dimension(scroll.getPreferredSize().width - 25, keys == null ? 0 : (keys.size() + 1) * 50)); //keys == null ? 0 : (keys.size() + 1) * 50
        Arrays.stream(panelListKey.getComponents()).forEach(it -> it.setPreferredSize(new Dimension(panelListKey.getPreferredSize().width - 50, 50)));
        panelListKey.updateUI();
        this.updateUI();
    }

    private List<String> getListKey(String path) {
        var folder = new File(path);
        File[] keys = folder.listFiles(file -> file.isFile() && file.getName().endsWith("keys"));
        var pathKeys = Arrays.stream(keys).map(it -> folder.getName() + "/" + it.getName()).toList();
        List<String> result = new ArrayList<>(pathKeys);
        Arrays.stream(folder.listFiles(File::isDirectory)).forEach(it -> {
            result.addAll(getListKey(it.getAbsolutePath()));
        });
        return result;
    }

    public void setPath(String path) {
        this.path = path;
        setListKey(getListKey(path));
    }

    static class FileTransferHandler extends TransferHandler {
        private final JButton key;
        private final String path;

        public FileTransferHandler(JButton tree, String path) {
            this.key = tree;
            this.path = path;
        }

        @Override
        public int getSourceActions(JComponent c) {
            return COPY;
        }

        @Override
        protected Transferable createTransferable(JComponent c) {
            String pathFile = c.getName();
            if (pathFile == null) return null;

            // Giả lập file, tạo danh sách file từ tên node
            File file = new File(path + "/" + pathFile);
            if (file.isDirectory()) return null;

            List<File> fileList = new ArrayList<>();
            fileList.add(file);

            return new FileTransferable(fileList);
        }
    }

}
