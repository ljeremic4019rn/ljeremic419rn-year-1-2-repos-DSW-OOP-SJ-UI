package dsw.rudok.app.gui.swing.tree.view;

import com.sun.tools.javac.Main;
import dsw.rudok.app.gui.swing.tree.RuTree;
import dsw.rudok.app.gui.swing.tree.model.RuTreeItem;
import dsw.rudok.app.gui.swing.view.MainFrame;
import dsw.rudok.app.repository.*;
import dsw.rudok.app.repository.node.RuNode;
import dsw.rudok.app.repository.node.RuNodeComposite;
import dsw.rudok.app.repository.slots.Slot;

import javax.print.Doc;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ListIterator;


public class RuTreeImplementation implements RuTree { //ova klasa generise nas Tree, ono sto cemo da stavimo na ScrollPane u GUI

    private RuTreeView treeView;
    private DefaultTreeModel treeModel;


    @Override
    public JTree generateTree(Workspace workspace) {    //prosledjujemo mu workspace/root
        RuTreeItem root = new RuTreeItem(workspace);    //napravimo wrapper za taj workspace, tako da moze JTree da ga prikaze
        treeModel = new DefaultTreeModel(root);         // ovo je default model za Tree, samo koristis od jave, PROSLEDIS MU SAMO ROOT
        treeView = new RuTreeView(treeModel);            // samo prosledis treeModel ^^^
        return treeView;
    }

    @Override
    public void addProject(Project project) {
        RuNode nodeModel = ((RuTreeItem) treeModel.getRoot()).getNodeModel();//dodajvanje projekta moze direktno na root/workspace tako da koristimo drugaciji call
        ((RuTreeItem) treeModel.getRoot()).add(new RuTreeItem(project));
        ((Workspace) nodeModel).addChild(project);
        SwingUtilities.updateComponentTreeUI(treeView);
    }

    @Override
    public void addDocument(Document document) {
        RuTreeItem rti = new RuTreeItem(document);
        document.setOriginalRTI(rti);

        ((RuTreeItem) treeView.getLastSelectedPathComponent()).add(rti);//uzimamo na sta smo kliknuli, i na to dodajemo novi Node
        Project project = (Project) getSelectedNode();
        project.addChild(document);
        SwingUtilities.updateComponentTreeUI(treeView);
    }


    @Override
    public void addSharedDocument(Project selectedProject, Document document) {
        String oldName = document.getName();
        RuTreeItem rti = new RuTreeItem(document);
        rti.setName(document.getName() + " copy");//oldName sluzi da kada pravimo sharedDoc ne promenimo ime originalnog
        document.setName(oldName);

        document.setIsShared(true);//stavljamo da smo podelili doc

        ((RuTreeItem) treeView.getLastSelectedPathComponent()).add(rti); //pravimo novi RuTreeItem(originalnog doc) i stavljamo ga u GUI JTree
        Project project = (Project) getSelectedNode();
        project.addChild(document);//addujemo stari doc u novi projekat

        document.getSharedProjects().add(selectedProject);//stavljamo projekat u listu projekada u kojima se ovaj doc nalazi
        document.getSharedRuTreeItems().add(rti); //dodajemo novi RuTreeItem u listu itema, koja sluzi da renamujemo podeljene

        SwingUtilities.updateComponentTreeUI(treeView);
    }


    @Override
    public void addPage(Page page) {
        ((RuTreeItem) treeView.getLastSelectedPathComponent()).add(new RuTreeItem(page));//uzimamo na sta smo kliknuli, i na to dodajemo novi Node
        Document document = (Document) getSelectedNode();
        document.addChild(page);
        SwingUtilities.updateComponentTreeUI(treeView);
    }

    @Override
    public void addSlot(Slot slot) {
        ((RuTreeItem) treeView.getLastSelectedPathComponent()).add(new RuTreeItem(slot));//uzimamo na sta smo kliknuli, i na to dodajemo novi Node
        Page page = (Page) getSelectedNode();
        page.addChild(slot);
        SwingUtilities.updateComponentTreeUI(treeView);
    }

    @Override
    public void removeProject(Project project){
        RuNodeComposite parent = (RuNodeComposite) project.getParent();
        parent.removeChild(project);
        SwingUtilities.updateComponentTreeUI(treeView);
    }

    @Override
    public void removeDocument(Document document){
        RuNodeComposite parent = (RuNodeComposite) document.getParent();
        parent.removeChild(document);
        removeFromSharedParents(document);//uklanja doc iz projekata u koje je sharova
        SwingUtilities.updateComponentTreeUI(treeView);
    }

    @Override
    public void removePage(Page page){
        RuNodeComposite parent = (RuNodeComposite) page.getParent();
        parent.removeChild(page);
        SwingUtilities.updateComponentTreeUI(treeView);
    }

    @Override
    public void removeSlot(Slot slot){
        RuNodeComposite parent = (RuNodeComposite) slot.getParent();
        parent.removeChild(slot);
        SwingUtilities.updateComponentTreeUI(treeView);
    }

    @Override
    public void renameNode(String newName){
        RuNode node = getSelectedNode();
        node.renameNode(newName);
        RuTreeItem treeItem = (RuTreeItem) treeView.getLastSelectedPathComponent();
        treeItem.setName(newName);
        if(node instanceof Document){
        renameFromSharedParents((Document) node);//renamujemo sharovane
        }
        SwingUtilities.updateComponentTreeUI(treeView);

    }

    @Override
    public RuNode getSelectedNode(){
        if ((treeView.getLastSelectedPathComponent()) == null) {
            return null;
        }
        return ((RuTreeItem)treeView.getLastSelectedPathComponent()).getNodeModel();//ovo nam daje na koji Node smo kliknuli u tree

    }

    @Override
    public void removeFromSharedParents(Document document){//prolazi kroz listu svih projekata u kojima se doc nalazi, i brise taj doc iz njih
        for (Project p : document.getSharedProjects()){
            p.removeChild(document);
        }
    }

    @Override
    public void renameFromSharedParents (Document document){
        document.getOriginalRTI().setName(document.getName());//menjamo original odvojeno od kopija, zbog "copy" odatka

        for (RuTreeItem rti : document.getSharedRuTreeItems()){

            if (document.getName().contains(" copy")){//ako vec ima copy u imenu samo promeni
                rti.setName(document.getName());
            }
            else {
                rti.setName(document.getName() + " copy");//ako nema copy u imenu ododaj
            }
        }
    }
    @Override
    public void clearProjects(){
        ListIterator<RuNode> it = MainFrame.getInstance().getDocumentRepository().getWorkspace().getChildren().listIterator();
        while(it.hasNext()){
            MainFrame.getInstance().getTree().removeProject((Project) MainFrame.getInstance().getDocumentRepository().
                    getWorkspace().getChildren().get(0));
        }
    }

}
