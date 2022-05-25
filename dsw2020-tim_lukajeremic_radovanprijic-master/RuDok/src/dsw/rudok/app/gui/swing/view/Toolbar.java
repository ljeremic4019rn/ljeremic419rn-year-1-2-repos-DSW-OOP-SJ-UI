package dsw.rudok.app.gui.swing.view;

import javax.swing.*;

public class Toolbar extends JToolBar {

    public Toolbar() {
        super(HORIZONTAL);
        setFloatable(false);
        add(MainFrame.getInstance().getActionManager().getNewNodeAction());
        add(MainFrame.getInstance().getActionManager().getRemoveNodeAction());
        add(MainFrame.getInstance().getActionManager().getRenameNodeAction());
        add(MainFrame.getInstance().getActionManager().getShareDocumentAction());
        add(MainFrame.getInstance().getActionManager().getSaveProjectAction());
        add(MainFrame.getInstance().getActionManager().getSaveProjectAsAction());
        add(MainFrame.getInstance().getActionManager().getOpenProjectAction());
        add(MainFrame.getInstance().getActionManager().getSwitchWorkspaceAction());
        add(MainFrame.getInstance().getActionManager().getSelectSlotAction());
        add(MainFrame.getInstance().getActionManager().getDeleteSlotAction());
        add(MainFrame.getInstance().getActionManager().getNewRectangleSlotAction());
        add(MainFrame.getInstance().getActionManager().getNewCircleSlotAction());
        add(MainFrame.getInstance().getActionManager().getNewTriangleSlotAction());
        add(MainFrame.getInstance().getActionManager().getMoveSlotAction());
        add(MainFrame.getInstance().getActionManager().getScaleSlotAction());
        add(MainFrame.getInstance().getActionManager().getRotateSlotAction());
        add(MainFrame.getInstance().getActionManager().getUndoAction());
        add(MainFrame.getInstance().getActionManager().getRedoAction());
    }
}
