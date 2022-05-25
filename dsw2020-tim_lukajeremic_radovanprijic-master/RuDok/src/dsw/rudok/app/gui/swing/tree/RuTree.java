package dsw.rudok.app.gui.swing.tree;

import dsw.rudok.app.repository.*;
import dsw.rudok.app.repository.node.RuNode;
import dsw.rudok.app.repository.slots.Slot;

import javax.print.Doc;
import javax.swing.*;

public interface RuTree { //nama kada neka klasa trazi da dobije Tree, ona zeli konacan tree, tako da cemo napraviti klasu koja sluzi
                        // samo kako bi napravila Tree, i dodelicemo joj ovaj interface

    JTree generateTree(Workspace workspace);
    void addProject(Project project);
    void addDocument(Document document);
    void addSharedDocument(Project project, Document document);
    void addPage(Page page);
    void addSlot(Slot slot);
    void removeProject(Project project);
    void removeDocument(Document document);
    void removePage(Page page);
    void removeSlot(Slot slot);
    void renameNode(String newName);
    void removeFromSharedParents(Document document);
    void renameFromSharedParents (Document document);
    void clearProjects();
    RuNode getSelectedNode();
}
