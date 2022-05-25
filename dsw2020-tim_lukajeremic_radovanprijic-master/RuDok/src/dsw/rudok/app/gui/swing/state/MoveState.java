package dsw.rudok.app.gui.swing.state;

import dsw.rudok.app.AppCore;
import dsw.rudok.app.command.MoveSlotCommand;
import dsw.rudok.app.errorhandler.ErrorType;
import dsw.rudok.app.gui.swing.view.observers.PageView;
import dsw.rudok.app.gui.swing.view.painters.SlotPainter;
import dsw.rudok.app.repository.slots.Slot;
import lombok.Getter;
import lombok.Setter;
import dsw.rudok.app.slothandler.ModificationType;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;

@Getter
@Setter
public class MoveState extends State {
    private PageView mediator;
    Point poz1 = new Point();
    Point poz2 = new Point();
    Point poz3 = new Point();

    public MoveState(PageView mediator) {
        this.mediator = mediator;
    }

    public void mousePressed(MouseEvent e) {
        Point position = e.getPoint();

        if (e.getButton()==MouseEvent.BUTTON1){
            if (mediator.getPage().getSelectedSlots().isEmpty()) {
                AppCore.getInstance().getIErrorHandler().generateError(ErrorType.NO_SHAPE_SELECTED);
            }
            else{
                if(mediator.getSlotIndexAtPosition(position) != -1){
                    for(SlotPainter sp: mediator.getSlotPainters()) {
                        if(sp.isElementAt(position)){
                            if(mediator.getPage().getSelectedSlots().contains(sp.getSlot())){
                                poz1.setLocation(e.getX(),e.getY());
                                mediator.getPage().setSelectedSlotsBeforeModification(createSelectedSlotsHashMap());//pravimo mapu sa starim pozicijama i stavljamo u page
                            }
                            else{
                                AppCore.getInstance().getIErrorHandler().generateError(ErrorType.NO_CLICK_ON_SHAPE);
                                break;
                            }
                        }
                    }
                }
                else{
                    AppCore.getInstance().getIErrorHandler().generateError(ErrorType.NO_CLICK_ON_SHAPE);
                }
            }
            poz3.setLocation(e.getX(),e.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mediator.getPage().setSelectedSlotsAfterModification(createSelectedSlotsHashMap());//pravimo mapu sa novim pozicijama i stavljamo u page
        mediator.getCommandManager().addCommand(new MoveSlotCommand(mediator.getPage()));//pravimo comandu za undo/redo
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        poz2.setLocation(e.getX(),e.getY());
        AppCore.getInstance().getISlotHandler().handleSlot(mediator.getPage().getSelectedSlots(), poz1 , poz2, ModificationType.MOVING);
        poz1.x = e.getX();
        poz1.y = e.getY();
    }

    public HashMap createSelectedSlotsHashMap(){
        HashMap<String, Point> selectedSlotsHashMap = new HashMap<>();

        for(Slot slot: mediator.getPage().getSelectedSlots()){
            Point oldPosition = new Point();
            oldPosition.setLocation(slot.getPosition());
            selectedSlotsHashMap.put(slot.getName(), oldPosition);
        }

        return selectedSlotsHashMap;
    }
}