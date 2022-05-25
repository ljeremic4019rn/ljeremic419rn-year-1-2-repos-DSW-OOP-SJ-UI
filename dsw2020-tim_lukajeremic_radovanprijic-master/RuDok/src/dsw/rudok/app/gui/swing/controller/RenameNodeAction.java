package dsw.rudok.app.gui.swing.controller;

import dsw.rudok.app.AppCore;
import dsw.rudok.app.errorhandler.ErrorType;
import dsw.rudok.app.gui.swing.tree.model.RuTreeItem;
import dsw.rudok.app.gui.swing.view.MainFrame;
import dsw.rudok.app.repository.Document;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class RenameNodeAction extends AbstractRudokAction{

    public RenameNodeAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
        putValue(SMALL_ICON, loadIcon("images/renameImg.png"));
        putValue(NAME, "Rename node");
        putValue(SHORT_DESCRIPTION, "Rename node");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(MainFrame.getInstance().getTree().getSelectedNode() == null || MainFrame.getInstance().getNumBull() == 1){
            AppCore.getInstance().getIErrorHandler().generateError(ErrorType.NO_NODE_SELECTED);
          return;
        }

        JFrame f = new JFrame();
        f.setTitle("Node renaming");
        String newName = JOptionPane.showInputDialog(f, "Enter a new name for your node:", "Node renaming", JOptionPane.INFORMATION_MESSAGE);

        if (newName == null)
            return;

        MainFrame.getInstance().getTree().renameNode(newName);
    }
}
