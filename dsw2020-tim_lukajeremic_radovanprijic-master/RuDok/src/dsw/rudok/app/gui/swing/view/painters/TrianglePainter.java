package dsw.rudok.app.gui.swing.view.painters;

import dsw.rudok.app.repository.slots.Slot;
import dsw.rudok.app.repository.slots.SlotRectangle;
import dsw.rudok.app.repository.slots.SlotTriangle;

import java.awt.geom.GeneralPath;

public class TrianglePainter extends SlotPainter{

    public TrianglePainter(Slot slot) {
        super(slot);
        Slot triangle = slot;
        shape = new GeneralPath();

        ((GeneralPath)shape).moveTo(triangle.getPointA().x+(triangle.getPointB().x-triangle.getPointA().x)/2, triangle.getPointA().y+(triangle.getPointB().y-triangle.getPointA().y)/2);
        ((GeneralPath)shape).lineTo(triangle.getPointC().x, triangle.getPointC().y);
        ((GeneralPath)shape).lineTo(triangle.getPointD().x, triangle.getPointD().y);
        ((GeneralPath)shape).closePath();
    }
}
