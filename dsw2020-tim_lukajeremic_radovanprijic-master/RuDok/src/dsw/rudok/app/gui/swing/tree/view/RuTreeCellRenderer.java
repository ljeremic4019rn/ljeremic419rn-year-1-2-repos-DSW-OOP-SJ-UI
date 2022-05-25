package dsw.rudok.app.gui.swing.tree.view;

import dsw.rudok.app.gui.swing.tree.model.RuTreeItem;
import dsw.rudok.app.repository.*;
import dsw.rudok.app.repository.slots.Slot;

import javax.print.Doc;
import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.net.URL;

public class RuTreeCellRenderer extends DefaultTreeCellRenderer { // ova klasa izcrtava nas Tree na GUI

        public RuTreeCellRenderer() {
        }

        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            //ovo mu prosledjujes da bi ZNAO STA DA ISCRTA

            super.getTreeCellRendererComponent(tree, value, sel,expanded, leaf, row, hasFocus);//ovo je default iscrtavanje ovih komponenata

            if (((RuTreeItem)value).getNodeModel() instanceof Workspace) { //ovo je specifiranje ikonice koju zelimo da iskoristimo
                URL imageURL = getClass().getResource("images/workspaceImg.png");
                Icon icon = null;
                if (imageURL != null)
                    icon = new ImageIcon(imageURL);
                setIcon(icon);

            } else if (((RuTreeItem)value).getNodeModel() instanceof Project) { //ovo je specifiranje ikonice koju zelimo da iskoristimo
                URL imageURL = getClass().getResource("images/projectImg.png");
                Icon icon = null;
                if (imageURL != null)
                    icon = new ImageIcon(imageURL);
                setIcon(icon);
            }
              else if (((RuTreeItem)value).getNodeModel() instanceof Document) { //ovo je specifiranje ikonice koju zelimo da iskoristimo
                Document sc = (Document) ((RuTreeItem)value).getNodeModel();
                if ( sc.getIsShared() == true){
                    URL imageURL = getClass().getResource("images/docOrange.png");
                    Icon icon = null;
                    if (imageURL != null)
                        icon = new ImageIcon(imageURL);
                    setIcon(icon);
                }
                else{
                    URL imageURL = getClass().getResource("images/documentImg.png");
                    Icon icon = null;
                    if (imageURL != null)
                        icon = new ImageIcon(imageURL);
                    setIcon(icon);
                }


            }
             else if (((RuTreeItem)value).getNodeModel() instanceof Page) { //ovo je specifiranje ikonice koju zelimo da iskoristimo
                URL imageURL = getClass().getResource("images/pageImg.png");
                Icon icon = null;
                if (imageURL != null)
                    icon = new ImageIcon(imageURL);
                setIcon(icon);
            }

            else if (((RuTreeItem)value).getNodeModel() instanceof Slot) { //ovo je specifiranje ikonice koju zelimo da iskoristimo
                URL imageURL = getClass().getResource("images/slotImg.png");
                Icon icon = null;
                if (imageURL != null)
                    icon = new ImageIcon(imageURL);
                setIcon(icon);
            }
            return this;
        }

}


