package dsw.rudok.app.gui.swing.tree.model;

import com.sun.tools.javac.Main;
import dsw.rudok.app.gui.swing.tree.RuTree;
import dsw.rudok.app.gui.swing.view.MainFrame;
import dsw.rudok.app.observer.ActionType;
import dsw.rudok.app.observer.ISubscriber;
import dsw.rudok.app.repository.Document;
import dsw.rudok.app.repository.Project;
import dsw.rudok.app.repository.node.RuNode;
import dsw.rudok.app.repository.node.RuNodeComposite;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

@Getter
@Setter
public class RuTreeItem extends DefaultMutableTreeNode{ //default je tako da implementujemo ono sto zelimo da prepravimo za nase potrebe
                             //ovo je kompleksnija verziija TreeNode
    //OVA KLASA je ono sto zelimo da ubacimo u nas Tree, i wrappovano je pomozu DefaultMutableTreeNode

    private String name;
    private RuNode nodeModel;

    public RuTreeItem(RuNode nodeModel) {
        this.nodeModel = nodeModel;
        this.name = nodeModel.getName();
    }

    public RuTreeItem(RuNode ruNode, String name) {
        this.name = name;
        this.nodeModel = ruNode;
    }

    @Override //pomocna metoda za pronalazak dece
    public int getIndex(TreeNode node) {
        return findIndexByChild((RuTreeItem)node);
    }



    @Override //pomocna metoda za pronalazak dece
    public TreeNode getChildAt(int childIndex) {
        return findChildByIndex(childIndex);
    }

    @Override
    public int getChildCount() {
        if(nodeModel instanceof RuNodeComposite) //ako je composite (moze da ima children)
            return ((RuNodeComposite) nodeModel).getChildren().size(); //onda  cemo ispisati broj dece
        return 0;//ako nije nista ne vracamo
    }

    @Override
    public boolean getAllowsChildren() { // da li sme da ima decu
        if(nodeModel instanceof RuNodeComposite)
            return true;
        return false;
    }

    @Override
    public boolean isLeaf() { //ako nije composite onda je leaf
        if(nodeModel instanceof RuNodeComposite)
            return false;
        return true;
    }

    @Override
    public Enumeration children() {
        if(nodeModel instanceof RuNodeComposite)
            return (Enumeration) ((RuNodeComposite) nodeModel).getChildren();
        return null;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof RuTreeItem) {
            RuTreeItem otherObj = (RuTreeItem) obj;
            return this.nodeModel.equals(otherObj.nodeModel);
        }
        return false;
    }

    private TreeNode findChildByIndex(int childIndex){

        if(nodeModel instanceof RuNodeComposite){
            RuTreeItem toLookFor = new RuTreeItem(((RuNodeComposite) nodeModel).getChildren().get(childIndex));

            if (super.children == null){
                super.children = new Vector<>();
            }
            super.children.add(toLookFor);


            Iterator childrenIterator = children.iterator();
            TreeNode current;

            while (childrenIterator.hasNext()){
                current = (TreeNode) childrenIterator.next();
                if (current.equals(toLookFor))
                    return current;
            }
        }

        return null;
    }

    private int findIndexByChild(RuTreeItem node){

        if(this.nodeModel instanceof RuNodeComposite){
            return  ((RuNodeComposite) this.nodeModel).getChildren().indexOf(node.getNodeModel());
        }
        return -1;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.nodeModel.setName(name);
    }
}
