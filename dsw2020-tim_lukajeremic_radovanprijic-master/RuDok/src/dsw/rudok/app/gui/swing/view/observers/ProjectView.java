package dsw.rudok.app.gui.swing.view.observers;

import dsw.rudok.app.gui.swing.controller.TabOpener;
import dsw.rudok.app.gui.swing.view.MainFrame;
import dsw.rudok.app.observer.ActionType;
import dsw.rudok.app.observer.ISubscriber;
import dsw.rudok.app.repository.Document;
import dsw.rudok.app.repository.Page;
import dsw.rudok.app.repository.Project;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;


@Getter
@Setter
public class ProjectView extends JTabbedPane implements ISubscriber { //drzi sve dekumente
    private Project project;
    ArrayList<DocumentView> documents;

    public  ProjectView(){

    }

    public ProjectView(Project project){
        documents = new ArrayList<>();
       this.project = project;
       project.addSubscriber(this);

    }

    public void update(Object notification, ActionType action){

        if(action == ActionType.DOCUMENT_ADDITION){
            DocumentView dv = new DocumentView((Document) notification);
            documents.add(dv);
            this.addTabs();
        }

        else if(action == ActionType.DOCUMENT_REMOVAL) {
            DocumentView tmpDoc = null;

            for (DocumentView d : documents) {
                if (d.getName() == ((Document) notification).getName()) {
                    String removeTab = ((Document) notification).getName();

                    for (int i = 0; i < MainFrame.getInstance().getProjectView().getTabCount(); i++) {
                        if (removeTab == (MainFrame.getInstance().getProjectView().getComponentAt(i).getName())) {
                           // System.out.println("sklonjen tab");
                            MainFrame.getInstance().getProjectView().remove(i);
                            tmpDoc = d;
                        }
                    }
                }
            }
            documents.remove(tmpDoc);//moramo van for jer inace ne radi
        }

        else if(action == ActionType.PROJECT_RENAMING){
            for (DocumentView d: documents){//iteriramo kroz sve dokumente i njihove pageove i promenimo lavele od stranica
                for(PageView p: d.getPages()){
                    String oldLabel = p.getLabel().getText();
                    String newLabel;
                    String splitPath [] = oldLabel.split("-");
                    newLabel = ((Project) notification).getName() + "-" + splitPath[1] + "-" + splitPath[2];
                    p.getLabel().setText(newLabel);
                }
            }

        }
    }

    public void addTabs(){
        for(DocumentView d: documents){
            this.add(d);
        }
    }
}
