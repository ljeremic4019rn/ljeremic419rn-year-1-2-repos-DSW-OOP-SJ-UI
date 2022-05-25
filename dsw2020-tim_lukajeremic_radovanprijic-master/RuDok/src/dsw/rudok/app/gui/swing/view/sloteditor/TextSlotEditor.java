package dsw.rudok.app.gui.swing.view.sloteditor;

import dsw.rudok.app.AppCore;
import dsw.rudok.app.gui.swing.controller.filters.SlotTextFileFilter;
import dsw.rudok.app.gui.swing.view.MainFrame;
import dsw.rudok.app.repository.slots.Slot;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class TextSlotEditor extends JFrame {
    private FlowLayout fl = new FlowLayout();
    private JTextPane textPane = new JTextPane();
    private JButton sacuvajBtn = new JButton("Save text");
    private JButton ucitajBtn = new JButton("Import text");
    private JButton defaultTextBtn = new JButton("Default text");
    private JButton boldTextBtn = new JButton("Bold text");
    private JButton italicTextBtn = new JButton("Italic text");
    private JButton underlineTextBtn = new JButton("Underlined text");
    private JSplitPane splitPane;
    JTable table = new JTable();

    public TextSlotEditor(Slot slot) {
        table.add(defaultTextBtn);
        table.add(boldTextBtn);
        table.add(italicTextBtn);
        table.add(underlineTextBtn);
        table.add(sacuvajBtn);
        table.add(ucitajBtn);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth / 3, screenHeight / 3);
        setLocationRelativeTo(null);

        textPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        table.setLayout(fl);

        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,textPane,table);
        splitPane.setOneTouchExpandable(false);
        splitPane.setDividerLocation(250);
        splitPane.setResizeWeight(1);
        add(splitPane);

        validate();
        this.pack();
        setSize(450,370);

        loadOldText(slot,textPane);

         sacuvajBtn.addActionListener(new ActionListener() {//cuvamo text u file na memoriji
             @Override
             public void actionPerformed(ActionEvent e) {
                String text = textPane.getText();
                JFileChooser jfc = new JFileChooser();
                jfc.setFileFilter(new SlotTextFileFilter());
                File slotFile;

                if(jfc.showSaveDialog(MainFrame.getInstance())== JFileChooser.APPROVE_OPTION){
                    slotFile=jfc.getSelectedFile();
                }
                else{
                    return;
                }
                AppCore.getInstance().getISerialization().serializeTextSlot(slot,text,slotFile);
             }
         });

         ucitajBtn.addActionListener(new ActionListener() {//ucitavamo text iz fila sa memorije
             @Override
             public void actionPerformed(ActionEvent e) {
                 JFileChooser jfc = new JFileChooser();//picker
                 jfc.setFileFilter(new SlotTextFileFilter());

                 if(jfc.showOpenDialog(MainFrame.getInstance())==JFileChooser.APPROVE_OPTION){
                     AppCore.getInstance().getISerialization().readTextSlot(jfc.getSelectedFile(), slot, textPane, TextSlotFileType.TYPE_ONE);
                 }
             }
         });

        defaultTextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleAttributeSet attributeSet = new SimpleAttributeSet();
                StyleConstants.setBold(attributeSet, false);
                StyleConstants.setItalic(attributeSet, false);
                StyleConstants.setUnderline(attributeSet, false);
                textPane.setCharacterAttributes(attributeSet, true);
            }
        });

        boldTextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleAttributeSet attributeSet = new SimpleAttributeSet();
                StyleConstants.setBold(attributeSet, true);
                textPane.setCharacterAttributes(attributeSet, true);
            }
        });

        italicTextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleAttributeSet attributeSet = new SimpleAttributeSet();
                StyleConstants.setItalic(attributeSet, true);
                textPane.setCharacterAttributes(attributeSet, true);
            }
        });

        underlineTextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleAttributeSet attributeSet = new SimpleAttributeSet();
                StyleConstants.setUnderline(attributeSet, true);
                textPane.setCharacterAttributes(attributeSet, true);
            }
        });
    }//constructor

    public void loadOldText (Slot slot, JTextPane textPane){
        if (slot.getSlotFile() != null){
            AppCore.getInstance().getISerialization().readTextSlot(null, slot, textPane, TextSlotFileType.TYPE_TWO);
        }
    }
}
