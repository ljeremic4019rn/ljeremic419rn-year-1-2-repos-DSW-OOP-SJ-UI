package dsw.rudok.app.gui.swing.view;

import dsw.rudok.app.AppCore;
import dsw.rudok.app.errorhandler.ErrorType;
import dsw.rudok.app.repository.Document;
import dsw.rudok.app.repository.Project;
import dsw.rudok.app.repository.node.RuNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class SharePane extends JFrame {
    private JButton btn;
    private JLabel label;
    private FlowLayout fl = new FlowLayout();

    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screenSize = kit.getScreenSize();

    public SharePane(Document document) {
        this.btn = new JButton("OK");
        this.label = new JLabel("Kliknite na projekat sa kojim zelite da podelite odabrani dokument.");
        this.setLayout(fl);
        add(label);
        add(btn);
        btn.setSize(20,20);
        setTitle("Project selector");
        this.setAlwaysOnTop(true);
        validate();
        this.pack();
        setSize(450,100);
        this.setLocation(screenSize.width/2-100,screenSize.height/2-170);

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (MainFrame.getInstance().getTree().getSelectedNode() instanceof Project) {
                    MainFrame.getInstance().getTree().addSharedDocument((Project) MainFrame.getInstance().getTree().getSelectedNode(), document);
                }
                else{
                    AppCore.getInstance().getIErrorHandler().generateError(ErrorType.NO_PROJECT_SELECTED);
                }
                dispose();
            }
        });
        closeWindow();
    }

    public void closeWindow(){
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) //ovo zatvara novi pop up
            {
                dispose();

            }
        });
    }
}
