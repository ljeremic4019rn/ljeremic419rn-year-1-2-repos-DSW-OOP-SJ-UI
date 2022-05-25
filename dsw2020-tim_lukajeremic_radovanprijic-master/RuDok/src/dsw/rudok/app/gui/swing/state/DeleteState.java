package dsw.rudok.app.gui.swing.state;

import dsw.rudok.app.AppCore;
import dsw.rudok.app.command.DeleteSlotCommand;
import dsw.rudok.app.errorhandler.ErrorType;
import dsw.rudok.app.gui.swing.view.observers.PageView;
import dsw.rudok.app.gui.swing.view.painters.SlotPainter;
import dsw.rudok.app.repository.slots.Slot;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.event.MouseEvent;

@Getter
@Setter
public class DeleteState extends State {
    private PageView mediator;

    public DeleteState(PageView mediator) {
        this.mediator = mediator;
    }

    public void mousePressed(MouseEvent e) {
        Point position = e.getPoint();
        if (e.getButton()==MouseEvent.BUTTON1){
            if(mediator.getSlotIndexAtPosition(position)!=-1){
                for(SlotPainter sp: mediator.getSlotPainters()) {
                    if(sp.isElementAt(position)){
                        if(mediator.getPage().getSelectedSlots().contains(sp.getSlot())){
                            mediator.getPage().getSelectedSlots().remove(sp.getSlot());
                            mediator.getPage().setSlotToBeDeleted(sp.getSlot());
                        }
                    }
                }
                if(mediator.getPage().getSlotToBeDeleted() != null) {
                    mediator.getCommandManager().addCommand(new DeleteSlotCommand(mediator.getPage(), mediator.getPage().getSlotToBeDeleted()));
                }
            }
            else{
                AppCore.getInstance().getIErrorHandler().generateError(ErrorType.NO_CLICK_ON_SHAPE);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mediator.getPage().setSlotToBeDeleted(null);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }
}
