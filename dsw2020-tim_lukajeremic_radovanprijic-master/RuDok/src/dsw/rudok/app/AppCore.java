package dsw.rudok.app;

import dsw.rudok.app.core.*;
import dsw.rudok.app.errorhandler.ErrorHandler;
import dsw.rudok.app.gui.swing.SwingGui;
import dsw.rudok.app.repository.RepositoryImpl;
import dsw.rudok.app.serialization.SerializationComponent;
import dsw.rudok.app.slothandler.SlotHandler;

public class AppCore extends ApplicationFramework {
    private static AppCore instance;

    private AppCore(){

    }

    public static AppCore getInstance(){
        if(instance==null){
            instance = new AppCore();
        }
        return instance;
    }

    public void run(){
        this.gui.start();
    }

    public static void main(String[] args) {
        Repository repository = new RepositoryImpl();
        Gui gui = new SwingGui(repository);
        IErrorHandler iErrorHandler = new ErrorHandler();
        ISlotHandler iSlotHandler = new SlotHandler();
        ISerialization iSerialization = new SerializationComponent();
        ApplicationFramework appCore = AppCore.getInstance();
        appCore.initialise(gui, repository, iErrorHandler, iSlotHandler, iSerialization);
        appCore.run();

    }
}
