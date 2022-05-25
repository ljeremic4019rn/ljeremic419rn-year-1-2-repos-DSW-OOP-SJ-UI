package dsw.rudok.app.gui.swing.view.painters;

import dsw.rudok.app.repository.slots.Slot;
import dsw.rudok.app.repository.slots.SlotRectangle;

import java.awt.geom.GeneralPath;


public class RectanglePainter extends SlotPainter{

    public RectanglePainter(Slot slot) {
        super(slot);
        Slot rectangle = slot;
        shape = new GeneralPath();

        ((GeneralPath)shape).moveTo(rectangle.getPointA().x,rectangle.getPointA().y);
        ((GeneralPath)shape).lineTo(rectangle.getPointB().x, rectangle.getPointB().y);
        ((GeneralPath)shape).lineTo(rectangle.getPointC().x, rectangle.getPointC().y);
        ((GeneralPath)shape).lineTo(rectangle.getPointD().x, rectangle.getPointD().y);
        ((GeneralPath)shape).closePath();
    }
}
