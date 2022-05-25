package dsw.rudok.app.repository;

import dsw.rudok.app.repository.node.RuNode;
import dsw.rudok.app.repository.node.RuNodeComposite;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Workspace extends RuNodeComposite {
    private File workspaceFile;

    public Workspace(String name) {
        super(name, null);
        this.workspaceFile = null;
    }

    @Override
    public void addChild(RuNode child) {
       if (child != null &&  child instanceof Project){
            Project project = (Project) child;
            if (!this.getChildren().contains(project)){
                this.getChildren().add(project);
            }
        }
    }

    @Override
    public void removeChild(RuNode child) {
        if (child != null && child instanceof Project) {
            Project project = (Project) child;
            if (this.getChildren().contains(project)) {
                this.getChildren().remove(project);
            }
        }
    }
}
