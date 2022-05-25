package dsw.rudok.app.gui.swing.view.observers;

import dsw.rudok.app.gui.swing.view.MainFrame;
import dsw.rudok.app.observer.ActionType;
import dsw.rudok.app.observer.ISubscriber;
import dsw.rudok.app.repository.Document;
import dsw.rudok.app.repository.Page;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.util.ArrayList;

@Getter
@Setter
public class DocumentView extends JDesktopPane implements ISubscriber {//drzi sve projekte

    ArrayList<PageView> pages;
    private Document document;
    private String name;

    public DocumentView(Document document) {
        this.document = document;
        this.name = document.getName();
        document.addSubscriber(this);
        pages = new ArrayList<>();
    }

    public void update(Object notification, ActionType action) {

        if (action == ActionType.PAGE_ADDITION) {
            String parent1 = ((Page) notification).getParent().getName();
            String parent2 = ((Page) notification).getParent().getParent().getName();
            String putanja =  parent2 + "-" + parent1 + "-" + ((Page) notification).getName();
            PageView pv = new PageView(putanja, (Page) notification);
            pages.add(pv);
            this.add(pv);//treba sa this.add jer inace se ne dodaje pravilo u listu i ne pojavljuje se odmah na ekranu
            revalidate();
            repaint();
        }

        else if (action == ActionType.PAGE_REMOVAL) {
            int j = 0;
            PageView tmpPage = null;

            for (PageView p : pages) {
                if (p.getName() == ((Page) notification).getName()) {
                    p.getLabel().setText(""); //nismo mogli da sklonimo lagel tako da smo mu ubili text, isti efekat :)
                    tmpPage = p;
                }
            }
            pages.remove(tmpPage);//moramo van for jer inace ne radi
            this.remove(tmpPage);//this.remove da bi sklonio objekat iz liste(prozor) i time se odmah skloni prozor sa ekrana
            revalidate();
            repaint();
        }

        else if (action == ActionType.DOCUMENT_RENAMING) {
            String newName = ((Document) notification).getName();

            for (int i = 0; i < MainFrame.getInstance().getProjectView().getTabCount(); i++) {
                if (this.name.equals(MainFrame.getInstance().getProjectView().getComponentAt(i).getName())) {//nadjemo tab koji nam treba preko imena

                    MainFrame.getInstance().getProjectView().setTitleAt(i, newName);//promenimo mu ime
                }
            }

            for (PageView p : pages) {
                String oldLabel = p.getLabel().getText();//iteriramo kroz sve pageove i promenimo ima labele
                String newLabel;
                String splitPath[] = oldLabel.split("-");
                newLabel = splitPath[0] + "-" + ((Document) notification).getName() + "-" + splitPath[2];
                p.getLabel().setText(newLabel);
            }

            this.name = ((Document) notification).getName(); // moramo da promenimo ime da bi mogli da izbrisemo tab ako je promenjeno ime
        }
    }

    public void addPages() {
        for (PageView p : pages) {
            this.add(p);
        }
    }
}
