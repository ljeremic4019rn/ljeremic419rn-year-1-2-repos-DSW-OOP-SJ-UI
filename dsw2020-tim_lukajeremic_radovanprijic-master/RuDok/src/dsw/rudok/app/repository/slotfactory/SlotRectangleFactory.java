package dsw.rudok.app.repository.slotfactory;

import dsw.rudok.app.repository.node.RuNode;
import dsw.rudok.app.repository.slots.Slot;
import dsw.rudok.app.repository.slots.SlotRectangle;
import dsw.rudok.app.repository.slots.SlotType;

import java.awt.*;

public class SlotRectangleFactory extends SlotFactory {

    @Override
    public Slot createSlot(String name, RuNode parent, Dimension size, Point position) {
        Slot slot = new SlotRectangle(name, parent, size, position);
        return slot;
    }
}
