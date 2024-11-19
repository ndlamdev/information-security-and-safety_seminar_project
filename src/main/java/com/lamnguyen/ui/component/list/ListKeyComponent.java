/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:38 AM - 12/11/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.ui.component.list;

import com.lamnguyen.ui.component.key.ButtonKey;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.*;

public class ListKeyComponent extends JPanel implements Observer {
    private List<String> keys;
    private boolean resize = true;
    private ImageIcon icon;
    private JPanel panelListKey;
    private JScrollPane scroll;
    private JLabel label;
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
        keys.forEach(it -> panelListKey.add(new ButtonKey(it)));
        repaintKeys();
    }

    private void init() {
        setBackground(Color.white);
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
        final List<String> result = new ArrayList<>();
        if (keys != null) {
            var pathKeys = Arrays.stream(keys).map(it -> folder.getName() + "/" + it.getName()).toList();
            result.addAll(pathKeys);
        }
        var listDir = folder.listFiles(File::isDirectory);
        if (listDir != null)
            Arrays.stream(listDir).forEach(it -> {
                result.addAll(getListKey(it.getAbsolutePath()));
            });
        return result;
    }

    public void setPath(String path) {
        this.path = path;
        setListKey(getListKey(path));
    }
}
