package com.lamnguyen.ui.component.tree;

import com.lamnguyen.helper.IconResizeHelper;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class CustomTreeCellRenderer extends DefaultTreeCellRenderer {
    private final Icon parentIcon;
    private final Icon childIcon;

    public CustomTreeCellRenderer() {
        parentIcon = IconResizeHelper.getInstance().initImageIcon("folder.png", 20, 20);
        childIcon = IconResizeHelper.getInstance().initImageIcon("file2.png", 20, 20);
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