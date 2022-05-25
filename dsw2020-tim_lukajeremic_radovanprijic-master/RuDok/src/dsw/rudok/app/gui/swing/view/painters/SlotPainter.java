package dsw.rudok.app.gui.swing.view.painters;

import dsw.rudok.app.repository.slots.Slot;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

@Getter
@Setter

public class SlotPainter {
    protected Shape shape;
    private Slot slot;

    public SlotPainter(Slot slot) {
        this.slot = slot;
    }

    public void paint(Graphics2D g, Slot slot){
        if (slot.getIsGreen() == true){
            g.setPaint(new Color(105, 201, 38, 112));
        }
        else {
            g.setPaint(new Color(217, 22, 22, 82));
        }
        g.setStroke(new BasicStroke(2f));
        g.draw(getShape());
        g.fill(getShape());
        g.drawString(slot.getName(), (int)slot.getPosition().getX() + slot.getSize().width/2 - 25, (int)slot.getPosition().getY() + slot.getSize().height/2 );
        g.setStroke(new BasicStroke((float)1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 1f, new float[]{3f, 6f}, 0 ));
    }

    public boolean isElementAt(Point pos){
        return getShape().contains(pos);
    }
}
