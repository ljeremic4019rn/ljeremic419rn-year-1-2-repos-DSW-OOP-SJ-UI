package dsw.rudok.app.gui.swing.state;

import dsw.rudok.app.AppCore;
import dsw.rudok.app.command.NewSlotCommand;
import dsw.rudok.app.gui.swing.view.MainFrame;
import dsw.rudok.app.gui.swing.view.observers.PageView;
import dsw.rudok.app.repository.Page;
import dsw.rudok.app.repository.slots.Slot;
import dsw.rudok.app.repository.slots.SlotType;

import java.awt.*;
import java.awt.event.MouseEvent;

public class RectangleState extends State{
    private PageView mediator;

    public RectangleState(PageView mediator) {
        this.mediator = mediator;
    }

    public void mousePressed(MouseEvent e) {
        Point position = e.getPoint();
        if (e.getButton() == MouseEvent.BUTTON1){
            if(mediator.getSlotIndexAtPosition(position)==-1){
                mediator.getCommandManager().addCommand(new NewSlotCommand(mediator.getPage(), position, SlotType.RECTANGLE));
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }
}
