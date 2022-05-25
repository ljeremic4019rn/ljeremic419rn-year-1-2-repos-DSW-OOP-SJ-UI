package dsw.rudok.app.gui.swing.controller;

import dsw.rudok.app.AppCore;
import dsw.rudok.app.gui.swing.controller.filters.WorkspaceFileFilter;
import dsw.rudok.app.gui.swing.view.MainFrame;
import dsw.rudok.app.repository.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class SwitchWorkspaceAction extends AbstractRudokAction{

    public SwitchWorkspaceAction (){
      //  putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("images/switch.png"));
        putValue(NAME, "Switch workspace");
        putValue(SHORT_DESCRIPTION, "Switch workspace");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jfc = new JFileChooser();//picker
        jfc.setFileFilter(new WorkspaceFileFilter());

        if(jfc.showOpenDialog(MainFrame.getInstance())==JFileChooser.APPROVE_OPTION){
            MainFrame.getInstance().getTree().clearProjects();
            AppCore.getInstance().getISerialization().readWorkspace(jfc.getSelectedFile(), MainFrame.getInstance().getTree(),
                    MainFrame.getInstance().getDocumentRepository().getWorkspace());
        }
    }
}
