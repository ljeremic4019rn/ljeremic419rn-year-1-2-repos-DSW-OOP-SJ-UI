package dsw.rudok.app.gui.swing.tree.controller;

import com.sun.tools.javac.Main;
import dsw.rudok.app.gui.swing.tree.model.RuTreeItem;
import dsw.rudok.app.gui.swing.view.MainFrame;
import dsw.rudok.app.repository.node.RuNode;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

public class RuTreeSelectionListener implements TreeSelectionListener { // ova klasa je da vidi na koji node si kliknuo u Tree
                                                                        //ovo se implementuje u RuTreeView

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        TreePath path = e.getPath(); //nadje path do Noda na koji si kluknuo
        RuTreeItem treeItemSelected = (RuTreeItem)path.getLastPathComponent(); //uzme poslednji node na pathu na koji si kluknuo
        System.out.println("Selektovan cvor:"+ treeItemSelected.getName()); //kaze koji je to poslednji node (projekat 50)
        System.out.println("getPath: "+e.getPath()); // i kaze na kom pathu je (workspace, projekat 50)

        MainFrame.getInstance().setNumBull(0);
    }

    public TreePath giveTreePath(){
        //TreePath path = e.getPath();
        return null;
    }


}


