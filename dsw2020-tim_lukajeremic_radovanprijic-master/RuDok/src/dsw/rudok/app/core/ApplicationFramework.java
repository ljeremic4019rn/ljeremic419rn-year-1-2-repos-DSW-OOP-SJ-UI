package dsw.rudok.app.core;

import dsw.rudok.app.errorhandler.ErrorHandler;
import dsw.rudok.app.serialization.SerializationComponent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import dsw.rudok.app.slothandler.SlotHandler;

@Getter
@Setter
@NoArgsConstructor
public abstract class ApplicationFramework { //ova klasa je potrebna da bi mogli da pokrenemo gui preko main klase

    protected Gui gui;
    protected Repository repository;
    protected IErrorHandler iErrorHandler;
    protected ISlotHandler iSlotHandler;
    protected ISerialization iSerialization;

    public abstract void run();
    public void initialise(Gui gui, Repository repository, IErrorHandler iErrorHandler, ISlotHandler iSlotHandler, ISerialization iSerialization){
        this.gui = gui;
        this.repository = repository;
        this.iErrorHandler = iErrorHandler;
        this.iErrorHandler.addSubscriber(gui);
        this.iSlotHandler = iSlotHandler;
        this.iSlotHandler.addSubscriber(gui);
        this.iSerialization = iSerialization;
    }
}
