package dsw.rudok.app.command;

import dsw.rudok.app.gui.swing.view.observers.PageView;
import dsw.rudok.app.repository.Page;
import dsw.rudok.app.repository.slotfactory.SlotCircleFactory;
import dsw.rudok.app.repository.slotfactory.SlotFactory;
import dsw.rudok.app.repository.slotfactory.SlotRectangleFactory;
import dsw.rudok.app.repository.slotfactory.SlotTriangleFactory;
import dsw.rudok.app.repository.slots.Slot;
import dsw.rudok.app.repository.slots.SlotType;

import java.awt.*;
import java.awt.geom.Point2D;

public class NewSlotCommand extends AbstractCommand{
    private Page page;
    private Slot slot = null;
    private Point position;
    private SlotType slotType;
    private SlotFactory slotFactory;
    private static int circleCounter = 1;
    private static int rectangleCounter = 1;
    private static int triangleCounter = 1;

    public NewSlotCommand(Page page, Point position, SlotType slotType) {
        this.page = page;
        this.position = position;
        this.slotType = slotType;
    }

    @Override
    public void doCommand() {
        if (slot == null) {
            if (slotType == SlotType.CIRCLE) {
                slotFactory = returnSlotFactory(SlotType.CIRCLE);
                slot = slotFactory.createSlot("Circle " + circleCounter++, page, new Dimension(50, 50), position);
            } else if (slotType == SlotType.RECTANGLE) {
                slotFactory = returnSlotFactory(SlotType.RECTANGLE);
                slot = slotFactory.createSlot("Rectangle " + rectangleCounter++, page, new Dimension(100, 50), position);
            } else if (slotType == SlotType.TRIANGLE) {
                slotFactory = returnSlotFactory(SlotType.TRIANGLE);
                slot = slotFactory.createSlot("Triangle " + triangleCounter++, page, new Dimension(60, 60), position);
            }
        }
        page.addChild(slot);
        if(slot.getIsGreen() == true){
            slot.setIsGreen(false);
        }
    }

    @Override
    public void undoCommand() {
        page.removeChild(slot);
    }

    public SlotFactory returnSlotFactory(SlotType type){
        if(type == SlotType.CIRCLE){
            return new SlotCircleFactory();
        }
        else if(type == SlotType.RECTANGLE){
            return new SlotRectangleFactory();
        }
        else if(type == SlotType.TRIANGLE){
            return new SlotTriangleFactory();
        }
        return null;
    }
}
