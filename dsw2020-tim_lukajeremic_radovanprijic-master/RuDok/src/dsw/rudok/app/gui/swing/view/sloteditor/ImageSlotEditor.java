package dsw.rudok.app.gui.swing.view.sloteditor;

import dsw.rudok.app.AppCore;
import dsw.rudok.app.gui.swing.controller.filters.SlotImageFileFilter;
import dsw.rudok.app.gui.swing.view.MainFrame;
import dsw.rudok.app.repository.slots.Slot;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageSlotEditor extends JFrame {
    private FlowLayout fl = new FlowLayout();
    private JButton sacuvajBtn = new JButton("Save image");
    private JButton ucitajBtn = new JButton("Import image");
    private JSplitPane splitPane;
    private JPanel panel = new JPanel();
    private JTable table = new JTable();
    private BufferedImage image;
    private JLabel  picLabel;
    private File imageFile = null;

    public ImageSlotEditor(Slot slot){
        table.add(sacuvajBtn);
        table.add(ucitajBtn);
        table.setLayout(fl);

        if (slot.getSlotFile() != null) {//ucitavanje stare slike
            try {
                image = ImageIO.read(new File(slot.getSlotFile().toString()));
                picLabel = new JLabel(new ImageIcon(image));
                panel.add(picLabel);
            } catch (IOException e3) {
                System.err.println(e3);
            }
        }

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth / 3, screenHeight / 3);
        setLocationRelativeTo(null);

        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,panel,table);
        splitPane.setDividerLocation(250);
        splitPane.setResizeWeight(1);
        add(splitPane);

        validate();
        this.pack();
        setSize(450,350);

        sacuvajBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//cuvamo filePath do slike
                slot.setSlotFile(imageFile);
            }
        });

        ucitajBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();//picker
                jfc.setFileFilter(new SlotImageFileFilter());

                if(jfc.showOpenDialog(MainFrame.getInstance())==JFileChooser.APPROVE_OPTION){//ucitavamo novu sliku
                    AppCore.getInstance().getISerialization().readImageSlot(jfc.getSelectedFile(), imageFile, image, picLabel, panel);
                    imageFile = jfc.getSelectedFile();
                }
            }
        });
    }
}
