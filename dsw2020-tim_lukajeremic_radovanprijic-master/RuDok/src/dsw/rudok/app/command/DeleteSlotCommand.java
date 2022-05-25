package dsw.rudok.app.command;

import dsw.rudok.app.gui.swing.view.observers.PageView;
import dsw.rudok.app.repository.Page;
import dsw.rudok.app.repository.slots.Slot;
import dsw.rudok.app.repository.slots.SlotType;

import java.awt.*;

public class DeleteSlotCommand extends AbstractCommand{
    private Page page;
    private Slot slot;

    public DeleteSlotCommand(Page page, Slot slot) {
        this.page = page;
        this.slot = slot;
    }

    @Override
    public void doCommand() {
        page.removeChild(slot);
    }

    @Override
    public void undoCommand() {
        page.addChild(slot);
        if(slot.getIsGreen() == true){
            slot.setIsGreen(false);
        }
    }
}
