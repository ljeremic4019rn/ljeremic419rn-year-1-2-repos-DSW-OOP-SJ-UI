package dsw.rudok.app.gui.swing.controller;

import dsw.rudok.app.AppCore;
import dsw.rudok.app.errorhandler.ErrorType;
import dsw.rudok.app.gui.swing.controller.AbstractRudokAction;
import dsw.rudok.app.gui.swing.view.MainFrame;
import dsw.rudok.app.gui.swing.view.observers.DocumentView;
import dsw.rudok.app.gui.swing.view.observers.PageView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class RotateSlotAction  extends AbstractRudokAction {

    public RotateSlotAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("images/rotate.png"));
        putValue(NAME, "Rotate slot");
        putValue(SHORT_DESCRIPTION, "Rotate slot");
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //da li imamo napravljan projekat                           da li imamo napravljene tabove
        if (MainFrame.getInstance().getProjectView() == null || MainFrame.getInstance().getProjectView().getSelectedIndex() == -1){
            AppCore.getInstance().getIErrorHandler().generateError(ErrorType.NO_FRAME_CREATED);
            return;
        }

        int br = MainFrame.getInstance().getProjectView().getSelectedIndex() ;
        DocumentView dv = MainFrame.getInstance().getProjectView().getDocuments().get(br);

        //proverava da li postoje prozori u tabu
        if (dv.getPages().isEmpty()){
            AppCore.getInstance().getIErrorHandler().generateError(ErrorType.NO_FRAME_CREATED);
            return;
        }

        //proverava da li si kliknuo na neki prozor u tabu
        if (dv.getSelectedFrame() == null){
            AppCore.getInstance().getIErrorHandler().generateError(ErrorType.NO_FRAME_SELECTED);
            return;
        }

        ((PageView) dv.getSelectedFrame()).startRotateState();
    }
}
