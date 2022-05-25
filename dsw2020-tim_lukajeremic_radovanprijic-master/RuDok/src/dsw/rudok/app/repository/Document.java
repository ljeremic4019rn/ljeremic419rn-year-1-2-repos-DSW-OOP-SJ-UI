package dsw.rudok.app.repository;

import dsw.rudok.app.gui.swing.tree.model.RuTreeItem;
import dsw.rudok.app.observer.ActionType;
import dsw.rudok.app.observer.IPublisher;
import dsw.rudok.app.observer.ISubscriber;
import dsw.rudok.app.repository.node.RuNode;
import dsw.rudok.app.repository.node.RuNodeComposite;
import lombok.Getter;
import lombok.Setter;

import java.rmi.ServerError;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Document extends RuNodeComposite{
    private ArrayList <Project> sharedProjects;//koristi se da bi izbrsali sharovane doc, time sto ovde pristupamo deci projekata u koje smo sharovali
    private RuTreeItem originalRTI;//sluzi pri rename, da ne bi promenili ime originala kada menjamo kopije
    private ArrayList <RuTreeItem> sharedRuTreeItems; //sluzi da renameujemo sharovane doc, time sto renamujemo RuTreeIteme a ne originalni doc
    private Boolean isShared; //da bi detektovali da li je doc sharovan

    public Document(String name, RuNode parent) {
        super(name, parent);
        this.sharedProjects = new ArrayList<>();
        this.sharedRuTreeItems = new ArrayList<>();
        this.isShared = false;
    }

    @Override
    public void addChild(RuNode child) {
        if (child != null &&  child instanceof Page){
            Page page = (Page) child;
            if (!this.getChildren().contains(page)){
                this.getChildren().add(page);
            }
            notifySubscribers(child, ActionType.PAGE_ADDITION);
            ((Project)this.getParent()).setChanged(true);
        }
    }

    @Override
    public void removeChild(RuNode child) {
        if (child != null && child instanceof Page) {
            Page page = (Page) child;
            if (this.getChildren().contains(page)) {
                this.getChildren().remove(page);
            }
            notifySubscribers(child, ActionType.PAGE_REMOVAL);
            ((Project)this.getParent()).setChanged(true);
        }
    }

    @Override
    public void renameNode(String newName){
        this.setName(newName);
        notifySubscribers(this, ActionType.DOCUMENT_RENAMING);
        ((Project)this.getParent()).setChanged(true);
    }
}
