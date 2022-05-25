package dsw.rudok.app.core;

import dsw.rudok.app.command.AbstractCommand;

public interface ICommand {
    void addCommand(AbstractCommand command);
    void doCommand();
    void undoCommand();
}
