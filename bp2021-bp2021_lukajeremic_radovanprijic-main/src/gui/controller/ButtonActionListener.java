package gui.controller;

import appcore.AppCore;
import compiler.CompilerImplementation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonActionListener implements ActionListener {
        JTextArea jtx;

    public ButtonActionListener(JTextArea jtx) {
        this.jtx = jtx;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AppCore.getInstance().getValidator().validateQuery(jtx.getText());
    }
}
