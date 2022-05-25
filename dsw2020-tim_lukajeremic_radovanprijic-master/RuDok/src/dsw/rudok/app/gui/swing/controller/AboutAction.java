package dsw.rudok.app.gui.swing.controller;

import com.sun.tools.javac.Main;
import dsw.rudok.app.gui.swing.view.AboutFrame;
import dsw.rudok.app.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class AboutAction extends AbstractRudokAction{

    public AboutAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("images/about.png"));
        putValue(NAME, "About");
        putValue(SHORT_DESCRIPTION, "About");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Frame about = new AboutFrame("About us");
        about.setVisible(true);
    }
}
