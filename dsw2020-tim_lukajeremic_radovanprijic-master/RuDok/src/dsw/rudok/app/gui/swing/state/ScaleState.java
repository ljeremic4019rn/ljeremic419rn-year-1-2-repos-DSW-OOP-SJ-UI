package dsw.rudok.app.gui.swing.state;

import dsw.rudok.app.AppCore;
import dsw.rudok.app.command.ScaleSlotCommand;
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
public class ScaleState extends State {
    private PageView mediator;
    Point poz1 = new Point();
    Point poz2 = new Point();

    public ScaleState(PageView mediator) {
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
                                mediator.getPage().setSelectedSlotsBeforeModification(createSelectedSlotsHashMap());//pravimo mapu sa novim velicinama i stavljamo u page
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
        }
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        mediator.getPage().setSelectedSlotsAfterModification(createSelectedSlotsHashMap());//pravimo mapu sa starim velicinama i stavljamo u page
        mediator.getCommandManager().addCommand(new ScaleSlotCommand(mediator.getPage()));//pravimo command za undo/redo
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        poz2.x = e.getX();
        poz2.y = e.getY();
        AppCore.getInstance().getISlotHandler().handleSlot(mediator.getPage().getSelectedSlots(), poz1, poz2, ModificationType.SCALING);

    }

    public HashMap createSelectedSlotsHashMap(){
        HashMap<String, Dimension> selectedSlotsHashMap = new HashMap<>();

        for(Slot slot: mediator.getPage().getSelectedSlots()){
            Dimension oldSize = new Dimension();
            oldSize.setSize(slot.getSize());
            selectedSlotsHashMap.put(slot.getName(), oldSize);
        }

        return selectedSlotsHashMap;
    }
}
