package dsw.rudok.app.gui.swing.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionManager {
    private NewNodeAction newNodeAction;
    private RemoveNodeAction removeNodeAction;
    private AboutAction aboutAction;
    private RenameNodeAction renameNodeAction;
    private ShareDocumentAction shareDocumentAction;
    private UndoAction undoAction;
    private RedoAction redoAction;
    private DeleteSlotAction deleteSlotAction;
    private MoveSlotAction moveSlotAction;
    private NewCircleSlotAction newCircleSlotAction;
    private NewRectangleSlotAction newRectangleSlotAction;
    private NewTriangleSlotAction newTriangleSlotAction;
    private RotateSlotAction rotateSlotAction;
    private ScaleSlotAction scaleSlotAction;
    private SelectSlotAction selectSlotAction;
    private SaveProjectAction saveProjectAction;
    private OpenProjectAction openProjectAction;
    private SaveProjectAsAction saveProjectAsAction;
    private SwitchWorkspaceAction switchWorkspaceAction;

    public ActionManager() {
        initialiseActions();
    }

    private void initialiseActions() {
        newNodeAction = new NewNodeAction();
        removeNodeAction = new RemoveNodeAction();
        aboutAction = new AboutAction();
        renameNodeAction = new RenameNodeAction();
        shareDocumentAction = new ShareDocumentAction();
        undoAction = new UndoAction();
        redoAction = new RedoAction();
        deleteSlotAction = new DeleteSlotAction();
        moveSlotAction = new MoveSlotAction();
        newCircleSlotAction = new NewCircleSlotAction();
        newRectangleSlotAction = new NewRectangleSlotAction();
        newTriangleSlotAction = new NewTriangleSlotAction();
        rotateSlotAction = new RotateSlotAction();
        scaleSlotAction = new ScaleSlotAction();
        selectSlotAction = new SelectSlotAction();
        saveProjectAction = new SaveProjectAction();
        openProjectAction = new OpenProjectAction();
        saveProjectAsAction = new SaveProjectAsAction();
        switchWorkspaceAction = new SwitchWorkspaceAction();
    }
}
