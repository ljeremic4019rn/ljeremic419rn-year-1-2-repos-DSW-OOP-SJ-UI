package dsw.rudok.app.command;

import dsw.rudok.app.AppCore;
import dsw.rudok.app.core.ICommand;
import dsw.rudok.app.errorhandler.ErrorType;

import java.util.ArrayList;

public class CommandManager implements ICommand {
    private ArrayList<AbstractCommand> commands = new ArrayList<>();  //lista koja predstavlja stek na kome se nalaze konkretne izvršene komande
    private int currentCommand = 0; //pokazivač steka, sadrži redni broj komande za undo / redo operaciju
    private int locationCheckerForError1 = 1;
    private int locationCheckerForError2 = 1;

    public void addCommand(AbstractCommand command){ // Dodaje novu komandu na stek i poziva izvršavanje komande
        while(currentCommand < commands.size())
            commands.remove(currentCommand); // Ova while petlja overriduje undo kada krenemo opet da pisemo nakon undo
        commands.add(command);
        doCommand();
    }

    public void doCommand(){ //Metoda koja poziva izvršavanje konkretne komande
        if(currentCommand < commands.size()){
            commands.get(currentCommand++).doCommand();
            locationCheckerForError2 = 1;
        }
        if(currentCommand == commands.size()){
            if (locationCheckerForError2 == 0){//koristi da se error ne bi prerano aktivirao
                AppCore.getInstance().getIErrorHandler().generateError(ErrorType.NO_REDO_REMAINING);
            }
            locationCheckerForError2--;
        }
    }

    public void undoCommand(){ //Metoda koja poziva undo konkretne komande
        if(currentCommand > 0){
            commands.get(--currentCommand).undoCommand();
            locationCheckerForError1 = 1;
        }
        if(currentCommand == 0){
            if (locationCheckerForError1 == 0){//koristi da se error ne bi prerano aktivirao
                AppCore.getInstance().getIErrorHandler().generateError(ErrorType.NO_UNDO_REMAINING);
            }
            locationCheckerForError1--;
        }
    }
}
