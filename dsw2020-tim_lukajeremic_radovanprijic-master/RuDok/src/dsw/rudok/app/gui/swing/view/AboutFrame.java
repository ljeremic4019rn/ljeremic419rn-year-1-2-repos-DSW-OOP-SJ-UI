package dsw.rudok.app.gui.swing.view;

import dsw.rudok.app.gui.swing.controller.AbstractRudokAction;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AboutFrame extends JFrame {

   // private JPanel panel;
    private FlowLayout fl = new FlowLayout();
    private JLabel ime1;
    private JLabel ime2;
    private JLabel index1;
    private JLabel index2;
    private ImageIcon image1;
    private ImageIcon image2;
    private JLabel imageLable1;
    private JLabel imageLable2;

    public AboutFrame(String title){
        GridLayout grid = new GridLayout(2,2,10,10);
        ime1 = new JLabel("Luka Jeremic RN40/19");
        ime2 = new JLabel("Radovan Prijic RN36/19");
        setLayout(fl);
        fl.setHgap(15);
        setTitle(title);
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        image1 = new ImageIcon(getClass().getResource("imm/radovan.png"));
        imageLable1 = new JLabel(image1);
        image2 = new ImageIcon(getClass().getResource("imm/luka.png"));
        imageLable2 = new JLabel(image2);
        add(imageLable1);
        add(imageLable2);
        add(ime2);
        add(ime1);
        validate();
        this.pack();
        setSize(450,320);
        closeWindow();
    }

    public void closeWindow(){
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){ //ovo zatvara novi pop up
                dispose();
            }
        });
    }
}
