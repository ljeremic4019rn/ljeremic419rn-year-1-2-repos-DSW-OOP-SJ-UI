package dsw.rudok.app.gui.swing.state;

import dsw.rudok.app.AppCore;
import dsw.rudok.app.command.MoveSlotCommand;
import dsw.rudok.app.command.RotateSlotCommand;
import dsw.rudok.app.errorhandler.ErrorType;
import dsw.rudok.app.gui.swing.view.observers.PageView;
import dsw.rudok.app.gui.swing.view.painters.SlotPainter;
import dsw.rudok.app.repository.slots.Slot;
import lombok.Setter;
import dsw.rudok.app.slothandler.ModificationType;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;

@Setter
public class RotateState extends State {
    private PageView mediator;
    Point poz1 = new Point();
    Point poz2 = new Point();

    public RotateState(PageView mediator) {
        this.mediator = mediator;
    }

    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (mediator.getPage().getSelectedSlots().isEmpty()) {
                AppCore.getInstance().getIErrorHandler().generateError(ErrorType.NO_SHAPE_SELECTED);
            }
            else{
                poz1.setLocation(e.getX(),e.getY());
                mediator.getPage().setSelectedSlotsBeforeModification(createSelectedSlotsHashMap());//pravimo mapu sa novim uglovima i stavljamo u page
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mediator.getPage().setSelectedSlotsAfterModification(createSelectedSlotsHashMap());//pravimo mapu sa starim uglovima i stavljamo u page
        mediator.getCommandManager().addCommand(new RotateSlotCommand(mediator.getPage()));//pravimo command za undo/redo
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        poz2.x = e.getX(); //uzima se pozicija gde je mis
        poz2.y = e.getY();
        AppCore.getInstance().getISlotHandler().handleSlot(mediator.getPage().getSelectedSlots(), poz1, poz2, ModificationType.ROTATION);
    }

    public HashMap createSelectedSlotsHashMap(){
        HashMap<String, Double> selectedSlotsHashMap = new HashMap<>();

        for(Slot slot: mediator.getPage().getSelectedSlots()){
            Double oldAngle = slot.getAngle();
            selectedSlotsHashMap.put(slot.getName(), oldAngle);
        }

        return selectedSlotsHashMap;
    }

}
