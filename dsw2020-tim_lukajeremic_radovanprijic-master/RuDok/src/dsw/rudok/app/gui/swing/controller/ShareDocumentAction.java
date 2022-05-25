package dsw.rudok.app.gui.swing.controller;

import com.sun.tools.javac.Main;
import dsw.rudok.app.AppCore;
import dsw.rudok.app.errorhandler.ErrorType;
import dsw.rudok.app.gui.swing.tree.model.RuTreeItem;
import dsw.rudok.app.gui.swing.view.MainFrame;
import dsw.rudok.app.gui.swing.view.SharePane;
import dsw.rudok.app.observer.ISubscriber;
import dsw.rudok.app.repository.Document;
import dsw.rudok.app.repository.Project;
import dsw.rudok.app.repository.Workspace;
import dsw.rudok.app.repository.node.RuNode;
import lombok.Setter;

import javax.print.Doc;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;


public class ShareDocumentAction  extends AbstractRudokAction{


    public ShareDocumentAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F1, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("images/share.png"));
        putValue(NAME, "Share document");
        putValue(SHORT_DESCRIPTION, "Share document");

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (MainFrame.getInstance().getTree().getSelectedNode() instanceof Document) {
            RuNode selectedDoc = MainFrame.getInstance().getTree().getSelectedNode();

            System.err.println(selectedDoc);

            SharePane sharePane = new SharePane((Document) selectedDoc);
            sharePane.setVisible(true);

        }
        else {
            AppCore.getInstance().getIErrorHandler().generateError(ErrorType.NO_DOCUMENT_SELECTED);
        }
    }
}
