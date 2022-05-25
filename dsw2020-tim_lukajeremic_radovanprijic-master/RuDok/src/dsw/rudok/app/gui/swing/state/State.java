package dsw.rudok.app.gui.swing.state;

import dsw.rudok.app.gui.swing.view.painters.SlotPainter;
import dsw.rudok.app.repository.slotfactory.SlotCircleFactory;
import dsw.rudok.app.repository.slotfactory.SlotFactory;
import dsw.rudok.app.repository.slotfactory.SlotRectangleFactory;
import dsw.rudok.app.repository.slotfactory.SlotTriangleFactory;
import dsw.rudok.app.repository.slots.Slot;
import dsw.rudok.app.repository.slots.SlotType;

import java.awt.event.MouseEvent;

public abstract class State {
    public abstract void mousePressed(MouseEvent e);
    public abstract void mouseReleased(MouseEvent e);
    public abstract void mouseDragged(MouseEvent e );
}
