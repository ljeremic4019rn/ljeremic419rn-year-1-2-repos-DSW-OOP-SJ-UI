package dsw.rudok.app.repository.slots;

import dsw.rudok.app.repository.node.RuNode;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class SlotCircle extends Slot {

    public SlotCircle(String name, RuNode parent, Dimension size, Point position) {
        super(name, parent, size, position);
    }
}
