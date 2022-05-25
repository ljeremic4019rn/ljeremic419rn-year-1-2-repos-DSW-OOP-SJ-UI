package dsw.rudok.app.gui.swing.controller;

import dsw.rudok.app.AppCore;
import dsw.rudok.app.errorhandler.ErrorType;
import dsw.rudok.app.gui.swing.controller.filters.ProjectFileFilter;
import dsw.rudok.app.gui.swing.view.MainFrame;
import dsw.rudok.app.repository.Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class SaveProjectAction extends AbstractRudokAction{

    public SaveProjectAction (){
      //  putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("images/save.png"));
        putValue(NAME, "Save project");
        putValue(SHORT_DESCRIPTION, "Save project");
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if(MainFrame.getInstance().getTree().getSelectedNode() instanceof Project) {
            JFileChooser jfc = new JFileChooser();
            jfc.setFileFilter(new ProjectFileFilter());

            Project project = (Project) MainFrame.getInstance().getTree().getSelectedNode();
            File projectFile = project.getProjectFile();

            if (!project.isChanged()){
                return;
            }
            if (project.getProjectFile()==null){
                if(jfc.showSaveDialog(MainFrame.getInstance())== JFileChooser.APPROVE_OPTION){
                    projectFile=jfc.getSelectedFile();
                }
                else{
                    return;
                }
            }
            AppCore.getInstance().getISerialization().serializeProject(project,projectFile);
        }
        else{
            AppCore.getInstance().getIErrorHandler().generateError(ErrorType.NO_PROJECT_SELECTED);
        }
    }
}
