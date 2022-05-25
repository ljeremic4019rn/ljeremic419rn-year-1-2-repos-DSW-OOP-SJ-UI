package dsw.rudok.app.gui.swing.controller;

import dsw.rudok.app.gui.swing.tree.model.RuTreeItem;
import dsw.rudok.app.gui.swing.view.MainFrame;
import dsw.rudok.app.gui.swing.view.observers.DocumentView;
import dsw.rudok.app.gui.swing.view.observers.PageView;
import dsw.rudok.app.gui.swing.view.observers.ProjectView;
import dsw.rudok.app.gui.swing.view.painters.SlotPainter;
import dsw.rudok.app.repository.Document;
import dsw.rudok.app.repository.Page;
import dsw.rudok.app.repository.Project;

import java.awt.event.*;

public class TabOpener implements MouseListener {

    public void mouseClicked(MouseEvent e) {

        if (e.getClickCount() == 2 && MainFrame.getInstance().getTree().getSelectedNode() instanceof Project) { //ako smo 2click i ako je na projekat
            MainFrame.getInstance().setOpenFrameCounter(0);//counter za namestanje prozora u tabovima, resetuje se da se ne bi promerali na dole

            RuTreeItem selektovaniProjekat = (RuTreeItem) MainFrame.getInstance().getWorkspaceTree().getLastSelectedPathComponent();//uzmemo na sta smo kliknuli
            ProjectView projectView = new ProjectView((Project) selektovaniProjekat.getNodeModel());


                for (int iproj = 0; iproj < selektovaniProjekat.getChildCount(); iproj++) {//prodjemo kroz sve dokumente
                    RuTreeItem rtiDoc = (RuTreeItem) selektovaniProjekat.getChildAt(iproj);
                    Document document = (Document) rtiDoc.getNodeModel();

                    DocumentView docPaneView = new DocumentView(document);//napravimo docView i stavimo mu parenda za Observer
                    projectView.getDocuments().add(docPaneView);//stavimo ga u doc listu u projectView

                    for (int jdoc = 0; jdoc < selektovaniProjekat.getChildAt(iproj).getChildCount(); jdoc++) {//prodjemo kroz sve stranice u dokumetnu
                        RuTreeItem rtiPage = (RuTreeItem) selektovaniProjekat.getChildAt(iproj).getChildAt(jdoc);
                        Page page = (Page) rtiPage.getNodeModel();

                        String putanja = selektovaniProjekat.toString() + "-" + selektovaniProjekat.getChildAt(iproj).toString() + "-" + selektovaniProjekat.getChildAt(iproj).getChildAt(jdoc).toString();

                        PageView pagePaneView = new PageView(putanja, page);
                        docPaneView.getPages().add(pagePaneView);//stavimo stranicu u listu stranica na dokument view




                    }
                    docPaneView.addPages();//stavimo stranice na dokument
                }
                projectView.addTabs();
                MainFrame.getInstance().setProjectView(projectView);
                MainFrame.getInstance().getSplit().setRightComponent(projectView);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}