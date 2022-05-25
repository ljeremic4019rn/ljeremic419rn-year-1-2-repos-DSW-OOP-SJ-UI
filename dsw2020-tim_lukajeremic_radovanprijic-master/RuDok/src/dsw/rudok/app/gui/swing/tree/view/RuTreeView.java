package dsw.rudok.app.gui.swing.tree.view;

import dsw.rudok.app.gui.swing.tree.controller.RuTreeCellEditor;
import dsw.rudok.app.gui.swing.tree.controller.RuTreeSelectionListener;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;

public class RuTreeView extends JTree { //ova klasa samo povezuje sve ostale elemente zajedno da bi Tree radio VVV


    public RuTreeView(DefaultTreeModel defaultTreeModel) {
        setModel(defaultTreeModel); // uzima model

        RuTreeCellRenderer ruTreeCellRenderer = new RuTreeCellRenderer(); //setuje sve komponente koje smo napravili (Editor, listener, renderer)
        addTreeSelectionListener(new RuTreeSelectionListener()); //setuje se selection listener
        setCellEditor(new RuTreeCellEditor(this, ruTreeCellRenderer));//setuje se cellEditor
        setCellRenderer(ruTreeCellRenderer);
        setEditable(true);
    }
}
