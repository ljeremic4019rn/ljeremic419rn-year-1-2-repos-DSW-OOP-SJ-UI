package dsw.rudok.app.gui.swing.tree.controller;

import dsw.rudok.app.gui.swing.tree.model.RuTreeItem;
import dsw.rudok.app.repository.Project;
import dsw.rudok.app.repository.Workspace;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;


public class RuTreeCellEditor extends DefaultTreeCellEditor implements ActionListener { //implementira ActionListener jer zelimo na 3click da se aktivira editovanje
        //ova klasa sluzi da editujemo ime listova/projekata, u ovom slucaju mozemo da editujemo samo workspace/rootNode

    private Object clickedOn =null;
    private JTextField edit=null;

    public RuTreeCellEditor(JTree arg0, DefaultTreeCellRenderer arg1) {
        super(arg0, arg1);
    }

                    //OVAJ JE NAJBITNIJI DEO OVOGA!!!!!
    public Component getTreeCellEditorComponent(JTree arg0, Object arg1, boolean arg2, boolean arg3, boolean arg4, int arg5) {
                                            // koje stablo | koju komponentu
        //super.getTreeCellEditorComponent(arg0,arg1,arg2,arg3,arg4,arg5);
        clickedOn =arg1;//komponenta koju zelimo
        edit=new JTextField(arg1.toString());// text koji zelimo
        edit.addActionListener(this);
        return edit;
    }

    public boolean isCellEditable(EventObject arg0) { //ovo proverava da li smo kliknuli 3 puta
        if (arg0 instanceof MouseEvent)
            if (((MouseEvent)arg0).getClickCount()==10){
                return true;
            }
        return false;

    }

    public void actionPerformed(ActionEvent e){
        if (!(clickedOn instanceof RuTreeItem)) //ako nije deo od Tree ubi
            return;
        RuTreeItem clicked = (RuTreeItem) clickedOn;//uzemmo na sta smo kliknuli

        clicked.setName(e.getActionCommand()); // mislim da e.getAction samo uzima ono sto si napisao tokom editovanja. not sure tho
        clicked.getNodeModel().setName(e.getActionCommand());// setuje name na gui deo treea


    }



}
