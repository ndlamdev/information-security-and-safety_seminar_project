package main.java.ui.fileEncrypt.component.tree;

import main.java.helper.IconResizeHelper;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class CustomTreeCellRenderer extends DefaultTreeCellRenderer {
    private final Icon parentIcon;
    private final Icon childIcon;

    public CustomTreeCellRenderer() {
        parentIcon = IconResizeHelper.getInstance().initImageIcon("icon/folder.png", 20, 20);
        childIcon = IconResizeHelper.getInstance().initImageIcon("icon/file2.png", 20, 20);
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
                                                  boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

        if (leaf && row != 0) {
            setIcon(childIcon);  // Use child icon for leaf nodes
        } else {
            setIcon(parentIcon); // Use parent icon for non-leaf nodes (parents)
        }
        return this;
    }
}