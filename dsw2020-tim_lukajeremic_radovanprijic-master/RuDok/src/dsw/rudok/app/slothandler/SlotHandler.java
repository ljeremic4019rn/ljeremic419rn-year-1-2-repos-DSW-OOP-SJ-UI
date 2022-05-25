package dsw.rudok.app.slothandler;

import dsw.rudok.app.core.ISlotHandler;
import dsw.rudok.app.observer.ActionType;
import dsw.rudok.app.observer.ISubscriber;
import dsw.rudok.app.repository.Project;
import dsw.rudok.app.repository.slots.Slot;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.atan2;

@NoArgsConstructor
public class SlotHandler implements ISlotHandler {
    List<ISubscriber> subscribers;

    public void handleSlot(ArrayList<Slot> slots, Point pressedPoint, Point draggedPoint, ModificationType modificationType){

        if(modificationType == ModificationType.MOVING){
            for (Slot s : slots){
               double moveX = draggedPoint.getX() - pressedPoint.getX();//pomeraj misa koji cemo da saberemo sa opzicijom slotova
               double moveY = draggedPoint.getY() - pressedPoint.getY();
               s.getPosition().setLocation(s.getPosition().getX() + moveX, s.getPosition().getY() + moveY);
               s.updatePoints(s.getPosition(),s.getSize());
               notifySubscribers(s, ActionType.SLOT_MODIFICATION);
               ((Project)s.getParent().getParent().getParent()).setChanged(true);
            }
        }
        else if(modificationType == ModificationType.SCALING){
            double x = pressedPoint.getX();
            double y = pressedPoint.getY();
            double newSizeX = draggedPoint.x - x; //razmak koji cemo da saberemo sa slotovima
            double newSizeY = draggedPoint.y - y;
            for (Slot s : slots){
               s.getSize().setSize(newSizeX,newSizeY);
               s.updatePoints(s.getPosition(),s.getSize());
               notifySubscribers(s, ActionType.SLOT_MODIFICATION);
               ((Project)s.getParent().getParent().getParent()).setChanged(true);
            }
        }
        else if(modificationType == ModificationType.ROTATION){
            double angle = (atan2(draggedPoint.y - slots.get(0).getRotationPoint().y, draggedPoint.x - slots.get(0).getRotationPoint().x) - //ugao pokreta misa koji sabiramo sa slotovima
                            atan2(pressedPoint.y -slots.get(0).getRotationPoint().y, pressedPoint.x - slots.get(0).getRotationPoint().x));
            for (Slot s : slots){
                s.setAngle(angle);
                notifySubscribers(s, ActionType.SLOT_MODIFICATION);
                ((Project)s.getParent().getParent().getParent()).setChanged(true);
            }
        }

    }

    @Override                             //ovo je point/dimenzija/ugao rotacije
    public void handleSlotCommand(Slot slot, Object modificationAttribute, ModificationType modificationType) {

        if (modificationType == ModificationType.MOVING){
            slot.setPosition((Point) modificationAttribute);
            slot.updatePoints(slot.getPosition(), slot.getSize());
            notifySubscribers(slot, ActionType.SLOT_MODIFICATION);
            ((Project)slot.getParent().getParent().getParent()).setChanged(true);
        }
        else if (modificationType == ModificationType.SCALING){
            slot.setSize((Dimension) modificationAttribute);
            slot.updatePoints(slot.getPosition(), slot.getSize());
            notifySubscribers(slot, ActionType.SLOT_MODIFICATION);
            ((Project)slot.getParent().getParent().getParent()).setChanged(true);
        }
        else if (modificationType == ModificationType.ROTATION){
            slot.setAngle((Double)modificationAttribute);
            notifySubscribers(slot, ActionType.SLOT_MODIFICATION);
            ((Project)slot.getParent().getParent().getParent()).setChanged(true);
        }

    }

    @Override
    public void addSubscriber(ISubscriber sub) {
        if(sub == null)
            return;
        if(this.subscribers ==null)
            this.subscribers = new ArrayList<>();
        if(this.subscribers.contains(sub))
            return;
        this.subscribers.add(sub);
    }

    @Override
    public void removeSubscriber(ISubscriber sub) {
        if(sub == null || this.subscribers == null || !this.subscribers.contains(sub))
            return;
        this.subscribers.remove(sub);
    }

    @Override
    public void notifySubscribers(Object notification, ActionType action) {
        if(notification == null || this.subscribers == null || this.subscribers.isEmpty())
            return;
        for(ISubscriber observer : subscribers){
            observer.update(notification, action);
        }
    }
}
