package dsw.rudok.app.command;

import dsw.rudok.app.AppCore;
import dsw.rudok.app.repository.Page;
import dsw.rudok.app.repository.node.RuNode;
import dsw.rudok.app.repository.slots.Slot;
import dsw.rudok.app.slothandler.ModificationType;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.HashMap;

@Getter
@Setter

public class MoveSlotCommand extends AbstractCommand{
    private Page page;
    private HashMap<String, Point> previousSlots;
    private HashMap<String, Point> newSlots;
    private ModificationType modificationType;
    private int a = 0;

    public MoveSlotCommand(Page page) {
        this.page = page;
        this.previousSlots = (HashMap<String, Point>) page.getSelectedSlotsBeforeModification();
        this.newSlots = (HashMap<String, Point>) page.getSelectedSlotsAfterModification();
        modificationType = ModificationType.MOVING;
    }

    @Override
    public void doCommand() {
        if(a != 0){
            for (RuNode arraySlot : page.getChildren()) {
                if (newSlots.containsKey(arraySlot.getName())) {
                    Point newPosition = new Point();
                    newPosition.setLocation(newSlots.get(arraySlot.getName()));
                    AppCore.getInstance().getISlotHandler().handleSlotCommand((Slot) arraySlot, newPosition, modificationType);
                }
            }
        }
        else{
            a++;
        }
    }

    @Override
    public void undoCommand() {
        for(RuNode arraySlot: page.getChildren()){
            if(previousSlots.containsKey(arraySlot.getName())){
                Point oldPosition = new Point();
                oldPosition.setLocation(previousSlots.get(arraySlot.getName()));
                AppCore.getInstance().getISlotHandler().handleSlotCommand((Slot) arraySlot, oldPosition, modificationType);
            }
        }
    }
}
