package dsw.rudok.app.core;

import dsw.rudok.app.observer.IPublisher;
import dsw.rudok.app.repository.slots.Slot;
import dsw.rudok.app.slothandler.ModificationType;

import java.awt.*;
import java.util.ArrayList;

public interface ISlotHandler extends IPublisher {
    void handleSlot(ArrayList<Slot> slots, Point pressedPoint, Point draggedPoint, ModificationType type);
    void handleSlotCommand(Slot slot, Object modificationAttribute, ModificationType modificationType);
}
