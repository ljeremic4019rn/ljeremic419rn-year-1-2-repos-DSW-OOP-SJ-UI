package appcore;

import gui.MainFrame;
import repository.data.Row;
import javax.swing.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        AppCore.getInstance().initialise();
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.setAppCore(AppCore.getInstance());
    }
}
