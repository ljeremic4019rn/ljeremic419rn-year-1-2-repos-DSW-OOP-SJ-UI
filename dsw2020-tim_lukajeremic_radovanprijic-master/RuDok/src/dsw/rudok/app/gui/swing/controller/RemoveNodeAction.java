package dsw.rudok.app.gui.swing.controller;

import dsw.rudok.app.AppCore;
import dsw.rudok.app.errorhandler.ErrorType;
import dsw.rudok.app.gui.swing.view.MainFrame;
import dsw.rudok.app.repository.*;
import dsw.rudok.app.repository.slots.Slot;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class RemoveNodeAction extends AbstractRudokAction implements ActionListener {

    public RemoveNodeAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        putValue(SMALL_ICON, loadIcon("images/deleteImg.png"));
        putValue(NAME, "Remove node");
        putValue(SHORT_DESCRIPTION, "Remove node");
    }

    public void actionPerformed(ActionEvent arg0) {
        if(MainFrame.getInstance().getTree().getSelectedNode() == null || MainFrame.getInstance().getNumBull() == 1){
            AppCore.getInstance().getIErrorHandler().generateError(ErrorType.NO_NODE_SELECTED);
            return;
        }
        if(MainFrame.getInstance().getTree().getSelectedNode() instanceof Project) {
            MainFrame.getInstance().getTree().removeProject((Project) MainFrame.getInstance().getTree().getSelectedNode());
            MainFrame.getInstance().setNumBull(1);//ovaj boolean je da bi regulisali error handler nakon sto smo obrisali nesto, jer se pointer zadrzi na obrisanom objektu
            MainFrame.getInstance().getProjectView().removeAll();//kada obrisemo projekat da se obrisu svi tabovi

        } else if(MainFrame.getInstance().getTree().getSelectedNode()  instanceof Document) {
            MainFrame.getInstance().getTree().removeDocument((Document) MainFrame.getInstance().getTree().getSelectedNode());
            MainFrame.getInstance().setNumBull(1);


        } else if(MainFrame.getInstance().getTree().getSelectedNode() instanceof Page) {
            MainFrame.getInstance().getTree().removePage((Page) MainFrame.getInstance().getTree().getSelectedNode());
            MainFrame.getInstance().setNumBull(1);

        } else if(MainFrame.getInstance().getTree().getSelectedNode() instanceof Slot) {
            MainFrame.getInstance().getTree().removeSlot((Slot) MainFrame.getInstance().getTree().getSelectedNode());
            MainFrame.getInstance().setNumBull(1);

        } else if(MainFrame.getInstance().getTree().getSelectedNode() instanceof Workspace){
            AppCore.getInstance().getIErrorHandler().generateError(ErrorType.WORKSPACE_DELETION);
            return;
        }
    }
}
