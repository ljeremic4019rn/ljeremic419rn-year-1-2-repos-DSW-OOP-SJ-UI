package dsw.rudok.app.gui.swing.view.painters;

import dsw.rudok.app.repository.slots.Slot;
import dsw.rudok.app.repository.slots.SlotCircle;

import java.awt.geom.GeneralPath;

public class CirclePainter extends SlotPainter {

    public CirclePainter(Slot slot) {
        super(slot);
        Slot circle = (SlotCircle) slot;
        shape=new GeneralPath();

        ((GeneralPath)shape).moveTo(circle.getPointA().x+circle.getSize().getWidth()/2, circle.getPointA().y);
        ((GeneralPath)shape).quadTo(circle.getPointB().x, circle.getPointB().y,
                circle.getPointB().x, circle.getPointB().y+circle.getSize().getHeight()/2);
        ((GeneralPath)shape).quadTo(circle.getPointC().x, circle.getPointC().y,
                circle.getPointC().x-circle.getSize().getWidth()/2, circle.getPointC().y);
        ((GeneralPath)shape).quadTo(circle.getPointD().x, circle.getPointD().y,
                circle.getPointD().x, circle.getPointD().y-circle.getSize().getHeight()/2);
        ((GeneralPath)shape).quadTo(circle.getPointA().x, circle.getPointA().y,
                circle.getPointA().x+circle.getSize().getWidth()/2, circle.getPointA().y);
    }
}
