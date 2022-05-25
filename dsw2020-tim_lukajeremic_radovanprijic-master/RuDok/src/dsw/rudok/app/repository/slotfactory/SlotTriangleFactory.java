package dsw.rudok.app.repository.slotfactory;

import dsw.rudok.app.repository.node.RuNode;
import dsw.rudok.app.repository.slots.Slot;
import dsw.rudok.app.repository.slots.SlotTriangle;
import dsw.rudok.app.repository.slots.SlotType;

import java.awt.*;

public class SlotTriangleFactory extends SlotFactory {

    @Override
    public Slot createSlot(String name, RuNode parent, Dimension size, Point position) {
        Slot slot = new SlotTriangle(name, parent, size, position);
        return slot;
    }
}

