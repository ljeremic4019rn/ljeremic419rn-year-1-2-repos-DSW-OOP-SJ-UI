package dsw.rudok.app.repository;

import dsw.rudok.app.AppCore;
import dsw.rudok.app.gui.swing.view.MainFrame;
import dsw.rudok.app.observer.ActionType;
import dsw.rudok.app.observer.IPublisher;
import dsw.rudok.app.observer.ISubscriber;
import dsw.rudok.app.repository.node.RuNode;
import dsw.rudok.app.repository.node.RuNodeComposite;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Project extends RuNodeComposite {
    private transient boolean changed;
    private File projectFile;

    public Project(String name, RuNode parent) {
        super(name, parent);
        this.changed = false;
        this.projectFile = null;
    }

    @Override
    public void addChild(RuNode child) {
        if (child != null && child instanceof Document) {
            Document document = (Document) child;
            if (!this.getChildren().contains(document)) {
                this.getChildren().add(document);
            }
            notifySubscribers(child, ActionType.DOCUMENT_ADDITION);
            this.setChanged(true);
        }
    }

    @Override
    public void removeChild(RuNode child) {
        if (child != null && child instanceof Document) {
            Document document = (Document) child;
            if (this.getChildren().contains(document)) {
                this.getChildren().remove(document);
            }
            notifySubscribers(child, ActionType.DOCUMENT_REMOVAL);
            this.setChanged(true);
        }
    }

    @Override
    public void renameNode(String newName) {
        this.setName(newName);
        notifySubscribers(this, ActionType.PROJECT_RENAMING);
        this.setChanged(true);
    }

    public void setChanged(boolean changed) {
        if (this.changed!=changed){
            this.changed=changed;
        }
    }
}
