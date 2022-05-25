package dsw.rudok.app.command;

import dsw.rudok.app.AppCore;
import dsw.rudok.app.repository.Page;
import dsw.rudok.app.repository.node.RuNode;
import dsw.rudok.app.repository.slots.Slot;
import dsw.rudok.app.slothandler.ModificationType;

import java.awt.*;
import java.util.HashMap;

public class ScaleSlotCommand extends AbstractCommand{
    private Page page;
    private HashMap<String, Dimension> previousSlots;
    private HashMap<String, Dimension> newSlots;
    private ModificationType modificationType;
    private int a = 0;

    public ScaleSlotCommand(Page page) {
        this.page = page;
        this.previousSlots = (HashMap<String, Dimension>) page.getSelectedSlotsBeforeModification();
        this.newSlots = (HashMap<String, Dimension>) page.getSelectedSlotsAfterModification();
        modificationType = ModificationType.SCALING;
    }

    @Override
    public void doCommand() {
        if(a != 0){
            for (RuNode arraySlot : page.getChildren()) {//promeni
                if (newSlots.containsKey(arraySlot.getName())) {
                    Dimension newSize = new Dimension();
                    newSize.setSize(newSlots.get(arraySlot.getName()));
                    AppCore.getInstance().getISlotHandler().handleSlotCommand((Slot) arraySlot, newSize, modificationType);
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
                Dimension oldSize = new Dimension();
                oldSize.setSize(previousSlots.get(arraySlot.getName()));
                AppCore.getInstance().getISlotHandler().handleSlotCommand((Slot) arraySlot, oldSize, modificationType);
            }
        }
    }
}
