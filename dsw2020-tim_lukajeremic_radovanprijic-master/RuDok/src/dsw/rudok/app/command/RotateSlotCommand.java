package dsw.rudok.app.command;

import dsw.rudok.app.AppCore;
import dsw.rudok.app.repository.Page;
import dsw.rudok.app.repository.node.RuNode;
import dsw.rudok.app.repository.slots.Slot;
import dsw.rudok.app.slothandler.ModificationType;

import java.awt.*;
import java.util.HashMap;

public class RotateSlotCommand extends AbstractCommand{
    private Page page;
    private HashMap<String, Double> previousSlots;
    private HashMap<String, Double> newSlots;
    private ModificationType modificationType;
    private int a = 0;

    public RotateSlotCommand(Page page) {
        this.page = page;
        this.previousSlots = (HashMap<String, Double>) page.getSelectedSlotsBeforeModification();
        this.newSlots = (HashMap<String, Double>) page.getSelectedSlotsAfterModification();
        modificationType = ModificationType.ROTATION;
    }

    @Override
    public void doCommand() {
        if(a != 0){
            for (RuNode arraySlot : page.getChildren()) {
                if (newSlots.containsKey(arraySlot.getName())) {
                    Double newAngle = newSlots.get(arraySlot.getName());
                    AppCore.getInstance().getISlotHandler().handleSlotCommand((Slot) arraySlot, newAngle, modificationType);
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
                Double oldAngle = previousSlots.get(arraySlot.getName());
                AppCore.getInstance().getISlotHandler().handleSlotCommand((Slot) arraySlot, oldAngle, modificationType);
            }
        }
    }
}
