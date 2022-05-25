package dsw.rudok.app.gui.swing.view;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class MyMenuBar extends JMenuBar {

    public MyMenuBar() {

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.add(MainFrame.getInstance().getActionManager().getNewNodeAction());//file tab
        fileMenu.add(MainFrame.getInstance().getActionManager().getRemoveNodeAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getRenameNodeAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getShareDocumentAction());

        JMenu saveMenu = new JMenu("Saving");
        saveMenu.add(MainFrame.getInstance().getActionManager().getSaveProjectAction());
        saveMenu.add(MainFrame.getInstance().getActionManager().getSaveProjectAsAction());
        saveMenu.add(MainFrame.getInstance().getActionManager().getOpenProjectAction());
        saveMenu.add(MainFrame.getInstance().getActionManager().getSwitchWorkspaceAction());

        JMenu aboutMenu = new JMenu("About");//about tab
        aboutMenu.setMnemonic(KeyEvent.VK_A);
        aboutMenu.add(MainFrame.getInstance().getActionManager().getAboutAction());

        JMenu slotMenu = new JMenu("Slot Handling");
        aboutMenu.setMnemonic(KeyEvent.VK_S);
        slotMenu.add(MainFrame.getInstance().getActionManager().getSelectSlotAction());
        slotMenu.add(MainFrame.getInstance().getActionManager().getDeleteSlotAction());
        slotMenu.add(MainFrame.getInstance().getActionManager().getNewRectangleSlotAction());
        slotMenu.add(MainFrame.getInstance().getActionManager().getNewCircleSlotAction());
        slotMenu.add(MainFrame.getInstance().getActionManager().getNewTriangleSlotAction());
        slotMenu.add(MainFrame.getInstance().getActionManager().getMoveSlotAction());
        slotMenu.add(MainFrame.getInstance().getActionManager().getScaleSlotAction());
        slotMenu.add(MainFrame.getInstance().getActionManager().getRotateSlotAction());
        slotMenu.add(MainFrame.getInstance().getActionManager().getUndoAction());
        slotMenu.add(MainFrame.getInstance().getActionManager().getRedoAction());

        this.add(fileMenu);
        this.add(saveMenu);
        this.add(slotMenu);
        this.add(aboutMenu);


    }
}
