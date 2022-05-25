package gui;

import appcore.AppCore;
import gui.controller.ButtonActionListener;
import lombok.Data;
import observer.Notification;
import observer.Subscriber;
import observer.enums.NotificationCode;
import repository.implementation.InformationResource;
import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

@Data

public class MainFrame extends JFrame implements Subscriber {
    private static MainFrame instance = null;
    private AppCore appCore;
    private JTable jTable;
    private JScrollPane jsp;
    private JSplitPane splitPane;
    private JSplitPane splitPane2;
    private JTextArea textArea;
    private JPanel bottomStatus;
    private JButton compBtn = new JButton("Compile");

    private MainFrame() {

    }

    public static MainFrame getInstance(){
        if (instance==null){
            instance=new MainFrame();
            instance.initialise();
        }
        return instance;
    }

    private void initialise() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jTable = new JTable();
        jTable.setPreferredScrollableViewportSize(new Dimension(750, 650));
        jTable.setFillsViewportHeight(true);

        textArea = new JTextArea();

        textArea.setSize(new Dimension(750, 300));

        splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(textArea),compBtn);
        compBtn.addActionListener(new ButtonActionListener(textArea));//uzimamo input i parsiramo ga


        splitPane2.setDividerLocation(650);

        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane2, new JScrollPane(jTable));
        splitPane.setDividerLocation(110);
        this.add(splitPane);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void setAppCore(AppCore appCore) {
        this.appCore = appCore;
        this.appCore.addSubscriber(this);
        this.jTable.setModel(appCore.getTableModel());
    }

    @Override
    public void update(Notification notification) {
        if (notification.getCode() == NotificationCode.RESOURCE_LOADED){
            System.out.println((InformationResource)notification.getData());
        }
        else{
            jTable.setModel((TableModel) notification.getData());
        }
    }
}
