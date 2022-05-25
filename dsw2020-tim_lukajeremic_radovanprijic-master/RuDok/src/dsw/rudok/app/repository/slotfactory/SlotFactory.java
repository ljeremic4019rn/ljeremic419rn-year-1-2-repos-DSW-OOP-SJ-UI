package dsw.rudok.app.repository.slotfactory;

import dsw.rudok.app.repository.node.RuNode;
import dsw.rudok.app.repository.slots.Slot;
import dsw.rudok.app.repository.slots.SlotType;

import java.awt.*;

public abstract class SlotFactory {

    public abstract Slot createSlot(String name, RuNode parent, Dimension size, Point position);
}
