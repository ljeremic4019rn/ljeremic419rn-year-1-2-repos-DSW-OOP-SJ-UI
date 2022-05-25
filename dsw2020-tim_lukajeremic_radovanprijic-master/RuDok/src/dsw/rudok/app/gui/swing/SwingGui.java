package dsw.rudok.app.gui.swing;

import com.sun.tools.javac.Main;
import dsw.rudok.app.core.Repository;
import dsw.rudok.app.errorhandler.MyError;
import dsw.rudok.app.gui.swing.view.MainFrame;
import dsw.rudok.app.core.Gui;
import dsw.rudok.app.gui.swing.view.observers.DocumentView;
import dsw.rudok.app.gui.swing.view.observers.PageView;
import dsw.rudok.app.observer.ActionType;
import dsw.rudok.app.repository.slots.Slot;

import javax.swing.*;

public class SwingGui implements Gui {

    private MainFrame instance;
    private Repository documentRepository;

    public SwingGui(Repository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public void start() {
        instance = MainFrame.getInstance();
        instance.setDocumentRepository(documentRepository);
        instance.initialiseWorkspaceTree();
        instance.setVisible(true);
    }

    public void update(Object notification, ActionType action) {
        if (notification instanceof MyError) {
            MainFrame.getInstance().showError((MyError) notification, action);
        }
        else if(notification instanceof Slot) {
            int br = MainFrame.getInstance().getProjectView().getSelectedIndex() ;
            DocumentView dv = MainFrame.getInstance().getProjectView().getDocuments().get(br);
            ((PageView) dv.getSelectedFrame()).update(notification, action);
        }
    }
}
