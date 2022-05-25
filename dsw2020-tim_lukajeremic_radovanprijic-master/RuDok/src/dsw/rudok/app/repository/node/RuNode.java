package dsw.rudok.app.repository.node;

import dsw.rudok.app.gui.swing.tree.model.RuTreeItem;
import dsw.rudok.app.observer.ActionType;
import dsw.rudok.app.observer.IPublisher;
import dsw.rudok.app.observer.ISubscriber;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class RuNode implements IPublisher, Serializable {
    private String name;
    @ToString.Exclude
    private RuNode parent;
    transient List<ISubscriber> subscribers;

    public RuNode(String name, RuNode parent) {
        this.name = name;
        this.parent = parent;
    }

    public void renameNode(String newName){
        this.name = newName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof RuNode) {
            RuNode otherObj = (RuNode) obj;
            return this.getName() == (otherObj.getName());
        }
        return false;
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
