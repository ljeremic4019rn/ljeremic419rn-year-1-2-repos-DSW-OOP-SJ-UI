package dsw.rudok.app.repository;

import dsw.rudok.app.observer.ActionType;
import dsw.rudok.app.repository.node.RuNode;
import dsw.rudok.app.repository.node.RuNodeComposite;
import dsw.rudok.app.repository.slots.Slot;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
public class Page extends RuNodeComposite{
    private ArrayList<Slot> selectedSlots;
    private Object selectedSlotsBeforeModification;
    private Object selectedSlotsAfterModification;
    private Slot slotToBeDeleted;

    public Page(String name, RuNode parent) {
        super(name, parent);
        this.selectedSlots = new ArrayList<>();
    }

    @Override
    public void addChild(RuNode child) {
        if (child != null &&  child instanceof Slot){
            Slot slot = (Slot) child;
            this.getChildren().add(slot);
            notifySubscribers(child, ActionType.SLOT_ADDITION);
            ((Project)this.getParent().getParent()).setChanged(true);
        }
    }

    @Override
    public void removeChild(RuNode child) {
        if (child != null && child instanceof Slot) {
            Slot slot = (Slot) child;
            this.getChildren().remove(slot);
            notifySubscribers(child, ActionType.SLOT_REMOVAL);
            ((Project)this.getParent().getParent()).setChanged(true);
        }
    }

    @Override
    public void renameNode(String newName){
        this.setName(newName);
        notifySubscribers(this, ActionType.PAGE_RENAMING);
        ((Project)this.getParent().getParent()).setChanged(true);
    }
}
