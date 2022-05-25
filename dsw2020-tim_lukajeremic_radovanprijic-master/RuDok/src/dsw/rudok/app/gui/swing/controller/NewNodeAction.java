package dsw.rudok.app.gui.swing.controller;

import dsw.rudok.app.AppCore;
import dsw.rudok.app.errorhandler.ErrorType;
import dsw.rudok.app.gui.swing.tree.model.RuTreeItem;
import dsw.rudok.app.gui.swing.view.MainFrame;
import dsw.rudok.app.repository.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class NewNodeAction extends AbstractRudokAction {

    int projectCounter = 0;
    int documentCounter = 0;
    int pageCounter = 0;
    int slotCounter = 0;

    public NewNodeAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("images/addimg.png"));
        putValue(NAME, "New node");
        putValue(SHORT_DESCRIPTION, "New node");
    }

    public void actionPerformed(ActionEvent arg0) {
        if(MainFrame.getInstance().getTree().getSelectedNode() == null || MainFrame.getInstance().getNumBull() == 1){
            AppCore.getInstance().getIErrorHandler().generateError(ErrorType.NO_NODE_SELECTED);
            return;
        }

        if (MainFrame.getInstance().getTree().getSelectedNode() instanceof Workspace) {
                projectCounter++;
                Project p = new Project("Project " + projectCounter, ((RuTreeItem) MainFrame.getInstance().getWorkspaceTree().getModel().getRoot()).getNodeModel());
                MainFrame.getInstance().getTree().addProject(p);//za workspace mo ze direktno root da se gleda zato je model>root>nodeModel

            }
            else if(MainFrame.getInstance().getTree().getSelectedNode() instanceof Project) {
                documentCounter++;
                Document d = new Document("Document " + documentCounter, MainFrame.getInstance().getTree().getSelectedNode());
                MainFrame.getInstance().getTree().addDocument(d);//dok za sve ostale mozes samo selektovani da preuzmes


            } else if(MainFrame.getInstance().getTree().getSelectedNode() instanceof Document) {
                pageCounter++;
                Page p = new Page("Page " + pageCounter, MainFrame.getInstance().getTree().getSelectedNode());
                MainFrame.getInstance().getTree().addPage(p);

            } /* else if(MainFrame.getInstance().getTree().getSelectedNode() instanceof Page) {
                slotCounter++;
                Slot s = new Slot("Slot " + slotCounter, MainFrame.getInstance().getTree().getSelectedNode());
                MainFrame.getInstance().getTree().addSlot(s);
            } */
    }
}

